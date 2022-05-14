package app.dtos;

import app.models.UserInReaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReactionsAfterUpdateDTO {
    private List<UserInReaction> likes;
    private List<UserInReaction> dislikes;
}
