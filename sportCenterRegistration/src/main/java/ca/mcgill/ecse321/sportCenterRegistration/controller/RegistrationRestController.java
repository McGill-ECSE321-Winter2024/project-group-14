package ca.mcgill.ecse321.sportCenterRegistration.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportCenterRegistration.dto.RegistrationDTO;
import ca.mcgill.ecse321.sportCenterRegistration.dto.RegistrationDTO;
import ca.mcgill.ecse321.sportCenterRegistration.model.Registration;
import ca.mcgill.ecse321.sportCenterRegistration.model.Registration;
import ca.mcgill.ecse321.sportCenterRegistration.service.RegistrationService;
import ca.mcgill.ecse321.sportCenterRegistration.service.RegistrationService;


@CrossOrigin(origins = "*")
@RestController
public class RegistrationRestController {
    @Autowired
    public RegistrationService RegistrationService;


    /*
     * 
     * @author Muhammad Hammad
     * 
     * method that converts a Registration object to a Registration dto
     * 
     */
    private RegistrationDTO convertToDTO(Registration registration){
        if (Registration == null) {
            throw new IllegalArgumentException("There is no such Registration!");
        }
        RegistrationDTO RegistrationDTO = new RegistrationDTO(registration.getDate(), registration.getAccount(), registration.getSession());
        return RegistrationDTO;
    }

    /*
     * 
     * @author Muhammad Hammad
     * 
     * method that converts a list of Registrations into a list of Registration dtos
     * 
     */
    private List<RegistrationDTO> convertListToDto(List<Registration> listRegistration){
        List<RegistrationDTO> listRegistrationDTO = new ArrayList<RegistrationDTO>(listRegistration.size());
        for (Registration Registration: listRegistration) {
            listRegistrationDTO.add(convertToDTO(Registration));
        }
        return listRegistrationDTO;
    }




    /*
     * 
     * @author Muhammad Hammad
     * 
     * Controller method that creates a new Registration object
     * 
     * 
     */

    @PostMapping(value= {"/Registration/{date}/{account}/{session}", "/Registration/{date}/{account}/{session}/"})
    public ResponseEntity<?> createRegistration(@PathVariable("username") String username, @PathVariable("email") String email, @PathVariable("password") String password) throws IllegalArgumentException {
        try {
            Registration Registration = RegistrationService.createRegistration(username, email, password);
            return ResponseEntity.ok(convertToDTO(Registration));
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }
    /*
     * 
     * @author Muhammad Hammad
     * 
     * Controller method that returns a Registration object by taking its username
     * 
     * 
     */

    @GetMapping(value= {"/Registration/{username}", "/Registration/{username}/"})
    public ResponseEntity<?> getRegistration(@PathVariable("username") String username) throws IllegalArgumentException {
        try {
            Registration Registration = RegistrationService.getRegistration(username);
            return ResponseEntity.ok(convertToDTO(Registration));
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }

    @GetMapping(value= {"/Registration/all", "/Registration/all/"})
    public ResponseEntity<?> getAllRegistrations() throws IllegalArgumentException {
        try {
            List<Registration> Registrations = RegistrationService.getAllRegistrations();
            
            return ResponseEntity.ok(convertListToDto(Registrations));
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }


    /*
     * 
     * @author Muhammad Hammad
     * 
     * Controller method that deletes a Registration with a given username
     * 
     * 
     * 
     */



    @DeleteMapping(value= {"/Registration/{username}", "/Registration/{username}/"})
    public ResponseEntity<?> deleteRegistration(@PathVariable("username") String username) throws IllegalArgumentException {
        try {
            Registration RegistrationDelete = RegistrationService.deleteRegistration(username);
            return ResponseEntity.ok(convertToDTO(RegistrationDelete));
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }


    /*
     * @author Muhammad Hammad
     * 
     * Controller method that updates the information of a Registration
     * 
     */

    @PutMapping(value= {"/Registration/update/{oldUsername}/{username}/{email}/{password}", "/Registration/update/{oldUsername}/{username}/{email}/{password}/"})
    public ResponseEntity<?> updateRegistration(@PathVariable("oldUsername") String oldUsername, @PathVariable("username") String username, @PathVariable("email") String email, @PathVariable("password") String password) throws IllegalArgumentException {
        try {
            Registration Registration = RegistrationService.updateRegistration(oldUsername, username, email, password);
            return ResponseEntity.ok(convertToDTO(Registration));
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }