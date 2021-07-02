package com.community.controller;

import com.community.entity.Writing;
import com.community.repository.WritingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/writings")
public class writingController {

    private final WritingRepository repository;

    /*
    *  GET /writings  : 글목록 조회(main)
    *  POST /writings : 글 생성
    *  GET /writings/{writingNum}  : 글 상세조회
    *  POST /writings/{writingNum}/edit : 글 수정
    *  DELETE /writings/{writingNum} : 글 삭제
    */

    @GetMapping
    public String main(Model model){
        List<Writing> writings = repository.getAll();
        model.addAttribute("writings", writings);
        return "/basic/main";
    }

    @GetMapping("/{writingId}")
    public String writing(@PathVariable Long writingId, Model model){
        Writing writing = repository.getOne(writingId);
        model.addAttribute("writing", writing);
        return "/basic/writing";
    }

}
