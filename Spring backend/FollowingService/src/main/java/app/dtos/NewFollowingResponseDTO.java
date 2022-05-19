package app.dtos;

import app.utils.FollowingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewFollowingResponseDTO {
    private FollowingStatus status;
}
