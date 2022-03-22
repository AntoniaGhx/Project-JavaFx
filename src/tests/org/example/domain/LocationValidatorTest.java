package org.example.domain;

import org.example.repository.IRepository;
import org.example.repository.InMemoryRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationValidatorTest {
    @Test
    void validateShouldRaiseExceptionForEachCase() throws Exception {

        //arrange
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

        LocationValidator locationValidator = new LocationValidator();
        // act
        try {
            locationValidator.validate(location, repositoryLocation);
            //assert
            fail("The number of locations is too big.");
        }catch (Exception ex){
            assertEquals ("The market reached maximum capacity!", ex.getMessage());
        }
    }

    @Test
    void validateShouldRaiseExceptionForStartDateAfterEndDate() throws Exception {

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