package com.community.web.apiController;

import com.community.domain.dto.CMRespDto;
import com.community.domain.dto.board.BoardDto;
import com.community.domain.dto.board.BoardPageDto;
import com.community.domain.dto.board.ReqCommentDto;
import com.community.domain.entity.Board;
import com.community.domain.entity.formEntity.AddboardForm;
import com.community.domain.entity.formEntity.EditBoardForm;
import com.community.service.member.MemberService;
import com.community.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.community.domain.dto.board.BoardDto.createboardDto;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ApiBoardController {

    private final BoardService boardService;
    private static final double BLOCK_PAGE_NUM_COUNT = 5;//블럭에 존재하는 페이지 수
    private static final double PAGE_POST_COUNT = 7;//한 페이지에 존재하는 게시글 수

    @GetMapping("/api/home")
    public ResponseEntity<?> boardsAll(@RequestParam(value="page", defaultValue="1") Integer page){
        Integer[] pageList = boardService.getPageList(page);
        List<BoardDto> boardList = boardService.getBoardList(page);
        return new ResponseEntity<>(new BoardPageDto(pageList, boardList), HttpStatus.OK);
    }

    // 글 keyword로 검색
    @GetMapping("/api/home/search")
    public ResponseEntity<?> searchBoards(@RequestParam String selected, @RequestParam String keyword){
        List<BoardDto> boardDtos = new ArrayList<>();
        if(selected.equals("title")) boardDtos = boardService.searchPostsTitle(keyword);
        else boardDtos = boardService.searchPostsContent(keyword);
//        Integer[] pageList = boardService.getPageList(1);/**/
//        return new ResponseEntity<>(new BoardPageDto(pageList, boardDtos), HttpStatus.OK);
        return new ResponseEntity<>(boardDtos, HttpStatus.OK);
    }

    // 글 상세
    @GetMapping("/api/board/{id}")
    public ResponseEntity<?> boardOne(@PathVariable Long id){
        Optional<Board> result = boardService.findOne(id);
        Board board = result.orElse(null);
        return new ResponseEntity<>(createboardDto(board), HttpStatus.OK);
    }

    //글 삭제
    @DeleteMapping("/api/board/{id}")
    public ResponseEntity<?> boardDelete(@PathVariable Long id){
        System.out.println("글 삭제 작동@@@@@!!!!!");
        boardService.delete(id);
        return new ResponseEntity<>(new CMRespDto<>(1, "success", null), HttpStatus.OK);
    }

    // 글쓰기
    @PostMapping("/api/board")
    public ResponseEntity<?> addboard(HttpServletRequest request, @RequestBody AddboardForm addboardForm) throws IOException {
        /*왜 아래 라인처럼 하면 되고
         BoardService.save((Long)request.getAttribute("memberId"), addboardForm) 이렇게 하면 안되는지 모르겠음*/
        Long memberId  = (Long) request.getAttribute("memberId");
        Board board = boardService.save(memberId, addboardForm);
        return new ResponseEntity<>(createboardDto(board), HttpStatus.OK);
    }

    // 글 수정
    @PutMapping("/api/board/{id}")
    public ResponseEntity<?> editboard(@PathVariable Long id, @RequestBody EditBoardForm editboardForm){
        BoardDto boardDto = boardService.update(id, editboardForm);
        return new ResponseEntity<>(boardDto, HttpStatus.OK);
    }

    //last페이지
    @GetMapping("api/maxPage")
    public ResponseEntity<?> getMaxPage(){
        Long postsTotalCount = boardService.getBoardCount();
        Integer totalLastPageNum = (int) (Math.ceil((postsTotalCount / PAGE_POST_COUNT)));//6
        return new ResponseEntity<>(totalLastPageNum, HttpStatus.OK);
    }

    /*댓글 관련*/
    @PostMapping("api/board/comment")
    public ResponseEntity<?> addComment(@RequestParam(value="board") Long boardId,
                                        @RequestBody ReqCommentDto reqCommentDto,
                                        HttpServletRequest request){
        System.out.println("🛑🛑🛑🛑🛑🛑🛑🛑🛑🛑🛑🛑");
        Long memberId  = (Long) request.getAttribute("memberId");
        BoardDto boardDto = boardService.addComment(boardId, memberId, reqCommentDto);
        return new ResponseEntity<>(boardDto, HttpStatus.OK);
    }
}
