package com.community.repository;

import com.community.entity.Writing;

import java.util.List;

public interface WritingRepository {
    /*
     *  GET /writings  : 글목록 조회(main)
     *  POST /writings : 글 생성
     *  GET /writings/{writingNum}  : 글 상세조회
     *  POST /writings/{writingNum} : 글 수정
     *  DELETE /writings/{writingNum} : 글 삭제
     */

    //GET /writings  : 글목록 조회(main)
    List<Writing> getAll();

    //GET /writings/{writingNum}  : 글 상세조회
    Writing getOne(Long id);

    //POST /writings : 글 생성
    void add(Writing writing);

    //POST /writings/{writingNum} : 글 수정
    void update(Writing writing);

    //DELETE /writings/{writingNum} : 글 삭제
    void delete(Long id);






}
