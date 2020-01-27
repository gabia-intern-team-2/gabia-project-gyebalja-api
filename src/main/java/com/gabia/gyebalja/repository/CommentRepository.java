package com.gabia.gyebalja.repository;

import com.gabia.gyebalja.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
