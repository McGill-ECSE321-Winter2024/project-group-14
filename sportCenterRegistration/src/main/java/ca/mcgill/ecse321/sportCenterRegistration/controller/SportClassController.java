package ca.mcgill.ecse321.sportCenterRegistration.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportCenterRegistration.dto.SportClassDTO;
import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;
import ca.mcgill.ecse321.sportCenterRegistration.service.SportClassService;

@RestController
@CrossOrigin(origins = "*")
public class SportClassController {
    @Autowired
    private SportClassService sportClassService;

    @GetMapping(value = { "/sport-class/{name}", "/sport-class/{name}/" })
    public SportClassDTO getSportClass(@PathVariable("name") String name) throws IllegalArgumentException {
        SportClass sportClass = sportClassService.getSportClass(name);
        return convertToDto(sportClass);
    }

    @PostMapping(value = { "/sport-class/{name}", "/sport-class/{name}/" })
    public SportClassDTO createSportClass(@PathVariable("name") String name) throws IllegalArgumentException {
        SportClass sportClass = sportClassService.createSportClass(name);
        return convertToDto(sportClass);
    }

    @PutMapping(value = { "/sport-class/approve/{name}", "/sport-class/approve/{name}/" })
    public SportClassDTO approveSportClass(@PathVariable("name") String name) throws IllegalArgumentException {
        SportClass sportClass = sportClassService.approveSportClass(name);
        return convertToDto(sportClass);
    }

    @DeleteMapping(value = { "/sport-class/{name}", "/sport-class/{name}/" })
    public void delete(@PathVariable("name") String name) throws IllegalArgumentException {
        sportClassService.deleteSportClass(name);
    }

    @GetMapping(value = { "/sport-class/all", "/sport-class/all/" })
    public List<SportClassDTO> getAllSportClass() throws IllegalArgumentException {
        return sportClassService.getAllSportClass().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
    }

    private SportClassDTO convertToDto(SportClass s) throws IllegalArgumentException {
        return new SportClassDTO(s.getId(), s.getName(), s.getApproved());
    }
}