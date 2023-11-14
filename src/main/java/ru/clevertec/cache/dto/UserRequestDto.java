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
    private static final String NAME_PATTERN = "[A-Za-z]+";
    private static final String SURNAME_PATTERN = "[A-Za-z]+";
    private static final Pattern NAME_REGEX = Pattern.compile(NAME_PATTERN);
    private static final Pattern SURNAME_REGEX = Pattern.compile(SURNAME_PATTERN);
    private static final int MIN_AGE = 0;

    public UserRequestDto {
        validateName(name);
        validateSurname(surname);
        validateAge(age);
    }

    private static void validateName(String name) {
        if (name == null || !NAME_REGEX.matcher(name).matches()) {
            throw new IllegalArgumentException("Invalid name format");
        }
    }

    private static void validateSurname(String surname) {
        if (surname == null || !SURNAME_REGEX.matcher(surname).matches()) {
            throw new IllegalArgumentException("Invalid surname format");
        }
    }

    private static void validateAge(Integer age) {
        if (age == null || age < MIN_AGE) {
            throw new IllegalArgumentException("Invalid age");
        }
    }

}
