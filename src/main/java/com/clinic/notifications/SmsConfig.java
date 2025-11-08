package com.clinic.notifications;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsConfig {

  // For local/dev: send OTP to console. Replace later with a real provider bean.
  @Bean
  public SmsSender smsSender() {
    return new ConsoleSmsSender();
  }
}