package app.repository;

import app.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    @Query(value = "{'username': ?0}")
    Optional<User> findByUsername(String username);

    Optional<List<User>> findByNameStartsWithIgnoreCase(String name);
}
