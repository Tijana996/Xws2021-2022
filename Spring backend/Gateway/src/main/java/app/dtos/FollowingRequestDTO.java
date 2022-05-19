package app.dtos;

import lombok.Data;

@Data
public class FollowingRequestDTO {
    private String userToFollowId;
    private String currentUserId;
    private String currentUserName;
    private String currentUserLastName;
}
