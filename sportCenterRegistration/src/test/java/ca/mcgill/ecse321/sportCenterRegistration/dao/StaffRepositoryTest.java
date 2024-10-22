package ca.mcgill.ecse321.sportCenterRegistration.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    @Autowired
    private OwnerRepository repoOwner;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
        repoOwner.deleteAll();
    }

    @Test
    public void testCreateAndReadStaff() {
        // create staff
        String username = "Admin";
        String email = "admin@gmail.com";
        String password = "admin123";
        Owner stephenOwner = new Owner(username, email, password);
        Staff stephenStaff = stephenOwner;
        // Save in the database
        stephenOwner = repoOwner.save(stephenOwner);
        int stephenId = stephenOwner.getId();

        // Read from database
        // Staff result = repo.findStaffById(stephenId);
        Staff resultStaff = repo.findStaffByUsername(username);

        // check objects
        assertNotNull(resultStaff);

        // check attributes
        assertEquals(stephenId , resultStaff.getId());
        assertEquals(username, resultStaff.getUsername());
        assertEquals(email, resultStaff.getEmail());
        assertEquals(password, resultStaff.getPassword());

        // check reference
        Owner resultOwner = repoOwner.findOwnerByUsername(username);
        assertNotNull(resultOwner);
        assertEquals(resultStaff.getId(), resultOwner.getId());

    }
}