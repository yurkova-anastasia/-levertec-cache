package ru.clevertec.cache.util.pdf;

import com.itextpdf.text.Document;
import ru.clevertec.cache.dto.UserResponseDto;


public interface UserPdfPrinter {
    void print(UserResponseDto userResponseDto);

}
