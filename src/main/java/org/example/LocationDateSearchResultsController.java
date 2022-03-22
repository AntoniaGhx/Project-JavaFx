package org.example;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.domain.Location;

public class LocationDateSearchResultsController {

    public TableView tblLocations;
    public TableColumn colIdLocation;
    public TableColumn colStartTimeLocation;
    public TableColumn colStopTimeLocation;
    public TableColumn colPriceLocation;

    public void setResultsList(List<Location> searchResults) {
        ObservableList<Location> results = FXCollections.observableArrayList();
        results.addAll(searchResults);
        tblLocations.setItems(results);
    }
}