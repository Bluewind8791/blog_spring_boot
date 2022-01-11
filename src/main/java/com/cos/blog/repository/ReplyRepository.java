package com.cos.blog.repository;

import com.cos.blog.model.Reply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    
    @Modifying
    @Query(value = "INSERT INTO reply(`userId`, `boardId`, `content`, `createDate`, `updateDate`) VALUES(?1, ?2, ?3, now(), now())", nativeQuery = true)
    Integer saveComment(Long userId, Long boardId, String content);
}
