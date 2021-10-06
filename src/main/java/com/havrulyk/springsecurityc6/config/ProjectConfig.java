package com.havrulyk.springsecurityc6.config;

import com.havrulyk.springsecurityc6.security.filter.TokenAuthFilter;
import com.havrulyk.springsecurityc6.security.filter.UsernamePasswordAuthFilter;
import com.havrulyk.springsecurityc6.security.providers.OtpAuthenticationProvider;
import com.havrulyk.springsecurityc6.security.providers.TokenAuthProvider;
import com.havrulyk.springsecurityc6.security.providers.UsernamePasswordAuthenticationProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
//@EnableAsync
public class ProjectConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

  @Autowired
  private OtpAuthenticationProvider otpAuthenticationProvider;

  @Autowired
  private UsernamePasswordAuthFilter usernamePasswordAuthFilter;

  @Autowired
  private TokenAuthFilter tokenAuthFilter;

  @Autowired
  private TokenAuthProvider tokenAuthProvider;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(usernamePasswordAuthenticationProvider).authenticationProvider(otpAuthenticationProvider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.addFilterAt(usernamePasswordAuthFilter, BasicAuthenticationFilter.class).addFilterAfter(tokenAuthFilter, BasicAuthenticationFilter.class);
  }

  @Bean
  public TokenAuthFilter tokenAuthFilter(){
    return new TokenAuthFilter();
  }

  @Bean
  public UsernamePasswordAuthFilter usernamePasswordAuthFilter(){
    return new UsernamePasswordAuthFilter();
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  //InitializingBean is called by spring when initializing a context, so we can set instructions for executing before initialization of context
  public InitializingBean initializingBean(){
    return () -> {
      //This strategy is for asynchronous processing of requests, because when @Async for controller is enabled, then it will have different threads for methods and information will be lost,
      // this strategy inherits information from previous thread and stores it to current thread
      SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    };
  }
}
