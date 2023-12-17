package ru.clevertec.cache.dto;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * A data transfer object (DTO) representing a request to create a new user.
 * This DTO includes details such as the user's name, surname, and birthdate.
 * It is used to transfer data from the client to the server when creating a new user.
 *
 * @author Yurkova Anastacia
 */
public record UserRequestDto(
        String name,
        String surname,
        Integer age,
        LocalDate birthdate
) {

}
