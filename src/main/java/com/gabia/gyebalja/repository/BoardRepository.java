package com.gabia.gyebalja.repository;

import com.gabia.gyebalja.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("select board " +
            "from Board board join fetch board.comments comments " +
            "where board.id = :boardId")
    Optional<Board> findBoardDetail(@Param("boardId") Long boardId);
}
