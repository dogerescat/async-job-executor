package com.example.async.api.domain.account;

import lombok.Getter;

@Getter
public class Account {
  private Long id;
  private UserName name;
  private Email email;
  private String password;

  public Account() {}

  public Account(Long id, UserName name, Email email, String password) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
  }


}
