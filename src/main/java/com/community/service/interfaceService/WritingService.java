package com.community.service.interfaceService;

import com.community.domain.entity.Writing;
import com.community.domain.entity.formEntity.AddWritingForm;

import java.util.List;

public interface WritingService {
    /*
     *  GET /writings  : 글목록 조회(main)
     *  POST /writings : 글 생성
     *  GET /writings/{writingNum}  : 글 상세조회
     *  POST /writings/{writingNum} : 글 수정
     *  DELETE /writings/{writingNum} : 글 삭제
     */

    //GET /writings  : 글목록 조회(main)
    List<Writing> findAll();

    //GET /writings/{writingNum}  : 글 상세조회
    Writing findOne(Long id);

    //POST /writings : 글 생성
    Writing save(Long memberId, AddWritingForm writingForm);

    //POST /writings/{writingNum} : 글 수정
    void update(Long beforeWritingId, Writing afterWriting);

    //DELETE /writings/{writingNum} : 글 삭제
    void delete(Long id);






}
