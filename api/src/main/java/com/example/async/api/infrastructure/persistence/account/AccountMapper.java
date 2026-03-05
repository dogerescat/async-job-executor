package com.example.async.api.infrastructure.persistence.account;

import com.example.async.api.domain.account.Account;

public class AccountMapper {
  public static AccountEntity toEntity(Account account) {
    return new AccountEntity(account.getId(), account.getName(), account.getEmail(),
        account.getPassword());
  }

  public static Account toDomain(AccountEntity entity) {
    return new Account(entity.getId(), entity.getName(), entity.getEmail(), entity.getPassword());
  }
}
