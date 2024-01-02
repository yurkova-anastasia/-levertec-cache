package ru.clevertec.spring_core.util.pdf;

import ru.clevertec.spring_core.dto.UserResponseDto;


public interface UserPdfPrinter {
    void print(UserResponseDto userResponseDto);

}
