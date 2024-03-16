package ca.mcgill.ecse321.sportCenterRegistration.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportCenterRegistration.model.Registration;
import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;
import ca.mcgill.ecse321.sportCenterRegistration.model.Session;
import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.Account;

@SpringBootTest
public class RegistrationRepositoryTest{
    @Autowired
    private RegistrationRepository registrationRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private SessionRepository sessionRepo;
    @Autowired
    private SportClassRepository sportClassRepo;
    @Autowired
    private InstructorRepository instructorRepo;
    @Autowired
    private AccountRepository accountRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        registrationRepo.deleteAll();
        sessionRepo.deleteAll();
        sportClassRepo.deleteAll();
        instructorRepo.deleteAll();
        customerRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadRegistration(){
        // create a sport class
        String name = "cardio";
        SportClass sportClass = new SportClass(name);

        // create a instructor
        String instructorUsername = "instructor";
        String instructorEmail = "instructor@gmail.com";
        String instructorPassword = "instructor";
        Instructor instructor = new Instructor(instructorUsername, instructorEmail, instructorPassword);

        // create a session
        Time startTime = Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("11:00:00");
        String location = "trottbuilding";
        Date date = Date.valueOf("2024-02-13");
        Session session = new Session(startTime,endTime,location,date, instructor, sportClass);

        // create a customer
        String customerUsername = "customer";
        String customerEmail = "customer@gmail.com";
        String customerPassword = "customer";
        Customer customer = new Customer(customerUsername, customerEmail, customerPassword);

        // create a registration
        Date registrationDate = Date.valueOf("2024-02-10");
        Registration registration = new Registration(registrationDate, customer, session); // Registration(Date aDate, Account aAccount, Session aSession)

        // saving into database
        sportClass = sportClassRepo.save(sportClass);
        instructor = instructorRepo.save(instructor);
        session = sessionRepo.save(session);
        customer = customerRepo.save(customer);
        registration = registrationRepo.save(registration);

        // reading from database
        Registration result = registrationRepo.findRegistrationById(registration.getId());

        // check objects
        assertNotNull(result);

        // check attributes
        assertEquals(registration.getId(), result.getId());
        assertEquals(registration.getDate(), result.getDate());
        assertEquals(registration.getAccount().getId(), result.getAccount().getId());
        assertEquals(registration.getSession().getId(), result.getSession().getId());

        // check references
        Account resultAccount = accountRepo.findAccountByUsername(customerUsername);
        Session resultSession = sessionRepo.findSessionById(session.getId());

        assertNotNull(resultAccount);
        assertNotNull(resultSession);
        assertEquals(registration.getAccount().getId(), resultAccount.getId());
        assertEquals(registration.getSession().getId(), resultSession.getId());
    }
}