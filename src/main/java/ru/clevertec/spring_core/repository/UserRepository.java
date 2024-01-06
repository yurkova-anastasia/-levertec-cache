package ru.clevertec.spring_core.repository;

import org.springframework.stereotype.Repository;
import ru.clevertec.spring_core.annotation.CustomCacheable;
import ru.clevertec.spring_core.connection.DataSourceProvider;
import ru.clevertec.spring_core.exception.RepositoryException;
import ru.clevertec.spring_core.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository class for managing operations on user entities.
 *
 * @author Yurkova Anastacia
 * @see User
 */
@Repository
public class UserRepository {

    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM users WHERE active = TRUE AND id = ?";

    private static final String SELECT_ALL_QUERY = "SELECT * FROM users WHERE active = TRUE LIMIT ? OFFSET ?";

    private static final String INSERT_QUERY = """
                INSERT INTO users (name, surname, age, birthdate, active) VALUES (?, ?, ?,  ?, ?)
            """;

    private static final String UPDATE_QUERY = """
                UPDATE users SET name = ?, surname = ?, age = ?, birthdate = ?, active = ? WHERE id = ?
            """;

    private static final String DELETE_QUERY = "UPDATE users SET active = FALSE WHERE id = ?";

    private DataSource dataSource;

    public UserRepository() {
        this.dataSource = DataSourceProvider.getDataSource();
    }

    /**
     * Saves an entity to the repository.
     *
     * @param user The entity to be saved.
     * @return The saved entity with an assigned id.
     * @throws RepositoryException If there is an error during the repository operation.
     * @author Yurkova Anastacia
     */
    @CustomCacheable
    public User save(User user) throws RepositoryException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     INSERT_QUERY,
                     Statement.RETURN_GENERATED_KEYS
             )
        ) {
            settingPreparedStatement(preparedStatement, user);
            int value = preparedStatement.executeUpdate();
            if (value == 1) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        user.setId(resultSet.getLong(1));
                    }
                }
            }
            return user;
        } catch (Exception ex) {
            throw new RepositoryException(user.getClass().getSimpleName() + " was not added [" + ex.getMessage() + "]");
        }
    }

    /**
     * Retrieves an entity by its unique identifier (id).
     *
     * @param id The unique identifier (id) of the entity to retrieve.
     * @return An Optional containing the retrieved entity if found, or empty if not found.
     * @throws RepositoryException If there is an error during the repository operation.
     * @author Yurkova Anastacia
     */
    @CustomCacheable
    public Optional<User> findById(Long id) throws RepositoryException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY)
        ) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.of(construct(resultSet)) : Optional.empty();
            }
        } catch (Exception ex) {
            throw new RepositoryException("The entity was not found[" + ex.getMessage() + "]");
        }
    }

    /**
     * Retrieves a list of entities with optional limits and offsets.
     *
     * @return A list of retrieved entities.
     * @throws RepositoryException If there is an error during the repository operation.
     * @author Yurkova Anastacia
     */
    @CustomCacheable
    public List<User> findAll(int limit, int offset) throws RepositoryException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY)
        ) {
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<User> found = new ArrayList<>();
                while (resultSet.next()) {
                    found.add(construct(resultSet));
                }
                return found;
            }
        } catch (Exception ex) {
            throw new RepositoryException("The entities were not found[" + ex.getMessage() + "]");
        }
    }

    /**
     * Updates an existing entity in the repository.
     *
     * @param user The entity to be updated.
     * @return True if the entity was successfully updated, false otherwise.
     * @throws RepositoryException If there is an error during the repository operation.
     * @author Yurkova Anastacia
     */
    @CustomCacheable
    public boolean update(User user) throws RepositoryException {
        int idQueryIndex = findIdPosition();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)
        ) {
            settingPreparedStatement(preparedStatement, user);
            preparedStatement.setLong(idQueryIndex, user.getId());
            return preparedStatement.executeUpdate() == 1;
        } catch (Exception ex) {
            throw new RepositoryException(user.getClass().getSimpleName() + " was not updated [" + ex.getMessage() + "]");
        }
    }

    /**
     * Deletes an entity from the repository by its unique identifier (id).
     *
     * @param id The unique identifier (id) of the entity to delete.
     * @return True if the entity was successfully deleted, false otherwise.
     * @throws RepositoryException If there is an error during the repository operation.
     * @author Yurkova Anastacia
     */
    @CustomCacheable
    public boolean delete(Long id) throws RepositoryException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)
        ) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (Exception ex) {
            throw new RepositoryException("The entity was not deleted [" + ex.getMessage() + "]");
        }
    }

    /**
     * Utility method to find the position of '?' placeholders in a SQL query.
     *
     * @return The count of '?' placeholders in the query.
     * @author Yurkova Anastacia
     */
    private int findIdPosition() {
        return (int) UserRepository.UPDATE_QUERY.chars()
                .filter(charId -> charId == '?')
                .count();
    }

    private User construct(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setAge(resultSet.getInt("age"));
        user.setBirthdate(resultSet.getDate("birthdate").toLocalDate());
        user.setActive(resultSet.getBoolean("active"));
        return user;
    }

    private void settingPreparedStatement(PreparedStatement preparedStatement, User element) throws SQLException {
        preparedStatement.setString(1, element.getName());
        preparedStatement.setString(2, element.getSurname());
        preparedStatement.setInt(3, element.getAge());
        preparedStatement.setDate(4, Date.valueOf(element.getBirthdate()));
        preparedStatement.setBoolean(5, true);
    }

}
