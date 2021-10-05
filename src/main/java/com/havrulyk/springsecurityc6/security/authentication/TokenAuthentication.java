package com.havrulyk.springsecurityc6.security.authentication;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public class TokenAuthentication extends UsernamePasswordAuthentication {

  public TokenAuthentication(Object principal, Object credentials) {
    super(principal, credentials);
  }

  public TokenAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
    super(principal, credentials, authorities);
  }
}
