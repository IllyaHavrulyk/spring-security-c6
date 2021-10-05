package com.havrulyk.springsecurityc6.security.filter;

import com.havrulyk.springsecurityc6.entity.Otp;
import com.havrulyk.springsecurityc6.managers.TokenManager;
import com.havrulyk.springsecurityc6.repository.OtpRepository;
import com.havrulyk.springsecurityc6.security.authentication.OtpAuthentication;
import com.havrulyk.springsecurityc6.security.authentication.UsernamePasswordAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

public class UsernamePasswordAuthFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private TokenManager tokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String otp = request.getHeader("otp");

        if (otp == null) {
            Authentication authentication = new UsernamePasswordAuthentication(username, password);
            authentication = authenticationManager.authenticate(authentication);
            String code = String.valueOf(new Random().nextInt(9999) + 1000);
            Otp otpEntity = new Otp();
            otpEntity.setUsername(username);
            otpEntity.setOtp(code);
            otpRepository.save(otpEntity);
        } else {
            Authentication authentication = new OtpAuthentication(username, otp);
            authentication = authenticationManager.authenticate(authentication);
            String token = UUID.randomUUID().toString();
            tokenManager.add(token);
            response.setHeader("Authorization", token);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }
}
