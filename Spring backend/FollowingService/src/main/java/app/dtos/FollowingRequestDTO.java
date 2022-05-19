package app.dtos;

import lombok.Data;

@Data
public class FollowingRequestDTO {
    private String currentUserId;
    private String currentUserName;
    private String currentUserLastName;
    private String requestUserId;
    private String requestUserName;
    private String requestUserLastName;
}
