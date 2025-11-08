package com.clinic.notifications;

public interface SmsSender {
  void sendOtp(String rawMobile, String code) throws Exception;
}