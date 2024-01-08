package ru.clevertec.spring_core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Base entity class representing common properties for all entities.
 * It includes an 'id' field as a unique identifier for the entity.
 *
 * @author Yurkova Anastacia
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity {

    private Long id;
}
