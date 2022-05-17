package app.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private String id;
    private String content;
    private LocalDateTime timestamp;
    private String userId;
    private String userName;
    private String userSurname;
}
