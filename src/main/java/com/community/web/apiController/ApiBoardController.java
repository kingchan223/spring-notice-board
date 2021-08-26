package com.community.web.apiController;

import com.community.domain.dto.board.BoardDto;
import com.community.domain.dto.board.BoardPageDto;
import com.community.domain.entity.Board;
import com.community.domain.entity.formEntity.AddboardForm;
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
    private final MemberService memberService;
    private static final double BLOCK_PAGE_NUM_COUNT = 5;//블럭에 존재하는 페이지 수
    private static final double PAGE_POST_COUNT = 7;//한 페이지에 존재하는 게시글 수

    @GetMapping("/api/home")
    public ResponseEntity<?> boardsAll(@RequestParam(value="page", defaultValue="1") Integer page){

//        List<Board> result = boardService.findAll();
//        List<BoardDto> wrtings = new ArrayList<>();

        Integer[] pageList = boardService.getPageList(page);
        List<BoardDto> boardList = boardService.getBoardList(page);
//        for (Board board : result) {
//            wrtings.add(createboardDto(board));
//        }
        return new ResponseEntity<>(new BoardPageDto(pageList, boardList), HttpStatus.OK);

    }

    @GetMapping("/api/home2")
    public ResponseEntity<?> boardsAll2(@RequestParam(value="page", defaultValue="1") Integer page){

        List<Board> result = boardService.findAll();
        List<BoardDto> wrtings = new ArrayList<>();
        for (Board board : result) {
            wrtings.add(createboardDto(board));
        }
        return new ResponseEntity<>(wrtings, HttpStatus.OK);

    }

    // 글 상세
    @GetMapping("/api/board/{id}")
    public ResponseEntity<?> boardOne(@PathVariable Long id){
        Optional<Board> result = boardService.findOne(id);
        Board board = result.orElse(null);
        return new ResponseEntity<>(createboardDto(board), HttpStatus.OK);
    }

    // 글쓰기
    @PostMapping("/api/board")
    public ResponseEntity<?> addboard(HttpServletRequest request, @RequestBody AddboardForm addboardForm) throws IOException {
        System.out.println("글쓰기 실행");
        System.out.println("request.getAttribute(\"memberId\"): "+request.getAttribute("memberId"));
        /*왜 아래 라인처럼 하면 되고
         BoardService.save((Long)request.getAttribute("memberId"), addboardForm) 이렇게 하면 안되는지 모르겠음*/
        Long memberId  = (Long) request.getAttribute("memberId");
        Board board = boardService.save(memberId, addboardForm);
        return new ResponseEntity<>(createboardDto(board), HttpStatus.OK);
    }

    @GetMapping("api/maxPage")
    public ResponseEntity<?> getMaxPage(){
        Long postsTotalCount = boardService.getBoardCount();
        Integer totalLastPageNum = (int) (Math.ceil((postsTotalCount / PAGE_POST_COUNT)));//6
        return new ResponseEntity<>(totalLastPageNum, HttpStatus.OK);
    }
}
