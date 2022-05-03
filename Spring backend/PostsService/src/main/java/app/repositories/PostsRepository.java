package app.repositories;

import app.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PostsRepository extends MongoRepository<Post, String> {

    Optional<List<Post>> findPostsByUserId(String userId);
}
