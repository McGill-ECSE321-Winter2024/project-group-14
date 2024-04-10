package ca.mcgill.ecse321.sportCenterRegistration.dto;

public abstract class AccountDTO {
    private int id;
    private String username;
    private String email;
    private String password;

    public AccountDTO() {

    }

    public AccountDTO(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
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
}
