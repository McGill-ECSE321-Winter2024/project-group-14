package ca.mcgill.ecse321.sportCenterRegistration.controller;

import java.util.ArrayList;
import java.util.List;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportCenterRegistration.dto.OwnerDTO;
import ca.mcgill.ecse321.sportCenterRegistration.model.Owner;
import ca.mcgill.ecse321.sportCenterRegistration.service.OwnerService;


@CrossOrigin(origins = "*")
@RestController
public class OwnerRestController {
    @Autowired
    public OwnerService OwnerService;


    /*
     * 
     * @author Muhammad Hammad
     * 
     * method that converts a Owner object to a Owner dto
     * 
     */
    private OwnerDTO convertToDTO(Owner Owner){
        if (Owner == null) {
            throw new IllegalArgumentException("There is no such Owner!");
        }
        OwnerDTO OwnerDTO = new OwnerDTO(Owner.getId(), Owner.getUsername(), Owner.getEmail(), Owner.getPassword());
        return OwnerDTO;
    }

    /*
     * 
     * @author Muhammad Hammad
     * 
     * method that converts a list of Owners into a list of Owner dtos
     * 
     */
    private List<OwnerDTO> convertListToDto(List<Owner> listOwner){
        List<OwnerDTO> listOwnerDTO = new ArrayList<OwnerDTO>(listOwner.size());
        for (Owner Owner: listOwner) {
            listOwnerDTO.add(convertToDTO(Owner));
        }
        return listOwnerDTO;
    }




    /*
     * 
     * @author Muhammad Hammad
     * 
     * Controller method that creates a new Owner object
     * 
     * 
     */

    @PostMapping(value= {"/Owner/{username}/{email}/{password}", "/Owner/{username}/{email}/{password}/"})
    public ResponseEntity<?> createOwner(@PathVariable("username") String username, @PathVariable("email") String email, @PathVariable("password") String password) throws IllegalArgumentException {
        try {
            Owner Owner = OwnerService.createOwner(username, email, password);
            return ResponseEntity.ok(convertToDTO(Owner));
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }
    /*
     * 
     * @author Muhammad Hammad
     * 
     * Controller method that returns a Owner object by taking its username
     * 
     * 
     */

    @GetMapping(value= {"/Owner/{username}", "/Owner/{username}/"})
    public ResponseEntity<?> getOwner(@PathVariable("username") String username) throws IllegalArgumentException {
        try {
            Owner Owner = OwnerService.getOwner(username);
            return ResponseEntity.ok(convertToDTO(Owner));
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }

    @GetMapping(value= {"/Owner/all", "/Owner/all/"})
    public ResponseEntity<?> getAllOwners() throws IllegalArgumentException {
        try {
            List<Owner> Owners = OwnerService.getAllOwners());
            
            return ResponseEntity.ok(convertListToDto(Owners));
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }


    /*
     * 
     * @author Muhammad Hammad
     * 
     * Controller method that deletes a Owner with a given username
     * 
     * 
     * 
     */



    @DeleteMapping(value= {"/Owner/{username}", "/Owner/{username}/"})
    public ResponseEntity<?> deleteOwner(@PathVariable("username") String username) throws IllegalArgumentException {
        try {
            Boolean deleteSucess = OwnerService.deleteOwner(username);
            return ResponseEntity.ok(deleteSucess);
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }


    /*
     * @author Muhammad Hammad
     * 
     * Controller method that updates the information of a Owner
     * 
     */

    @PutMapping(value= {"/Owner/update/{oldUsername}/{username}/{email}/{password}", "/Owner/update/{oldUsername}/{username}/{email}/{password}/"})
    public ResponseEntity<?> updateOwner(@PathVariable("oldUsername") String oldUsername, @PathVariable("username") String username, @PathVariable("email") String email, @PathVariable("password") String password) throws IllegalArgumentException {
        try {
            Owner Owner = OwnerService.updateOwner(oldUsername, username, email, password);
            return ResponseEntity.ok(convertToDTO(Owner));
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }







    



}
