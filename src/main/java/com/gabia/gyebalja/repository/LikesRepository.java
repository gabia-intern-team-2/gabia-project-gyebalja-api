package com.gabia.gyebalja.repository;

import com.gabia.gyebalja.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    int countByBoardId(Long boardId);

    Optional<Likes> findByUserIdAndBoardId(Long userId, Long boardId);

    void deleteByUserIdAndBoardId(Long userId, Long boardId);

}
