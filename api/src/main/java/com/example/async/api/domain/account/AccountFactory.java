package com.example.async.api.domain.account;

public class AccountFactory {
  public static Account createNew(String name, String email, String password) {
    var userName = new UserName(name);
    var emailObj = new Email(email);
    return new Account(null, userName, emailObj, password);
  }
}
