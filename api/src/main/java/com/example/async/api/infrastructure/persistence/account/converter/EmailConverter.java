package com.example.async.api.infrastructure.persistence.account.converter;

import com.example.async.api.domain.account.Email;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA が Email 値オブジェクトをカラムに保存・復元するためのコンバータ。
 */
@Converter(autoApply = false)
public class EmailConverter implements AttributeConverter<Email, String> {
  @Override
  public String convertToDatabaseColumn(Email attribute) {
    return attribute == null ? null : attribute.getValue();
  }

  @Override
  public Email convertToEntityAttribute(String dbData) {
    return dbData == null ? null : new Email(dbData);
  }
}
