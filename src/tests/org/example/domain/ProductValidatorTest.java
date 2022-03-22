package org.example.domain;

import org.example.repository.IRepository;
import org.example.repository.InMemoryRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductValidatorTest {
    @Test
    void validateShouldRaiseExceptionIfNameIsEmpty() throws Exception {

        Product p = new Product(1, "", 10, 20);

        ProductValidator productValidator = new ProductValidator();
        // act
        try {
            productValidator.validate(p);
            //assert
            fail("Empty Name");
        }catch (Exception ex){
            assertEquals ("Please enter a name for this product!", ex.getMessage());
        }
    }

    @Test
    void validateShouldRaiseExceptionIfPriceIsEmpty() throws Exception {

        Product p = new Product(1, "asdasda", 0, 20);

        ProductValidator productValidator = new ProductValidator();
        // act
        try {
            productValidator.validate(p);
            //assert
            fail("0 Price");
        }catch (Exception ex){
            assertEquals ("Please enter a price for this product!", ex.getMessage());
        }
    }

    @Test
    void validateShouldRaiseExceptionIfQuantityIsEmpty() throws Exception {

        Product p = new Product(1, "asdasda", 12, 0);

        ProductValidator productValidator = new ProductValidator();
        // act
        try {
            productValidator.validate(p);
            //assert
            fail("0 Quantity");
        }catch (Exception ex){
            assertEquals ("Please enter a quantity for this product!", ex.getMessage());
        }
    }

    @Test
    void validateShouldRaiseExceptionIfQuantityIsTooMuch() throws Exception {

        Product p = new Product(1, "asdasda", 12, 200);

        ProductValidator productValidator = new ProductValidator();
        // act
        try {
            productValidator.validate(p);
            //assert
            fail("Too much quantity");
        }catch (Exception ex){
            assertEquals ("The maximum quantity is 100 kg!", ex.getMessage());
        }
    }
}