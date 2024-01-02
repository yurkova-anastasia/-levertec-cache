package ru.clevertec.spring_core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.spring_core.dto.UserRequestDto;
import ru.clevertec.spring_core.dto.UserResponseDto;
import ru.clevertec.spring_core.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserResponseDto getById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping
    public List<UserResponseDto> getAll(
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "0") int page) {
        return userService.findAll(size, page);
    }

    @PostMapping
    public String createUser(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.save(userRequestDto);
        return "/users/" + userResponseDto.id();
    }

    @PutMapping("/{id}")
    public boolean update(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        return userService.updateById(id, userRequestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }

}
