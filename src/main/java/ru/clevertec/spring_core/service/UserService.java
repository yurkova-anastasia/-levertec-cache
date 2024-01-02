package ru.clevertec.spring_core.service;



import ru.clevertec.spring_core.dto.UserRequestDto;
import ru.clevertec.spring_core.dto.UserResponseDto;

import java.util.List;

/**
 * Service interface for managing users.
 *
 * @author Yurkova Anastacia
 */
public interface UserService {

    UserResponseDto findById(Long id);

    List<UserResponseDto> findAll(int limit, int offset);

    UserResponseDto save(UserRequestDto user);

    boolean updateById(Long id, UserRequestDto userDto);

    boolean deleteById(Long id);
}
