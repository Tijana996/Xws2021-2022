package app.dtos;

import app.models.Comment;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDTO {
    private String id;

    private String content;

    private LocalDateTime timestamp;

    private String userId;

    private String userName;

    private String userLastName;

    private List<Comment> comments;
}
