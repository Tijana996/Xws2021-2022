package app.dtos;

import lombok.Data;

@Data
public class ReactionRequestDTO {
    private String postId;
    private String userId;
    private String userName;
    private String userLastName;
}
