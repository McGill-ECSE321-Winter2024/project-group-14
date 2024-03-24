package ca.mcgill.ecse321.sportCenterRegistration.controller;

import java.sql.Date;
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
import ca.mcgill.ecse321.sportCenterRegistration.model.Account;
import ca.mcgill.ecse321.sportCenterRegistration.model.Registration;
import ca.mcgill.ecse321.sportCenterRegistration.model.Session;
import ca.mcgill.ecse321.sportCenterRegistration.service.RegistrationService;

@CrossOrigin(origins = "*")
@RestController
public class RegistrationRestController {
    @Autowired
    public RegistrationService registrationService;

    /*
     * 
     * @author Muhammad Hammad
     * 
     * @author David Marji
     * method that converts a Registration object to a Registration dto
     * 
     */
    private RegistrationDTO convertToDTO(Registration registration) {
        if (registration == null) {
            throw new IllegalArgumentException("There is no such Registration!");
        }
        RegistrationDTO RegistrationDTO = new RegistrationDTO(registration.getDate(), registration.getAccount(),
                registration.getSession());
        return RegistrationDTO;
    }

    /*
     * 
     * @author Muhammad Hammad
     * 
     * method that converts a list of Registrations into a list of Registration dtos
     * 
     */
    private List<RegistrationDTO> convertListToDto(List<Registration> listRegistration) {
        List<RegistrationDTO> listRegistrationDTO = new ArrayList<RegistrationDTO>(listRegistration.size());
        for (Registration Registration : listRegistration) {
            listRegistrationDTO.add(convertToDTO(Registration));
        }
        return listRegistrationDTO;
    }

    /*
     * 
     * @author Muhammad Hammad
     * 
     * @author David Marji
     * Controller method that creates a new Registration object
     * 
     * 
     */

    @PostMapping(value = { "/registration/{account}/{session}/{date}", "/registration/{account}/{session}/{date}/" })
    public ResponseEntity<?> createRegistration(@PathVariable("date") Date date,
            @PathVariable("account") Account account,
            @PathVariable("session") Session session) throws IllegalArgumentException {

        try {
            Registration registration = registrationService.createRegistration(date, account.getUsername(),
                    session.getInstructor().getUsername(), session.getSportClass().getName(), session.getStartTime());

            return ResponseEntity.ok(convertToDTO(registration));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*
     * 
     * @author Muhammad Hammad
     * 
     * @author David Marji
     * Controller method that returns a Registration object by taking its username
     * 
     * 
     */
    @GetMapping(value = { "/Registration/{account}/{session}", "/Registration/{account}/{session}/" })
    public ResponseEntity<?> getRegistration(@PathVariable("account") Account account,
            @PathVariable("session") Session session)
            throws IllegalArgumentException {

        try {
            Registration Registration = registrationService.getRegistration(account.getUsername(),
                    session.getStartTime(), session.getInstructor().getUsername(), session.getSportClass().getName());

            return ResponseEntity.ok(convertToDTO(Registration));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = { "/Registration/all", "/Registration/all/" })
    public ResponseEntity<?> getAllRegistrations() throws IllegalArgumentException {
        try {
            List<Registration> Registrations = registrationService.getAllRegistrations();

            return ResponseEntity.ok(convertListToDto(Registrations));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*
     * 
     * @author Muhammad Hammad
     * 
     * @author David Marji
     * Controller method that deletes a Registration with a given username
     * 
     * 
     * 
     */

    @DeleteMapping(value = { "/Registration/{account}/{session}", "/Registration/{account}/{session}/" })
    public ResponseEntity<?> deleteRegistration(@PathVariable("account") Account account,
            @PathVariable("session") Session session)
            throws IllegalArgumentException {

        try {
            Boolean deleted = registrationService.deleteRegistration(account.getUsername(),
                    session.getSportClass().getName(), session.getInstructor().getUsername(), session.getStartTime());

            return ResponseEntity.ok(deleted);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*
     * @author Muhammad Hammad
     * 
     * @author David Marji
     * 
     * Controller method that updates the information of a Registration
     * 
     */

    @PutMapping(value = { "/Registration/update/{oldAccount}/{oldSession}/{account}/{session}/{date}",
            "/Registration/update/{oldAccount}/{oldSession}/{account}/{session}/{date}/" })

    public ResponseEntity<?> updateRegistration(@PathVariable("oldAccount") Account oldAccount,
            @PathVariable("oldSession") Session oldSession, @PathVariable("account") Account account,
            @PathVariable("session") Session session, @PathVariable("date") Date date) throws IllegalArgumentException {

        try {
            Registration Registration = registrationService.updateRegistration(oldAccount, oldSession, account,
                    session, date);

            return ResponseEntity.ok(convertToDTO(Registration));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}