package ca.mcgill.ecse321.sportCenterRegistration.controller;

import java.util.ArrayList;
import java.util.List;

// import org.checkerframework.checker.units.qual.A; // I don't know what this is, so I just commented it out
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportCenterRegistration.dto.InstructorDTO;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.service.InstructorService;

@CrossOrigin(origins = "*")
@RestController
public class InstructorRestController {
    @Autowired
    public InstructorService InstructorService;

    /*
     * 
     * @author Muhammad Hammad
     * 
     * method that converts a Instructor object to a Instructor dto
     * 
     */
    private InstructorDTO convertToDTO(Instructor Instructor) {
        if (Instructor == null) {
            throw new IllegalArgumentException("There is no such Instructor!");
        }
        InstructorDTO InstructorDTO = new InstructorDTO(Instructor.getId(), Instructor.getUsername(),
                Instructor.getEmail(), Instructor.getPassword(), "Instructor");
        return InstructorDTO;
    }

    /*
     * 
     * @author Muhammad Hammad
     * 
     * method that converts a list of Instructors into a list of Instructor dtos
     * 
     */
    private List<InstructorDTO> convertListToDto(List<Instructor> listInstructor) {
        List<InstructorDTO> listInstructorDTO = new ArrayList<InstructorDTO>(listInstructor.size());
        for (Instructor Instructor : listInstructor) {
            listInstructorDTO.add(convertToDTO(Instructor));
        }
        return listInstructorDTO;
    }

    /*
     * 
     * @author Muhammad Hammad
     * 
     * Controller method that creates a new Instructor object
     * 
     * 
     */

    @PostMapping(value = { "/instructor", "/instructor/" })
    public ResponseEntity<?> createInstructor(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password)
            throws IllegalArgumentException {
        try {
            Instructor Instructor = InstructorService.createInstructor(username, email, password);
            return ResponseEntity.ok(convertToDTO(Instructor));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    /*
     * 
     * @author Muhammad Hammad
     * 
     * Controller method that returns a Instructor object by taking its username
     * 
     * 
     */

    @GetMapping(value = { "/instructor/{username}", "/instructor/{username}/" })
    public ResponseEntity<?> getInstructor(@PathVariable("username") String username) throws IllegalArgumentException {
        try {
            Instructor Instructor = InstructorService.getInstructor(username);
            return ResponseEntity.ok(convertToDTO(Instructor));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = { "/instructor/all", "/instructor/all/" })
    public ResponseEntity<?> getAllInstructors() throws IllegalArgumentException {
        try {
            List<Instructor> Instructors = InstructorService.getAllInstructors();

            return ResponseEntity.ok(convertListToDto(Instructors));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*
     * 
     * @author Muhammad Hammad
     * 
     * Controller method that deletes a Instructor with a given username
     * 
     * 
     * 
     */

    @DeleteMapping(value = { "/instructor/{username}", "/instructor/{username}/" })
    public ResponseEntity<?> deleteInstructor(@PathVariable("username") String username)
            throws IllegalArgumentException {
        try {
            Instructor instructorDelete = InstructorService.deleteInstructor(username);
            return ResponseEntity.ok(convertToDTO(instructorDelete));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*
     * @author Muhammad Hammad
     * 
     * Controller method that updates the information of a Instructor
     * 
     */

    @PutMapping(value = { "/instructor/update/{oldUsername}/{username}/{email}/{password}",
            "/instructor/update/{oldUsername}/{username}/{email}/{password}/" })
    public ResponseEntity<?> updateInstructor(@PathVariable("oldUsername") String oldUsername,
            @PathVariable("username") String username, @PathVariable("email") String email,
            @PathVariable("password") String password) throws IllegalArgumentException {
        try {
            Instructor Instructor = InstructorService.updateInstructor(oldUsername, username, email, password);
            return ResponseEntity.ok(convertToDTO(Instructor));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
