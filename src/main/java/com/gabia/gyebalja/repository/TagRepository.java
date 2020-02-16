package com.gabia.gyebalja.repository;

import com.gabia.gyebalja.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {

    //태그 이름으로 조회
    Optional<Tag> findHashTagByName(String tagName);
}
