package com.gabia.gyebalja.repository;

import com.gabia.gyebalja.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
