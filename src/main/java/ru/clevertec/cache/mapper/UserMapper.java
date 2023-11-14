package ru.clevertec.cache.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.clevertec.cache.dto.UserRequestDto;
import ru.clevertec.cache.dto.UserResponseDto;
import ru.clevertec.cache.model.User;

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
@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    UserResponseDto toDto(User user);

    User fromDto(UserRequestDto dto);

    List<UserResponseDto> toListOfDto(List<User> users);
}
