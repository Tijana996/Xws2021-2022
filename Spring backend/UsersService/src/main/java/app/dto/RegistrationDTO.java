package app.dto;

import lombok.Data;

@Data
public class RegistrationDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
