package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    public void write(Board board, User user) {
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board boardDetail(int id) {
        return boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("글 상세보기 실패");
        });
    }

    @Transactional
    public void boardDelete(int id) {
        boardRepository.deleteById(id);
    }

    // 메서드 종료시 트랜잭션이 종료 된다.
    // 이때 더티체킹이 되면서 자동 업데이트가 된다.(DB flush)
    @Transactional
    public void boardUpdate(int id, Board requestBoard) {
        Board board = boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("글 찾기 실패");
        });
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
    }

    @Transactional
    public void replyWrite(ReplySaveRequestDto replySaveDto) {
        // //직접 영속화하여 집어넣기
        // var user = userRepository.findById(replySaveDto.getUserId()).orElseThrow(()
        // -> {
        // return new IllegalArgumentException("댓글의 유저 찾기 실패");
        // });

        // var board =
        // boardRepository.findById(replySaveDto.getBoardId()).orElseThrow(() -> {
        // return new IllegalArgumentException("댓글의 게시글 찾기 실패");
        // });

        // Reply reply = Reply.builder()
        // .user(user)
        // .board(board)
        // .content(replySaveDto.getContent())
        // .build();

        // // 아마도 영속성 컨텍스트에서 가져온 객체 자체를 가지고 저장하는게 아니고, 다른 객체를 저장해주는 로직이라 save를 호출해야 함.
        // replyRepository.save(reply);

        // replyRepository에 메서드만들어서 사용하기
        replyRepository.replySave(replySaveDto.getUserId(), replySaveDto.getBoardId(), replySaveDto.getContent());
    }

    @Transactional
    public void replyDelete(int replyId) {
        replyRepository.deleteById(replyId);
    }
}
