package ca.mcgill.ecse321.sportCenterRegistration.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportCenterRegistration.model.Owner;
import ca.mcgill.ecse321.sportCenterRegistration.model.Staff;

@SpringBootTest
public class StaffRepositoryTest {

    @Autowired
    private StaffRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateAndReadStaff() {
        // create staff
        String username = "Stephen";
        String email = "stephen@gmail.com";
        String password = "stephen123";
        Staff stephen = new Staff(username, email, password);

        // Save in the database
        stephen = repo.save(stephen);
        int stephenId = stephen.getId();

        // Read from database
        Staff result = repo.findStaffById(stephenId);// error here

        assertNotNull(result);
        assertEquals(stephenId, result.getId());
        assertEquals(username, result.getUsername());
        assertEquals(email, result.getEmail());
        assertEquals(password, result.getPassword());

    }
}