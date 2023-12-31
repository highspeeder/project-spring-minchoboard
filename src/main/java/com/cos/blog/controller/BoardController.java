package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.service.BoardService;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 세션에 접근하고 싶으면 매개변수에 @AuthenticationPrincipal UserDetails principal를 사용한다.
    @GetMapping({ "", "/" })
    public String index(Model model,
            @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("boards", boardService.boardList(pageable));

        return "index"; // viewResolver 작동
    }

    @GetMapping(value = "/board/{id}")
    public String findByid(Model model, @PathVariable int id) {
        model.addAttribute("board", boardService.boardDetail(id));
        return "board/detail";
    }

    // user권한이 필요
    @GetMapping(value = "/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    // 수정하기
    @GetMapping(value = "/board/{id}/updateform")
    public String updateForm(@PathVariable int id, Model model) {
        model.addAttribute("board", boardService.boardDetail(id));
        return "board/updateForm";
    }

}
