package app.dtos;

import lombok.Data;

import java.util.List;

@Data
public class MyProfileDTO {
    private String name;
    private String lastName;
    private List<PostDTO> posts;
}
