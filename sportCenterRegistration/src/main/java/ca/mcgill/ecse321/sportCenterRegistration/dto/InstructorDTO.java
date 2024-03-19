package ca.mcgill.ecse321.sportCenterRegistration.dto;

public class InstructorDTO {
    private int id;
    private String username;
    private String email;
    private String password;


    public InstructorDTO() {

    }
    
    public InstructorDTO(int id, String username, String email, String password) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.password = password;
    }

    public int getInstructorId() {
        return this.id;
    }
    
    public String getInstructorUsername() {
        return this.username;
    }
    
    public String getInstructorEmail() {
        return this.email;
    }
    
    public String getInstructorPassword() {
        return this.password;
    }
}


