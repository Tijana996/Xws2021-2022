package app.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowingRequestExtendedDTO {
    private String userToFollowId;
    private String currentUserId;
    private String currentUserName;
    private String currentUserLastName;
    private boolean isProfilePrivate;
}
