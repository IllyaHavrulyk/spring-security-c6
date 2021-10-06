package com.havrulyk.springsecurityc6.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @RequestMapping(value = "/hello", method = RequestMethod.GET)
  //    @Async
  public String hello() {
    Runnable r = () -> {
      Authentication authentication =
          SecurityContextHolder.getContext().getAuthentication();

      System.out.println(authentication);
    };
//        DelegatingSecurityContextRunnable dr = new DelegatingSecurityContextRunnable(r);

    ExecutorService service = Executors.newSingleThreadExecutor();
//        DelegatingSecurityContextExecutorService dService =
//                new DelegatingSecurityContextExecutorService(service);

    service.submit(r);
    service.shutdown();

    return "Hello!";
  }// token
}
