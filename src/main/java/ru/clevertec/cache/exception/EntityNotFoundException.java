package ru.clevertec.cache.exception;

/**
 * An exception that indicates the entity was not found.
 * This exception is typically thrown when an operation or query attempts to access an entity that does not exist.
 *
 * @author Yurkova Anastacia
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
