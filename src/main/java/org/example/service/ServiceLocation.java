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

public class ServiceLocation {
    private IRepository<Location> locationIRepository;
    private IRepository<Product> productIRepository;
    private IRepository<Seller> sellerIRepository;
    private UndoRedoManager undoRedoManager;
    private LocationValidator locationValidator;

    public ServiceLocation(IRepository<Location> locationIRepository,
                           IRepository<Product> productIRepository,
                           IRepository<Seller> sellerIRepository,
                           UndoRedoManager undoRedoManager,
                           LocationValidator locationValidator) {
        this.locationIRepository = locationIRepository;
        this.productIRepository = productIRepository;
        this.sellerIRepository = sellerIRepository;
        this.undoRedoManager = undoRedoManager;
        this.locationValidator = locationValidator;
    }

    /**
     * ...
     RepositoryException if ...
     */

    public void addLocation(int id, String startTime, String stopTime) throws RepositoryException, ParseException {
        float price = this.calculatePrice(startTime, stopTime);
        Location location = new Location(id, startTime, stopTime, price);
        this.locationValidator.validate(location, this.locationIRepository);
        this.locationIRepository.create(location);

        undoRedoManager.addToUndo(new UndoRedoAddOperation<>(this.locationIRepository, location));
    }

    public void updateLocation(int id, String startTime, String stopTime) throws RepositoryException, ParseException {
        float price = this.calculatePrice(startTime, stopTime);
        Location location = new Location(id, startTime, stopTime, price);
        this.locationIRepository.update(location);
    }

    public void deleteLocation(int idLocation) throws RepositoryException {
        Location location = this.locationIRepository.readOne(idLocation);
        this.locationIRepository.delete(idLocation);

        undoRedoManager.addToUndo(new UndoRedoDeleteOperation<>(this.locationIRepository, location));
    }

    /**
     * Returneaza toate locatiile care contin in orice camp (mai putin ID-ul) textul dat.
     * @return o lista cu prajiturile gasite.
     */

    public List<Location> getAllLocations() {
        return this.locationIRepository.read();
    }

    public List<Location> getLocationsByText(String searchText) {
        List<Location> results = new ArrayList<>();
        for (Location l : this.getAllLocations()) {
            if (String.valueOf(l.getStartTime()).contains(searchText) || String.valueOf(l.getStopTime()).contains(searchText)) {
                results.add(l);
            }
        }

        return results;
    }

    public List<Location> getBetweenTwoDateAndTimes(LocalDateTime start, LocalDateTime end) {
        List<Location> results = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        for (Location l: this.getAllLocations()) {
            String stringDate = l.getStartTime();
            LocalDateTime typedDate = LocalDateTime.parse(stringDate, formatter);
            if (start.isBefore(typedDate) && typedDate.isBefore(end)) {
                results.add(l);
            }
        }

        return results;
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
