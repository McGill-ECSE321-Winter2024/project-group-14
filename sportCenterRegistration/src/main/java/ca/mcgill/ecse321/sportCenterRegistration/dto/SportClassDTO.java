package ca.mcgill.ecse321.sportCenterRegistration.dto;

public class SportClassDTO {
    private String name;
    private boolean approved;

    public SportClassDTO(String name, boolean approved) {
        this.name = name;
        this.approved = approved;
    }

    public String getName() {
        return name;
    }

    public boolean getApproved() {
        return approved;
    }

}
