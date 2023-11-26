package util.pdf.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.clevertec.cache.dto.UserResponseDto;
import ru.clevertec.cache.exception.PdfPrinterException;
import ru.clevertec.cache.util.impl.UserPdfPrinter;

import java.io.File;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserPdfPrinterTest {

    @Test
    void print_ProducesPdfForUser_CreatesPdfFileSuccessfully() {
        // Given
        UserPdfPrinter printer = new UserPdfPrinter();
        UserResponseDto userResponseDto = new UserResponseDto(1L, "name", "surname", 12, LocalDate.now());

        // When
        assertDoesNotThrow(() -> printer.print(userResponseDto));

        // Then
        String filePath = System.getProperty("user.dir") + "/userPdf/UserPDF1.pdf";
        File pdfFile = new File(filePath);

        Assertions.assertTrue((pdfFile).exists());

        Assertions.assertTrue((pdfFile.getName()).endsWith(".pdf"));
    }

    @Test
    void print_WithNullUserDto_ThrowsPdfPrinterException() {
        // Given
        UserPdfPrinter printer = new UserPdfPrinter();

        // When/Then
        PdfPrinterException exception = assertThrows(PdfPrinterException.class, () -> printer.print(null));
        assertEquals("Unable to print PDF document for this user", exception.getMessage());
    }
}
