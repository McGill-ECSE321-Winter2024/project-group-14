package ca.mcgill.ecse321.sportCenterRegistration.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportCenterRegistration.dao.AccountRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.OwnerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.OwnerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.SessionRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.ShiftRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.SportClassRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.StaffRepository;


import ca.mcgill.ecse321.sportCenterRegistration.model.Account;
import ca.mcgill.ecse321.sportCenterRegistration.model.Owner;
import ca.mcgill.ecse321.sportCenterRegistration.model.Owner;
import ca.mcgill.ecse321.sportCenterRegistration.model.Registration;
import ca.mcgill.ecse321.sportCenterRegistration.model.Session;
import ca.mcgill.ecse321.sportCenterRegistration.model.Shift;
import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;
import ca.mcgill.ecse321.sportCenterRegistration.model.Staff;





@Service
public class OwnerService {

    @Autowired
    OwnerRepository OwnerRepository;
    @Autowired
    SportClassRepository sportClassRepo;

    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }


    @Transactional
    public Owner getOwner(String username) {
        Owner Owner = OwnerRepository.findOwnerByUsername(username);
        if (Owner == null) {
            throw new IllegalArgumentException("Owner name is invalid");
        }
        return Owner;
    }

    @Transactional
    public Boolean deleteOwner(String username) {
        if (username == null || username.trim().length() == 0) {
            throw new IllegalArgumentException("Owner name cannot be empty!");
        }
        Owner OwnerToDelete = getOwner(username);
        OwnerRepository.delete(OwnerToDelete);
        return true;
    }

    @Transactional
    public Owner createOwner(String username, String email, String password ) {


        if (username == null || username.strip() == "") {
            throw new IllegalArgumentException("Username cannot be empty!");
        }
        if (email == null || email.strip() == "") {
            throw new IllegalArgumentException("Email cannot be empty!");
        }
        if (password == null || password.strip() == "") {
            throw new IllegalArgumentException("Password cannot be empty!");
        }

        Owner Owner = new Owner(username, email, password);
        OwnerRepository.save(Owner);
        return Owner;
    }

    @Transactional
    public List<Owner> getAllOwners() {
        return toList(OwnerRepository.findAll());
    }


    @Transactional
    public SportClass approveSportClass(String name){
        if (name==null || name.length()<=0){
            throw new IllegalArgumentException("Sport Class name should not be empty!");
        }
        SportClass sportClass = sportClassRepo.findSportClassByName(name);
        if (sportClass==null){
            throw new IllegalArgumentException("Sport Class doesn't exist!");
        }
        sportClass.setApproved(true);
        return sportClassRepo.save(sportClass);
    }

}