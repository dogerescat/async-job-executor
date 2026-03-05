package com.example.async.api.infrastructure.persistence.account.converter;

import com.example.async.api.domain.account.UserName;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA が UserName 値オブジェクトをカラムに保存・復元するためのコンバータ。
 */
@Converter(autoApply = false)
public class UserNameConverter implements AttributeConverter<UserName, String> {
  @Override
  public String convertToDatabaseColumn(UserName attribute) {
    return attribute == null ? null : attribute.getValue();
  }

  @Override
  public UserName convertToEntityAttribute(String dbData) {
    return dbData == null ? null : new UserName(dbData);
  }
}
