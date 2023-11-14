package ru.clevertec.cache.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Represents a User entity with properties such as name, surname, birthdate, and active status.
 *
 * @author Yurkova Anastacia
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User extends BaseEntity{

    private String name;
    private String surname;
    private Integer age;
    private LocalDate birthdate;
    private Boolean active;

}
