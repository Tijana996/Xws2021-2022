package app.dtos;

import lombok.Data;

@Data
public class PostReactionDTO {
    private String postId;
    private String userId;
    private String userName;
    private String userLastName;
}
