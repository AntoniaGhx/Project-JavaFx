package org.example.domain;

import org.example.repository.IRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LocationValidator {
    public void validate(Location location, IRepository<Location> repository) throws ValidatorException, ParseException {
        List<Location> locations = repository.read();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date d1 = sdf.parse(location.getStartTime());
        Date d2 = sdf.parse(location.getStopTime());
        long difference_In_Time = d2.getTime() - d1.getTime();

        if(difference_In_Time < 0){
            throw new ValidatorException("Start date should be before the end date!");
        }

        if(locations.size() == 10){
            throw new ValidatorException("The market reached maximum capacity!");
        }
    }
}
