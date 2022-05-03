package app.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Post {

    @Id
    private String id;

    private String content;

    private LocalDateTime timestamp;

    private String userId;
}
