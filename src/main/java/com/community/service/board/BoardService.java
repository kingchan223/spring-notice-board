package com.community.service.board;

import com.community.domain.dto.board.BoardDto;
import com.community.domain.entity.Board;
import com.community.domain.entity.formEntity.AddboardForm;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BoardService {
    /*
     *  GET /boards  : 글목록 조회(main)
     *  POST /boards : 글 생성
     *  GET /boards/{boardNum}  : 글 상세조회
     *  POST /boards/{boardNum} : 글 수정
     *  DELETE /boards/{boardNum} : 글 삭제
     */

    //GET /boards  : 글목록 조회(main)
    List<Board> findAll();

    //GET /boards/{boardNum}  : 글 상세조회
    Optional<Board> findOne(Long id);

    //POST /boards : 글 생성
    Board save(Long memberId, AddboardForm boardForm) throws IOException;

    //POST /boards/{boardNum} : 글 수정
    void update(Long beforeboardId, Board afterboard);

    //DELETE /boards/{boardNum} : 글 삭제
    void delete(Long id);

    List<BoardDto> getBoardList(Integer pageNum);

    Integer[] getPageList(Integer currentPageNum);

    Long getBoardCount();

    List<BoardDto> searchPostsTitle(String keyword);




}
