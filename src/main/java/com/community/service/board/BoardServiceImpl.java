package com.community.service.board;


import com.community.domain.dto.board.BoardDto;
import com.community.domain.entity.Board;
import com.community.domain.entity.Member;
import com.community.domain.entity.formEntity.AddboardForm;
import com.community.domain.entity.formEntity.EditBoardForm;
import com.community.repository.board.BoardRepository;
import com.community.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Primary
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardServiceImpl implements BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    private static final int BLOCK_PAGE_NUM_COUNT = 5;//블럭에 존재하는 페이지 수
    private static final int PAGE_POST_COUNT = 7;//한 페이지에 존재하는 게시글 수

    /*게시글 저장하기*/
    @Transactional
    @Override
    public Board save(Long memberId, AddboardForm boardForm) throws IOException {
        Member member = memberRepository.findOne(memberId);

        Board board = Board.createBoard(member, boardForm);

        boardRepository.save(board);
        return board;
    }

    public Board findOne(Long id){
        return boardRepository.findById(id);
    }

    /*게시글 수정하기*/
    @Transactional
    @Override
    public BoardDto update(Long beforeboardId, EditBoardForm editBoardForm) {
        Optional<Board> board = findOne(beforeboardId);


    }

    /*게시글 삭제하기*/
    @Transactional
    @Override
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    /*전체 게시글 조회하기*/
    @Override
    public List<Board> findAll(){
        return boardRepository.findAll();
    }


    /*게시글 PAGE_POST_COUNT만큼 가져오기*/
    @Override
    public List<BoardDto> getBoardList(Integer pageNum){
        Page<Board> page = boardRepository.findAll(PageRequest
                .of(pageNum-1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "date")));

        List<Board> boards = page.getContent();
        List<BoardDto> boardDtoList = new ArrayList<>();
        for (Board board : boards) {
            boardDtoList.add(BoardDto.createboardDto(board));
        }

        return boardDtoList;
    }

    /*페이징 숫자 리스트 가져오기*/
    @Override
    public Integer[] getPageList(Integer currentPageNum){
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        // 총 게시글 수
        Double postsTotalCount = Double.valueOf(this.getBoardCount());
        System.out.println("postsTotalCount = " + postsTotalCount);
        // 총 게시글 수를 기준으로 계산한 마지막 페이지 번호 계산  // PAGE_POST_COUNT = 7
                                                    // BLOCK_PAGE_NUM_COUNT = 5
        Integer totalLastPageNum = (int) (Math.ceil((postsTotalCount / PAGE_POST_COUNT)));//6
        System.out.println("totalLastPageNum = " + totalLastPageNum);
        //현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = ( currentPageNum+(BLOCK_PAGE_NUM_COUNT-1) > totalLastPageNum)
                ? totalLastPageNum
                : currentPageNum + (BLOCK_PAGE_NUM_COUNT-1);

        // 페이지 시작 번호 조정
//        currentPageNum = (currentPageNum<=3) ? 1 : currentPageNum-2;

        // 페이지 번호 할당
        for(int p=currentPageNum, i=0; p<=blockLastPageNum; p++, i++){
            pageList[i] = p;
        }
        return pageList;
    }

    /*전체 게시글 수*/
    @Override
    public Long getBoardCount(){
        return boardRepository.count();
    }

    /*Title에 keyword가 포함된 */
    @Override
    public List<BoardDto> searchPostsTitle(String keyword) {
        List<Board> boards = boardRepository.findByTitleContaining(keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();
        if(boards.isEmpty()) return boardDtoList;
        for (Board board : boards) {
            boardDtoList.add(BoardDto.createboardDto(board));
        }
        return boardDtoList;
    }

    @Override
    public List<BoardDto> searchPostsContent(String keyword) {
        List<Board> boards = boardRepository.findByContentContaining(keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();
        if(boards.isEmpty()) return boardDtoList;
        for (Board board : boards) {
            boardDtoList.add(BoardDto.createboardDto(board));
        }
        return boardDtoList;
    }


}