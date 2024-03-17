
import java.sql.Date;
import java.time.LocalDate;

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

//    @Transactional
//    public Iterable<SportClass> findAllSportClass(){
//        ;
//    }

}