package app.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Following {

    @Id
    private String id;
    private String userId;
    private List<Follower> following;
    private List<Follower> followers;
    private List<Follower> requests;

}
