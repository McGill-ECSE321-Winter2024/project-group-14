package ca.mcgill.ecse321.sportCenterRegistration.dto;

public class SportClassDto{
    private String name;
    private boolean approved;

    public SportClassDto(String name, boolean approved){
        this.name = name;
        this.approved = approved;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setApproved(boolean approved){
        this.approved = approved;
    }

    public String getName()
    {
        return name;
    }

    public boolean getApproved()
    {
        return approved;
    }

}