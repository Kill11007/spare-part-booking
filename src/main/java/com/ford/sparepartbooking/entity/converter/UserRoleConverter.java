package com.ford.sparepartbooking.entity.converter;

import com.ford.sparepartbooking.enums.UserRole;
import com.ford.sparepartbooking.exception.UserRoleNotFoundException;
import java.util.Optional;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRole, String> {

  @Override
  public String convertToDatabaseColumn(UserRole attribute) {
    return Optional.ofNullable(attribute)
        .map(UserRole::getRole)
        .orElseThrow(() -> new UserRoleNotFoundException(null));
  }

  @Override
  public UserRole convertToEntityAttribute(String dbData) {
    return UserRole.of(dbData);
  }
}
