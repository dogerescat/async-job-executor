package com.example.async.api.infrastructure.persistence.account;

import java.util.Optional;
import com.example.async.api.domain.account.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJpaRepository extends JpaRepository<AccountEntity, Long> {
  Optional<AccountEntity> findByEmail(Email email);

}
