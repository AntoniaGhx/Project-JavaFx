package org.example.service;
import org.example.domain.*;
import org.example.repository.IRepository;
import org.example.repository.RepositoryException;

import java.util.ArrayList;
import java.util.List;

public class ServiceProduct {
    private IRepository<Product> productIRepository;
    private IRepository<Seller> sellerIRepository;
    private IRepository<Location> locationIRepository;
    private UndoRedoManager undoRedoManager;
    private ProductValidator productValidator;

    public ServiceProduct(IRepository<Product> productIRepository,
                          IRepository<Seller> sellerIRepository,
                          IRepository<Location> locationIRepository,
                          UndoRedoManager undoRedoManager,
                          ProductValidator productValidator) {
        this.productIRepository = productIRepository;
        this.sellerIRepository = sellerIRepository;
        this.locationIRepository = locationIRepository;
        this.undoRedoManager = undoRedoManager;
        this.productValidator = productValidator;
    }

    public void addProduct(int id, String name,float price, float quantity) throws RepositoryException {
        Product product = new Product(id, name, price, quantity);
        this.productValidator.validate(product);
        this.productIRepository.create(product);

        undoRedoManager.addToUndo(new UndoRedoAddOperation<>(this.productIRepository, product));
    }

    public void updateProduct(int id, String name, float price, float quantity) throws RepositoryException {
        Product product = new Product(id, name, price, quantity);
        this.productValidator.validate(product);
        this.productIRepository.update(product);

    }

    public void deleteProduct(int idProduct) throws RepositoryException {
        Product product = this.productIRepository.readOne(idProduct);
        this.productIRepository.delete(idProduct);

        undoRedoManager.addToUndo(new UndoRedoDeleteOperation<>(this.productIRepository, product));
    }

    public List<Product> getAllProducts() {
        return this.productIRepository.read();
    }

    public List<Product> getProductByText(String searchText) {
        List<Product> results = new ArrayList<>();
        for (Product p : this.getAllProducts()) {
            if (p.getName().contains(searchText) ||
                    String.valueOf(p.getPrice()).contains(searchText) ||
                    String.valueOf(p.getQuantity()).contains(searchText)) {
                results.add(p);
            }
        }

        return results;
    }

}

