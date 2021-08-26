package com.community.service.board;

import com.community.domain.dto.board.BoardDto;
import com.community.domain.entity.Board;
import com.community.domain.entity.formEntity.AddboardForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    Board findOne(Long id);

    //POST /boards : 글 생성
    Board save(Long memberId, AddboardForm boardForm) throws IOException;

    //POST /boards/{boardNum} : 글 수정
    void update(Long beforeboardId, Board afterboard);

    //DELETE /boards/{boardNum} : 글 삭제
    void delete(Long id);

    public List<BoardDto> getBoardList(Integer pageNum);

    public Integer[] getPageList(Integer currentPageNum);
}
