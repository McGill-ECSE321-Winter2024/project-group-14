package ca.mcgill.ecse321.sportCenterRegistration.dto;

public abstract class AccountDTO {
    private int id;
    private String username;
    private String email;
    private String password;
    private String type;

    public AccountDTO() {

    }

    public AccountDTO(int id, String username, String email, String password, String type) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getType() {
        return this.type;
    }
}
