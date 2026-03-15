package com.example.async.api.domain.account;

/**
 * ユーザ名を表す値オブジェクト
 */
public class UserName {
  private static final int MAX_LENGTH = 16;

  private String value;

  public UserName(String value) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("Invalid user name");
    }
    if (value.length() > MAX_LENGTH) {
      throw new IllegalArgumentException("User name must be " + MAX_LENGTH + " characters or less");
    }
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
