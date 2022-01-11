package com.cos.blog.service;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        System.out.println("Board saved!");
    }

    @Transactional(readOnly = true)
    public Page<Board> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board viewBoardDetail(Long id) {
        return boardRepository.findById(id)
            .orElseThrow(() -> {
                return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을 수 없습니다.");
            })
        ;
    }

    @Transactional
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void updateBoard(Long id, Board requestBoard) {
        Board board = boardRepository.findById(id)
            .orElseThrow(() -> {
                return new IllegalArgumentException("글 찾기 실패: 아이디를 찾을 수 없습니다.");
            }); // 영속화
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        // 해당 함수로 종료시에 트랜잭션이 service가 종료될때 트랜잭션이 종료. 이때 dirty checking 이 일어남 - 자동 업데이트
    }

    @Transactional
    public void comment(ReplySaveRequestDto replySaveRequestDto) {
        replyRepository.saveComment(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
    }

}// end of class