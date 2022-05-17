package app.dtos;

import lombok.Data;


@Data
public class SavePostDTO {
    private String content;

    private String userId;

    private String userName;

    private String userLastName;

    private String link;

}
