package ca.mcgill.ecse321.sportCenterRegistration.dto;

import java.util.List;

public class CustomerDTO extends AccountDTO{
    public CustomerDTO(int id, String username, String email, String password) {
        super(id, username, email, password);
    }
}
