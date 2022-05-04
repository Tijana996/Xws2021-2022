package app.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDTO {
    private String id;

    private String content;

    private LocalDateTime timestamp;

    private String userId;
    private String userName;
    private String userLastName;
}
