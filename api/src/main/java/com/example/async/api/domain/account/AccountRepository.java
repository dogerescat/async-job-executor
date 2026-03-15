package com.example.async.api.domain.account;

import java.util.Optional;

public interface AccountRepository {
  Account save(Account account);

  Optional<Account> findById(Long id);

  Optional<Account> findByEmail(Email email);
}
