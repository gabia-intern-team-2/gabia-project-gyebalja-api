package com.gabia.gyebalja.repository;

import com.gabia.gyebalja.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoardId(Long id);  // 검토. throws
}
