package app.dtos;

import lombok.Data;

@Data
public class CommentDTO {
    private String postId;
    private String content;
    private String userId;
    private String userName;
    private String userSurname;
}
