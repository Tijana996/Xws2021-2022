package app.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
public class Post {

    @Id
    private String id;

    private String content;

    private LocalDateTime timestamp;

    private String userId;

    private String userName;

    private String userLastName;

    private List<Comment> comments;

    private List<UserInReaction> likes;

    private List<UserInReaction> dislikes;
}
