package com.havrulyk.springsecurityc6.repository;

import com.havrulyk.springsecurityc6.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findUserByUsername(String username);
}
