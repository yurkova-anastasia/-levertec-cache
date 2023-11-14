package ru.clevertec.cache.service;



import ru.clevertec.cache.dto.UserRequestDto;
import ru.clevertec.cache.dto.UserResponseDto;

import java.util.List;

/**
 * Service interface for managing users.
 *
 * @author Yurkova Anastacia
 */
public interface UserService {

    UserResponseDto findById(Long id);

    List<UserResponseDto> findAll();

    UserResponseDto save(UserRequestDto user);

    boolean updateById(Long id, UserRequestDto userDto);

    boolean deleteById(Long id);
}
