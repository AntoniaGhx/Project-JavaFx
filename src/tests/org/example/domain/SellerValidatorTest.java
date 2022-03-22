package org.example.domain;

import org.example.repository.IRepository;
import org.example.repository.InMemoryRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SellerValidatorTest {

    @Test
    void validateShouldRaiseExceptionIfNameIsEmpty() throws Exception {

        //arrange
        IRepository<Location> repositoryLocation = new InMemoryRepository<>();
        IRepository<Seller> repositorySeller = new InMemoryRepository<>();

        List<Integer> productList1 = new ArrayList<Integer>();
        productList1.add(1);
        productList1.add(2);

        Seller s = new Seller(2,"", true, true, 65, productList1);

        SellerValidator sellerValidator = new SellerValidator();
        // act
        try {
            sellerValidator.validate(s, repositoryLocation, repositorySeller);
            //assert
            fail("Empty Name");
        }catch (Exception ex){
            assertEquals ("Please enter your name!", ex.getMessage());
        }
    }

    @Test
    void validateShouldRaiseExceptionIfMaximumCapacityIsReached() throws Exception {

        //arrange
        IRepository<Location> repositoryLocation = new InMemoryRepository<>();
        IRepository<Seller> repositorySeller = new InMemoryRepository<>();

        List<Integer> productList1 = new ArrayList<Integer>();
        productList1.add(1);
        productList1.add(2);

        Seller s = new Seller(1,"Ana", true, true, 101, productList1);

        SellerValidator sellerValidator = new SellerValidator();
        // act
        try {
            sellerValidator.validate(s, repositoryLocation, repositorySeller);
            //assert
            fail("Maximum capacity reached");
        }catch (Exception ex){
            assertEquals ("The market reached maximum capacity!", ex.getMessage());
        }
    }

    @Test
    void validateShouldRaiseExceptionIfLocationDoesNotExists() throws Exception {

        //arrange
        IRepository<Location> repositoryLocation = new InMemoryRepository<>();
        IRepository<Seller> repositorySeller = new InMemoryRepository<>();

        List<Integer> productList1 = new ArrayList<Integer>();
        productList1.add(1);
        productList1.add(2);

        Seller s = new Seller(1,"Ana", true, true, 2, productList1);

        SellerValidator sellerValidator = new SellerValidator();
        // act
        try {
            sellerValidator.validate(s, repositoryLocation, repositorySeller);
            //assert
            fail("Location doesn't exist");
        }catch (Exception ex){
            assertEquals ("The location provided doesn't exist!", ex.getMessage());
        }
    }

    @Test
    void validateShouldRaiseExceptionIfLocationIsAlreadyRented() throws Exception {

        //arrange
        IRepository<Location> repositoryLocation = new InMemoryRepository<>();
        IRepository<Seller> repositorySeller = new InMemoryRepository<>();

        List<Integer> productList1 = new ArrayList<Integer>();
        productList1.add(1);
        productList1.add(2);

        repositoryLocation.create(new Location(2, "20.03.2021 15:32", "20.04.2021 15:15", 10));
        repositorySeller.create(new Seller(2,"Ana", true, true, 2, productList1));
        Seller s = new Seller(1,"Ana", true, true, 2, productList1);

        SellerValidator sellerValidator = new SellerValidator();
        // act
        try {
            sellerValidator.validate(s, repositoryLocation, repositorySeller);
            //assert
            fail("Location already rented");
        }catch (Exception ex){
            assertEquals ("The location is already rented!", ex.getMessage());
        }
    }

    @Test
    void validateSellerLocationShouldRaiseExceptionIfLocationsExceedMaximumNumber() throws Exception {

        IRepository<Location> repositoryLocation = new InMemoryRepository<>();
        repositoryLocation.create(new Location(65, "20.03.2021 15:32", "20.04.2021 15:15", 10));
        repositoryLocation.create(new Location(4, "20.03.2021 15:32", "20.04.2021 15:15", 10));
        repositoryLocation.create(new Location(8, "20.03.2021 15:32", "20.04.2021 15:15", 10));
        repositoryLocation.create(new Location(7, "20.03.2021 15:32", "20.04.2021 15:15", 10));
        repositoryLocation.create(new Location(6, "20.03.2021 15:32", "20.04.2021 15:15", 10));
        repositoryLocation.create(new Location(5, "20.03.2021 15:32", "20.04.2021 15:15", 10));
        repositoryLocation.create(new Location(9, "20.03.2021 15:32", "20.04.2021 15:15", 10));
        repositoryLocation.create(new Location(3, "20.03.2021 15:32", "20.04.2021 15:15", 10));
        repositoryLocation.create(new Location(2, "20.03.2021 15:32", "20.04.2021 15:15", 10));
        repositoryLocation.create(new Location(1, "20.03.2021 15:32", "20.04.2021 15:15", 10));

        Location location = new Location(30, "20.03.2021 15:32", "20.04.2021 15:15", 10);

        SellerValidator sellerValidator = new SellerValidator();
        // act
        try {
            sellerValidator.validateSellerLocation(location, repositoryLocation);
            //assert
            fail("Too many locations!");
        }catch (Exception ex){
            assertEquals ("The market reached maximum capacity!", ex.getMessage());
        }
    }

    @Test
    void validateSellerLocationShouldRaiseExceptionIfStartDateAfterEndDate() throws Exception {

        //arrange
        IRepository<Location> repositoryLocation = new InMemoryRepository<>();
        repositoryLocation.create(new Location(65, "20.03.2021 15:32", "20.04.2021 15:15", 10));

        Location location = new Location(30, "20.04.2021 15:15", "20.03.2021 15:32", 10);

        LocationValidator locationValidator = new LocationValidator();
        // act
        try {
            locationValidator.validate(location, repositoryLocation);
            //assert
            fail("Start date is after end date!");
        }catch (Exception ex){
            assertEquals ("Start date should be before the end date!", ex.getMessage());
        }
    }
}