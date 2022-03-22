package org.example.repository;


import org.example.domain.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class InMemoryRepositoryTest {
    InMemoryRepositoryTest() {
    }

    @Test
    void createShouldValidateTheIdAndAddTheObject() throws Exception {
        IRepository<Product> inMemoryRepository = new InMemoryRepository();
        Product product1 = new Product(1, "mere", 10, 50);
        Product product2 = new Product(2, "rosii", 8, 50);
        Product product3 = new Product(1, "banane", 12, 50);
        inMemoryRepository.create(product1);
        Assertions.assertEquals(1, inMemoryRepository.read().size(), "Dupa adaugarea unui produs, read().size() != 1!");
        Assertions.assertEquals(product1.getIdEntity(), ((Product)inMemoryRepository.read().get(0)).getIdEntity());
        inMemoryRepository.create(product2);
        Assertions.assertEquals(2, inMemoryRepository.read().size(), "Dupa adaugarea a 2 produse, read().size() != 2!");

        try {
            inMemoryRepository.create(product3);
            Assertions.fail("Adaugarea unui produs cu id existent nu da exceptie!");
        } catch (RepositoryException var6) {
            Assertions.assertEquals(2, inMemoryRepository.read().size(), "S-a adaugat un produs cu id existent!");
        }

    }

    @Test
    void readOneShouldCorrectlyReturnProduct() throws Exception {
        IRepository<Product> inMemoryRepository = new InMemoryRepository();
        List<Product> products = new ArrayList();

        int i;
        Product foundById;
        for(i = 0; i < 20; ++i) {
            foundById = new Product(i, "a", 10, 50);
            products.add(foundById);
        }

        Iterator var5 = products.iterator();

        while(var5.hasNext()) {
            foundById = (Product) var5.next();
            inMemoryRepository.create(foundById);
        }

        for(i = 0; i < 20; ++i) {
            foundById = (Product) inMemoryRepository.readOne(i);
            Assertions.assertEquals(i, foundById.getIdEntity());
        }

        Assertions.assertNull(inMemoryRepository.readOne(100));
    }

    @Test
    void deleteShouldValidateTheIdAndRemoveTheObject() throws Exception {
        IRepository<Product> inMemoryRepository = new InMemoryRepository();
        List<Product> products = new ArrayList();

        int i;
        Product foundById;
        for(i = 0; i < 20; ++i) {
            foundById = new Product(i, "a", 10, 50);
            products.add(foundById);
        }

        Iterator var6 = products.iterator();

        while(var6.hasNext()) {
            foundById = (Product) var6.next();
            inMemoryRepository.create(foundById);
        }

        for(i = 0; i < 20; ++i) {
            inMemoryRepository.delete(i);
            foundById = (Product) inMemoryRepository.readOne(i);
            Assertions.assertNull(foundById);
            Assertions.assertEquals(20 - i - 1, inMemoryRepository.read().size());
        }

        try {
            inMemoryRepository.delete(5);
            Assertions.fail("Stergerea unui produs cu id existent nu da exceptie!");
        } catch (Exception var5) {
            Assertions.assertEquals(0, inMemoryRepository.read().size(), "Exista produse nesterse!");
        }

    }

    @Test
    void read() {
    }

}