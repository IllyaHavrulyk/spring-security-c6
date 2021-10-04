package com.havrulyk.springsecurityc6.service;

import com.havrulyk.springsecurityc6.entity.User;
import com.havrulyk.springsecurityc6.repository.UserRepository;
import com.havrulyk.springsecurityc6.security.model.SecurityUser;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JpaUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> maybeUser = userRepository.findUserByUsername(username);
    User user = maybeUser.orElseThrow(() -> new UsernameNotFoundException("Invalid username"));
    return new SecurityUser(user);
  }
}
