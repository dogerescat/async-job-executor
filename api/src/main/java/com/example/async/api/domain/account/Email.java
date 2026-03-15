package com.example.async.api.domain.account;

import java.util.regex.Pattern;

/*
 * メールアドレスを表す値オブジェクト
 */
public class Email {
  private static final Pattern EMAIL_PATTERN =
      Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

  private String value;

  public Email(String value) {
    if (value == null || !EMAIL_PATTERN.matcher(value).matches()) {
      throw new IllegalArgumentException("Invalid email address");
    }
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
