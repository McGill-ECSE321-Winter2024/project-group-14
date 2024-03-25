package ca.mcgill.ecse321.sportCenterRegistration.dto;

import ca.mcgill.ecse321.sportCenterRegistration.model.Account;
import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.Registration;
import ca.mcgill.ecse321.sportCenterRegistration.model.Session;

import ca.mcgill.ecse321.sportCenterRegistration.dto.SessionDTO;
import ca.mcgill.ecse321.sportCenterRegistration.dto.AccountDTO;

import java.sql.Date;

import java.sql.Time;
import java.util.List;

public class RegistrationDTO{
    //we don't know if the registration will be by a customer, owner, or instructor.
    private AccountDTO registeringAccount;

    private Date registrationDate;
    private SessionDTO session;

    @SuppressWarnings("unused")
    private List<String> errors;

    public RegistrationDTO(String error) {
        this.errors = List.of(error);
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
    
    public RegistrationDTO(){

    }

    public RegistrationDTO(Date date, Account account, Session session){
        this.setAccount(account);

        //clone the date so that the user can't alter the original date object
        this.registrationDate = (Date) date.clone();

        this.setSession(session);
    }

    public AccountDTO getAccount(){
        return registeringAccount;
    }

    public Date getDate(){
        return registrationDate;
    }

    public SessionDTO getSession(){
        return session;
    }

    public void setDate(Date newDate){
        this.registrationDate = (Date) newDate.clone();
    }

    public void setAccount(Account account){
        if(account == null){
            this.registeringAccount = null;
        }
        else if(account instanceof Customer){
            this.registeringAccount = new CustomerDTO(account.getId(), account.getUsername(), account.getEmail(), account.getPassword());
        }
        else if(account instanceof Instructor){
            this.registeringAccount = new InstructorDTO(account.getId(), account.getUsername(), account.getEmail(), account.getPassword());
        }
        else{
            this.registeringAccount = new OwnerDTO(account.getId(), account.getUsername(), account.getEmail(), account.getPassword());
        }
    }

    public void setSession(Session session){
        this.session = new SessionDTO(
            (Date)session.getDate().clone(), (Time) session.getStartTime().clone(), (Time) session.getEndTime().clone(), session.getId(), session.getLocation(), session.getInstructor(), session.getSportClass()
        );

    }
}
