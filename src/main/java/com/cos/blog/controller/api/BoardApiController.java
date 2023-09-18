package com.cos.blog.controller.api;

import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.Board;
import com.cos.blog.service.BoardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    // 글쓰기
    @PostMapping(value = "/api/board")
    public ResponseEntity<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
        boardService.write(board, principal.getUser());
        return new ResponseEntity<>(1, HttpStatus.OK); 
    }

    //글 삭제하기
    @DeleteMapping("/api/board/{id}")
    public ResponseEntity<Integer> deleteById(@PathVariable int id) {
        boardService.boardDelete(id);
        return new ResponseEntity<>(1, HttpStatus.OK);      
    }

    //글 수정하기
    @PutMapping("/api/board/{id}")
    public ResponseEntity<Integer> update(@PathVariable int id, @RequestBody Board board){
        boardService.boardUpdate(id, board);
        return new ResponseEntity<>(1, HttpStatus.OK); 
    }
}
