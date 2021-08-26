package com.community.service.board;


import com.community.domain.dto.board.BoardDto;
import com.community.domain.entity.Board;
import com.community.domain.entity.Member;
import com.community.domain.entity.formEntity.AddboardForm;
import com.community.repository.member.MemberRepository;
import com.community.repository.board.BoardJpaRepository;
import com.community.repository.board.BoardRepository;
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

@Primary
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardJpaRepository boardJpaRepository;

    private static final int BLOCK_PAGE_NUM_COUNT = 5;//블럭에 존재하는 페이지 수
    private static final int PAGE_POST_COUNT = 7;//한 페이지에 존재하는 게시글 수

    @Transactional
    public Board save(Long memberId, AddboardForm boardForm) throws IOException {
        Member member = memberRepository.findOne(memberId);

        Board board = Board.createBoard(member, boardForm);

        boardRepository.save(board);
        return board;
    }

    public Board findOne(Long id){
        return boardRepository.findOne(id);
    }


    @Transactional
    @Override
    public void update(Long beforeboardId, Board afterboard) {

    }

    @Transactional
    @Override
    public void delete(Long id) {
        boardRepository.delete(id);
    }

    public List<Board> findAll(){
        return boardRepository.findAll();
    }


    public List<BoardDto> getBoardList(Integer pageNum){
        Page<Board> page = boardJpaRepository.findAll(PageRequest
                .of(pageNum-1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "createDate")));

        List<Board> boards = page.getContent();
        List<BoardDto> boardDtoList = new ArrayList<>();
        for (Board board : boards) {
            boardDtoList.add(BoardDto.createboardDto(board));
        }

        return boardDtoList;
    }

    public Integer[] getPageList(Integer currentPageNum){
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        // 총 게시글 수
        Double postsTotalCount = Double.valueOf(this.getBoardCount());

        // 총 게시글 수를 기준으로 계산한 마지막 페이지 번호 계산
        Integer totalLastPageNum = (int) (Math.ceil((postsTotalCount / PAGE_POST_COUNT)));

        //현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum>currentPageNum+BLOCK_PAGE_NUM_COUNT)
                ? currentPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        // 페이지 시작 번호 조정
        currentPageNum = (currentPageNum<=3) ? 1 : currentPageNum-2;

        // 페이지 번호 할당
        for(int val=currentPageNum, i=0; val<=blockLastPageNum;val++,i++){
            pageList[i] = val;
        }
        return pageList;
    }

    public Long getBoardCount(){
        return boardJpaRepository.count();
    }

}
