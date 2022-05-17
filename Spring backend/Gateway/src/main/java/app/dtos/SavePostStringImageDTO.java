package app.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavePostStringImageDTO {
    private String content;

    private String userId;

    private String userName;

    private String userLastName;

    private String image;

    private String link;
}
