package com.gabia.gyebalja.repository;

import com.gabia.gyebalja.domain.Likes;
import lombok.Builder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Long deleteByUserIdAndBoardId(Long userId, Long boardId);
}
