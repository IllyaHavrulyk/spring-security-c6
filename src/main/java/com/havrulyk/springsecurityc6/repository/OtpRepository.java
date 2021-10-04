package com.havrulyk.springsecurityc6.repository;

import com.havrulyk.springsecurityc6.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp, Long> {

}
