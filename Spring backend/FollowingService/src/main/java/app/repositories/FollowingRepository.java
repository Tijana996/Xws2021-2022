package app.repositories;

import app.models.Following;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowingRepository extends MongoRepository<Following, String> {
    @Query(value = "{'userId': ?0}")
    Optional<Following> findByUserId(String userId);
}
