package ca.mcgill.ecse321.sportCenterRegistration.dto;

public class OwnerDTO extends AccountDTO {
    private int id;
    private String username;
    private String email;
    private String password;

    public OwnerDTO() {

    }

    public OwnerDTO(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getOwnerId() {
        return this.id;
    }

    public String getOwnerUsername() {
        return this.username;
    }

    public String getOwnerEmail() {
        return this.email;
    }

    public String getOwnerPassword() {
        return this.password;
    }
}
