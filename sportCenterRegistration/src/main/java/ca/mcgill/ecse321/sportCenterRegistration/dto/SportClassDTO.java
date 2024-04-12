package ca.mcgill.ecse321.sportCenterRegistration.dto;

public class SportClassDTO {
    private int id;
    private String name;
    private boolean approved;

    public SportClassDTO(int id, String name, boolean approved) {
        this.id = id;
        this.name = name;
        this.approved = approved;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean getApproved() {
        return approved;
    }

}