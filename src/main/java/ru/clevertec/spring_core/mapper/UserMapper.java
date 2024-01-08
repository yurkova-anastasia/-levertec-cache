package ru.clevertec.spring_core.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.clevertec.spring_core.dto.UserRequestDto;
import ru.clevertec.spring_core.dto.UserResponseDto;
import ru.clevertec.spring_core.model.User;

import java.util.List;

/**
 * Mapper interface responsible for mapping between User entities and their corresponding DTOs.
 * It provides methods for converting User objects to UserResponseDto objects and vice versa.
 * Additionally, it supports mapping lists of User entities to lists of UserResponseDto objects.
 *
 * @author Yurkova Anastacia
 * @see User
 * @see UserResponseDto
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toDto(User user);

    User fromDto(UserRequestDto dto);

    List<UserResponseDto> toListOfDto(List<User> users);
}
