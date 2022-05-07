package app.dtos;

import lombok.Data;

@Data
public class CommentRequestDTO {
    private String postId;
    private String content;
    private String userId;
    private String userName;
    private String userSurname;
}
