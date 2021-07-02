package com.community.controller;

import com.community.entity.Writing;
import com.community.repository.WritingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/writings")
public class writingController {
    private final WritingRepository repository;
    /*
    *  GET /writings  : 글목록 조회(main)
    *  POST /writings : 글 생성
    *  GET /writings  :  글 생성 화면
    *  GET /writings/{writingNum}  : 글 상세조회
    *  POST /writings/{writingNum}/edit : 글 수정
    *  DELETE /writings/{writingNum} : 글 삭제
    */
    @GetMapping
    public String main(Model model){
        List<Writing> writings = repository.getAll();
        model.addAttribute("writings", writings);
        for (Writing writing : writings) {
            log.info("writing.date={}", writing.getDate());

        }
        return "/basic/main";
    }
    @GetMapping("/{writingId}")
    public String writing(@PathVariable Long writingId, Model model){
        Writing writing = repository.getOne(writingId);
        model.addAttribute("writing", writing);
        return "/basic/writing";
    }
    @GetMapping("/add")
    public String add(){
        return "/basic/addForm";
    }

    @PostMapping("/add")

    public String addWriting(@ModelAttribute Writing writing, RedirectAttributes redirectAttributes, Model model){
        Writing savedWriting = repository.add(writing);
        redirectAttributes.addAttribute("writingId", savedWriting.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/writings/{writingId}";
    }
}
