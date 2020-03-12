package com.gabia.gyebalja.repository;

import com.gabia.gyebalja.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Author : 이현재
 * Part : All
 */

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoardId(Long id);
}
