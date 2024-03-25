package ca.mcgill.ecse321.sportCenterRegistration.dto;

public class CustomerDTO extends AccountDTO {
    private int id;
    private String username;
    private String email;
    private String password;

    public CustomerDTO() {

    }

    public CustomerDTO(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getCustomerId() {
        return this.id;
    }

    public String getCustomerUsername() {
        return this.username;
    }

    public String getCustomerEmail() {
        return this.email;
    }

    public String getCustomerPassword() {
        return this.password;
    }
}
