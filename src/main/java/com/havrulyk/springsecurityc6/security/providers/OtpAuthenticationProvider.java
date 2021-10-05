package com.havrulyk.springsecurityc6.security.providers;

import com.havrulyk.springsecurityc6.entity.Otp;
import com.havrulyk.springsecurityc6.repository.OtpRepository;
import com.havrulyk.springsecurityc6.security.authentication.OtpAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private OtpRepository otpRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String otp = (String) authentication.getCredentials();
        Optional<Otp> maybeJpaOtp = otpRepository.findOtpByUsername(username);
        if(maybeJpaOtp.isPresent()){
            return new OtpAuthentication(username, otp, List.of(() -> "read"));
        }
        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuthentication.class.equals(authentication);
    }
}
