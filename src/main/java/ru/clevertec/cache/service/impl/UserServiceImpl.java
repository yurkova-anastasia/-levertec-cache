package ru.clevertec.cache.service.impl;

import org.mapstruct.factory.Mappers;
import ru.clevertec.cache.dto.UserRequestDto;
import ru.clevertec.cache.dto.UserResponseDto;
import ru.clevertec.cache.exception.EntityNotFoundException;
import ru.clevertec.cache.mapper.UserMapper;
import ru.clevertec.cache.model.User;
import ru.clevertec.cache.repository.UserRepository;
import ru.clevertec.cache.service.UserService;

import java.util.List;

/**
 * Service implementation for managing users.
 *
 * @author Yurkova Anastacia
 */
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Finds a user by their unique identifier (ID).
     *
     * @param id The unique identifier of the user.
     * @return The UserResponseDto representing the found user.
     * @throws EntityNotFoundException If no user with the given ID is found.
     */
    @Override
    public UserResponseDto findById(Long id) {
        return userMapper.toDto(
                userRepository.findById(id).orElseThrow(() ->
                        new EntityNotFoundException("User with id = " + id + " was not found")));
    }

    /**
     * Retrieves a list of users with pagination support.
     *
     * @return A list of UserResponseDto representing users.
     */
    @Override
    public List<UserResponseDto> findAll(int limit, int offset) {
        return userMapper.toListOfDto(userRepository.findAll(limit, offset));
    }

    /**
     * Saves a new user based on the provided UserRequestDto.
     *
     * @param userDto The UserRequestDto containing user information.
     * @return The UserResponseDto representing the saved user.
     */
    @Override
    public UserResponseDto save(UserRequestDto userDto) {
        validateUserRequestDto(userDto);
        User user = userMapper.fromDto(userDto);
        return userMapper.toDto(userRepository.save(user));
    }

    /**
     * Updates an existing user based on the provided UserRequestDto.
     *
     * @param id      The unique identifier of the user to update.
     * @param userDto The UserRequestDto containing updated user information.
     * @return True if the user was successfully updated, false otherwise.
     * @throws EntityNotFoundException If no user with the given ID is found.
     */
    @Override
    public boolean updateById(Long id, UserRequestDto userDto) {
        validateUserRequestDto(userDto);
        userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User with id = " + id + " was not found"));
        User user = userMapper.fromDto(userDto);
        user.setId(id);
        return userRepository.update(user);
    }

    /**
     * Deletes a user by their unique identifier (ID).
     *
     * @param id The unique identifier of the user to delete.
     * @return True if the user was successfully deleted, false otherwise.
     * @throws EntityNotFoundException If no user with the given ID is found.
     */
    @Override
    public boolean deleteById(Long id) {
        userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User with id = " + id + " was not found"));
        return userRepository.delete(id);
    }

    private void validateUserRequestDto(UserRequestDto userDto) {
        try {
            new UserRequestDto(
                    userDto.name(),
                    userDto.surname(),
                    userDto.age(),
                    userDto.birthdate()
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UserRequestDto: " + e.getMessage());
        }
    }

}
