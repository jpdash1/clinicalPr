package com.clinic.notifications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleSmsSender implements SmsSender {
  private static final Logger log = LoggerFactory.getLogger(ConsoleSmsSender.class);

  @Override
  public void sendOtp(String rawMobile, String code) {
    // Local dev: print OTP to console
    log.info("[DEV-OTP] To={} Code={}", rawMobile, code);
  }
}