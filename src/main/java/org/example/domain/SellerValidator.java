package org.example.domain;

import org.example.LocationsWithSellersAndProductsController;
import org.example.repository.IRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SellerValidator {
    public void validate(Seller seller, IRepository<Location> locationRepository, IRepository<Seller> sellerRepository) throws ValidatorException {
        List<Location> locations = locationRepository.read();
        List<Seller> sellers = sellerRepository.read();

        if(seller.getName().isEmpty()){
            throw new ValidatorException("Please enter your name!");
        }

        if(seller.getLocationId() > 100){
            throw new ValidatorException("The market reached maximum capacity!");
        }

        boolean locationExists = false;
        for(Location l: locations){
            if(l.getIdEntity() == seller.getLocationId()){
                locationExists = true;
            }
        }

        if(locationExists == false){
            throw new ValidatorException("The location provided doesn't exist!");
        }

        boolean isLocationAlreadyRented = false;
        for(Seller s: sellers){
            if(s.getLocationId() == seller.getLocationId()){
                isLocationAlreadyRented = true;
            }
        }

        if(isLocationAlreadyRented){
            throw new ValidatorException("The location is already rented!");
        }
    }

    public void validateSellerLocation(Location location, IRepository<Location> locationRepository) throws ValidatorException, ParseException {
        List<Location> locations = locationRepository.read();

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
