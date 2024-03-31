package ca.mcgill.ecse321.sportCenterRegistration.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;
import ca.mcgill.ecse321.sportCenterRegistration.dao.SportClassRepository;

@Service
public class SportClassService{
    @Autowired
    private SportClassRepository sportClassRepo;


    @Transactional
    public SportClass getSportClass(String name){
        if (name==null || name.length()<=0){
            throw new IllegalArgumentException("Sport Class name should not be empty!");
        }
        SportClass sportClass = sportClassRepo.findSportClassByName(name);
        if (sportClass==null){
            throw new IllegalArgumentException("Sport Class doesn't exist!");
        }
        return sportClass;
    }

    @Transactional
    public SportClass createSportClass(String name){
        if (name==null || name.length()<=0){
            throw new IllegalArgumentException("Sport Class name should not be empty!");
        }
        if (sportClassRepo.findSportClassByName(name)!=null){
            throw new IllegalArgumentException("Sport Class already exists!");
        }
        SportClass sportClass = new SportClass(name);
        return sportClassRepo.save(sportClass);
    }

    @Transactional
    public void deleteSportClass(String name){
        if (name==null || name.length()<=0){
            throw new IllegalArgumentException("Sport Class name should not be empty!");
        }
        SportClass sportClass = sportClassRepo.findSportClassByName(name);
        if (sportClass==null){
            throw new IllegalArgumentException("Sport Class doesn't exist!");
        }
        sportClassRepo.delete(sportClass);
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

    @Transactional
    public List<SportClass> getAllSportClass(){
        return sportClassRepo.findAll();
    }
}