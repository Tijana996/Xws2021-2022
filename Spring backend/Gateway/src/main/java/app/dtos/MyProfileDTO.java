package app.dtos;

import lombok.Data;

import java.util.List;

@Data
public class MyProfileDTO {
    private String name;
    private String lastName;
    private boolean privateProfile;
    private List<PostDTO> posts;
    private String followingStatus;
    private List<FollowerDTO> requests;
}
