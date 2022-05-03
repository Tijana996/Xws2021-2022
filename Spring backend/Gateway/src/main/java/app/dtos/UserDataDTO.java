package app.dtos;

import lombok.Data;

@Data
public class UserDataDTO {
    private String Id;
    private String name;
    private String surname;
    private String phoneNumber = "";
    private String address = "";
    private String email = "";
    private String birthDate = "";
    private String education = "";
    private String workingExperience = "";
    private String hoby = "";
    public boolean privateProfile;
}
