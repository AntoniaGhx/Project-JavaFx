package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.domain.Location;
import org.example.domain.LocationsWithSellersAndProducts;

import java.util.List;

public class LocationsWithSellersAndProductsController {
    public TableView tblLocationsWithSellersAndProducts;
    public TableColumn colIdLocation;
    public TableColumn colNameSeller;
    public TableColumn colProductNames;

    public void setResultsList(List<LocationsWithSellersAndProducts> locationsWithSellersAndProductsList) {
        ObservableList<LocationsWithSellersAndProducts> results = FXCollections.observableArrayList();
        results.addAll(locationsWithSellersAndProductsList);
        tblLocationsWithSellersAndProducts.setItems(results);
    }
}
