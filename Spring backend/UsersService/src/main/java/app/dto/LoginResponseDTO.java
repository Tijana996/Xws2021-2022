package app.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String token;
    private String userId;
    private String name;
    private String lastName;

}
