package com.example.async.api.application.account;

import com.example.async.api.domain.account.Account;
import com.example.async.api.domain.account.AccountFactory;
import com.example.async.api.domain.account.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
  private final AccountRepository accountRepository;

  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Account create(CreateAccountCommand command) {
    var account = AccountFactory.createNew(command.name(), command.email(), command.password());
    var savedAccount = accountRepository.save(account);

    return savedAccount;
  }

}
