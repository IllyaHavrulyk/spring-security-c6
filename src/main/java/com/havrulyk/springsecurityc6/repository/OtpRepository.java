package com.havrulyk.springsecurityc6.repository;

import com.havrulyk.springsecurityc6.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findOtpByUsername(String username);
}
