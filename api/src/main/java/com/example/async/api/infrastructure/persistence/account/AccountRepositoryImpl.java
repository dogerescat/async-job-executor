package com.example.async.api.infrastructure.persistence.account;

import java.util.Optional;
import com.example.async.api.domain.account.Account;
import com.example.async.api.domain.account.AccountRepository;
import com.example.async.api.domain.account.Email;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
  private final AccountJpaRepository accountJpaRepository;

  public AccountRepositoryImpl(AccountJpaRepository accountJpaRepository) {
    this.accountJpaRepository = accountJpaRepository;
  }

  @Override
  public Account save(Account account) {
    AccountEntity saved = accountJpaRepository.save(AccountMapper.toEntity(account));
    return AccountMapper.toDomain(saved);
  }

  @Override
  public Optional<Account> findById(Long id) {
    return accountJpaRepository.findById(id).map(AccountMapper::toDomain);
  }

  @Override
  public Optional<Account> findByEmail(Email email) {
    return accountJpaRepository.findByEmail(email).map(AccountMapper::toDomain);
  }
}
