package com.community.web.controller;

import com.community.domain.entity.Board;
import com.community.service.board.BoardService;
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
    private final BoardService boardService;

    @GetMapping
    public String adminHome(Model model){
        List<Board> Boards = boardService.findAll();
        model.addAttribute("boards", Boards);
        return "/admin/main";
    }

    @GetMapping("/board/{boardId}")
    public String delete(@PathVariable Long boardId){
        boardService.delete(boardId);

        return "redirect:/admin";
    }
}
