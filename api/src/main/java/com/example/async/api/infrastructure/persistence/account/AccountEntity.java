package com.example.async.api.infrastructure.persistence.account;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import com.example.async.api.domain.account.Email;
import com.example.async.api.domain.account.UserName;
import com.example.async.api.infrastructure.persistence.account.converter.EmailConverter;
import com.example.async.api.infrastructure.persistence.account.converter.UserNameConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "accounts")
public class AccountEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Convert(converter = UserNameConverter.class)
  @Column(nullable = false, length = 16)
  private UserName name;

  @Convert(converter = EmailConverter.class)
  @Column(nullable = false, length = 256, unique = true)
  private Email email;

  @Column(nullable = false, length = 256)
  private String password;
  /**
   * タスク作成日時
   */
  @Column(nullable = false, updatable = false)
  private OffsetDateTime createdAt;

  /**
   * タスク更新日時
   */
  @Column(nullable = false)
  private OffsetDateTime updatedAt;

  public AccountEntity(Long id, UserName name, Email email, String password) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
  }

  @PrePersist
  private void onCreate() {
    OffsetDateTime now = OffsetDateTime.now();
    this.createdAt = now;
    this.updatedAt = now;
  }

  @PreUpdate
  private void onUpdate() {
    this.updatedAt = OffsetDateTime.now();
  }
}
