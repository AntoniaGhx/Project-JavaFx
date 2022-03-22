package org.example.domain;

import org.example.repository.IRepository;

import java.util.List;

public class ProductValidator {
    public void validate(Product product) throws ValidatorException {
        if(product.getName().isEmpty()){
            throw new ValidatorException("Please enter a name for this product!");
        }
        if(product.getPrice() == 0){
            throw new ValidatorException("Please enter a price for this product!");
        }
        if(product.getQuantity() == 0){
            throw new ValidatorException("Please enter a quantity for this product!");
        }
        if(product.getQuantity() >= 100){
            throw new ValidatorException("The maximum quantity is 100 kg!");
        }
    }
}
