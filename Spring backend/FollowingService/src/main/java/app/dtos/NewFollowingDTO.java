package app.dtos;

import lombok.Data;

@Data
public class NewFollowingDTO {
    private String userToFollowId;
    private String currentUserId;
    private String currentUserName;
    private String currentUserLastName;
    private boolean isProfilePrivate;
}
