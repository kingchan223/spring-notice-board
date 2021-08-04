package com.community.web.controller;

import com.community.domain.entity.Writing;
import com.community.service.writing.WritingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final WritingService writingService;

    @GetMapping
    public String adminHome(Model model){
        List<Writing> writings = writingService.findAll();
        model.addAttribute("writings", writings);
        return "/admin/main";
    }

    @GetMapping("/writing/{writingId}")
    public String delete(@PathVariable Long writingId){
        writingService.delete(writingId);

        return "redirect:/admin";
    }
}
