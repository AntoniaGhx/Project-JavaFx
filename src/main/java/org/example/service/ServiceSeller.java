package org.example.service;
import org.example.domain.*;
import org.example.repository.IRepository;
import org.example.repository.RepositoryException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceSeller {
    private IRepository<Seller> sellerIRepository;
    private IRepository<Product> productIRepository;
    private IRepository<Location> locationIRepository;
    private UndoRedoManager undoRedoManager;
    private SellerValidator sellerValidator;

    public ServiceSeller(IRepository<Seller> sellerIRepository,
                         IRepository<Product> productIRepository,
                         IRepository<Location> locationIRepository,
                         UndoRedoManager undoRedoManager,
                         SellerValidator sellerValidator){
        this.sellerIRepository = sellerIRepository;
        this.productIRepository = productIRepository;
        this.locationIRepository = locationIRepository;
        this.undoRedoManager = undoRedoManager;
        this.sellerValidator = sellerValidator;
    }

    public void addSeller(int id, String name, boolean vegetableSeller, boolean fruitSeller, int locationId,
                          List<Integer> productsIds, String startTime, String stopTime) throws RepositoryException, ParseException {
        float price = this.calculatePrice(startTime, stopTime);

        Seller seller = new Seller(id, name, vegetableSeller, fruitSeller, locationId, productsIds);
        Location location = new Location(locationId, startTime, stopTime, price);

        boolean locationExists = false;
        for(Location l: locationIRepository.read()){
            if(l.getIdEntity() == seller.getLocationId()){
                locationExists = true;
            }
        }

        if(locationExists == false){
            this.sellerValidator.validateSellerLocation(location, locationIRepository);
            this.locationIRepository.create(location);
        }

        this.sellerValidator.validate(seller, locationIRepository, sellerIRepository);
        this.sellerIRepository.create(seller);

        undoRedoManager.addToUndo(new UndoRedoAddOperation<>(this.sellerIRepository, seller));
    }

    public void updateSeller(int id, String name, boolean vegetableSeller, boolean fruitSeller, int locationId, List<Integer> productsIds) throws RepositoryException {
        Seller seller = new Seller(id, name, vegetableSeller, fruitSeller, locationId, productsIds);
        this.sellerValidator.validate(seller, locationIRepository, sellerIRepository);
        this.sellerIRepository.update(seller);
    }

    public void deleteSeller(int idSeller) throws RepositoryException {
        Seller seller = this.sellerIRepository.readOne(idSeller);
        this.sellerIRepository.delete(idSeller);

        undoRedoManager.addToUndo(new UndoRedoDeleteOperation<>(this.sellerIRepository, seller));
    }

    public List<Seller> getAllSellers() {
        return this.sellerIRepository.read();
    }

    public List<Seller> getSellersByText(String searchText) {
        List<Seller> results = new ArrayList<>();
        for (Seller s : this.getAllSellers()) {
            if (s.getName().contains(searchText) ||
                    String.valueOf(s.isVegetableSeller()).contains(searchText) ||
                    String.valueOf(s.isFruitSeller()).contains(searchText)) {
                results.add(s);
            }
        }

        return results;
    }

    public List<SellersWithProducts> getSellersWithProducts(){
        List<SellersWithProducts> sellersWithProductsList = new ArrayList<>();

        for(Seller seller : sellerIRepository.read()){
            List<String> sellerProductNames = new ArrayList<>();
            for(Integer productId : seller.getProductsIds()){
                sellerProductNames.add(productIRepository.readOne(productId).getName());
            }
            SellersWithProducts sellersWithProducts = new SellersWithProducts(seller.getIdEntity(), seller.getName(), sellerProductNames);
            sellersWithProductsList.add(sellersWithProducts);
        }

        return sellersWithProductsList;
    }

    public List<LocationsWithSellersAndProducts> getLocationsWithSellersAndProducts(LocalDateTime start, LocalDateTime end){
        List<LocationsWithSellersAndProducts> locationsWithSellersAndProductsList = new ArrayList<>();
        List<Location> locationSearchResults = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        for (Location l: locationIRepository.read()) {
            String stringDate = l.getStartTime();
            LocalDateTime typedDate = LocalDateTime.parse(stringDate, formatter);
            if (start.isBefore(typedDate) && typedDate.isBefore(end)) {
                locationSearchResults.add(l);
            }
        }

        for(Seller seller : sellerIRepository.read()){
            for(Location location : locationSearchResults){
                if(location.getIdEntity() == seller.getLocationId()){
                    List<String> sellerProductNames = new ArrayList<>();
                    for(Integer productId : seller.getProductsIds()){
                        sellerProductNames.add(productIRepository.readOne(productId).getName());
                    }
                    LocationsWithSellersAndProducts locationsWithSellersAndProducts = new LocationsWithSellersAndProducts(seller.getLocationId(), seller.getName(), sellerProductNames);
                    locationsWithSellersAndProductsList.add(locationsWithSellersAndProducts);
                }
            }
        }

        return locationsWithSellersAndProductsList;
    }

    public float calculatePrice(String startTime, String stopTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        try {
            Date d1 = sdf.parse(startTime);
            Date d2 = sdf.parse(stopTime);
            long difference_In_Time = d2.getTime() - d1.getTime();

            long differenceInDays
                    = (difference_In_Time
                    / (1000 * 60 * 60 * 24))
                    % 365;

            float price = 10 * differenceInDays;
            return price;
        } catch (ParseException e) {
            System.out.print(e);
        }
        return 0;
    }
}
