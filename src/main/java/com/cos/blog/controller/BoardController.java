package com.cos.blog.controller;

import com.cos.blog.service.BoardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 메인페이지로 갈때 데이터를 들고 간다
    @GetMapping({"","/"})
    public String index(Model model) {
        model.addAttribute("boards", boardService.boardList());
        return "index"; // view Resolver 작동, model의 정보를 들고 이동
    }

    @GetMapping("/board/save_form")
    public String saveForm() {
        return "board/save_form";
    }
}
