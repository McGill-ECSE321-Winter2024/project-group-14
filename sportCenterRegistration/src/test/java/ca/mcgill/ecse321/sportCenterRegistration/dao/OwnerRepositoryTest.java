package ca.mcgill.ecse321.sportCenterRegistration.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportCenterRegistration.model.Owner;

@SpringBootTest
public class OwnerRepositoryTest{
    @Autowired
    private OwnerRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateAndReadOwner(){
        // Create Owner
        String username = "Admin";
        String email = "admin@gmail.com";
        String password = "admin123";
        Owner admin = new Owner(username, email, password);

        // Save in the database
        admin = repo.save(admin);
        int adminId = admin.getId();

        // Read from database
//        Owner result = repo.findOwnerById(adminId);
        Owner result = repo.findOwnerByUsername(username);

        // check objects
        assertNotNull(result);
        // check attributes
        assertEquals(adminId, result.getId());
        assertEquals(username, result.getUsername());
        assertEquals(email, result.getEmail());
        assertEquals(password, result.getPassword());

    }
}