package com.havrulyk.springsecurityc6.security.providers;

import com.havrulyk.springsecurityc6.managers.TokenManager;
import com.havrulyk.springsecurityc6.security.authentication.TokenAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthProvider implements AuthenticationProvider {

  @Autowired
  private TokenManager tokenManager;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String token = authentication.getName();
    boolean exists = tokenManager.contains(token);
    if(exists){
      return new TokenAuthentication(token, null, null);
    }

    throw new BadCredentialsException("Bad credentials");
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return TokenAuthentication.class.equals(aClass);
  }
}
