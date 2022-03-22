package org.example;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.domain.*;
import org.example.service.ServiceLocation;
import org.example.service.ServiceProduct;
import org.example.service.ServiceSeller;
import org.example.service.UndoRedoManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PrimaryController {

    public TextField txtProductId;
    public TextField txtProductName;
    public TextField txtProductPrice;
    public TextField txtProductQuantity;
    public TableView tblProducts;
    public TableColumn colIdProduct;
    public TableColumn colNameProduct;
    public TableColumn colPriceProduct;
    public TableColumn colQuantity;
    public Button btnAddProduct;
    public Button btnUpdateProduct;
    public TableColumn colIdSeller;
    public TableColumn colNameSeller;
    public TableColumn colVegetableSeller;
    public TableColumn colFruitSeller;
    public TableColumn colSellerLocationId;
    public TextField txtSellerId;
    public TextField txtSellerName;
    public TextField txtSellerLocationId;
    public CheckBox chkVegetableSeller;
    public CheckBox chkFruitSeller;
    public Button btnDeleteSelectedProduct;
    public Button btnAddSeller;
    public Button btnUpdateSeller;
    public Button btnDeleteSelectedSeller;
    public TableView tblSellers;
    public TableView tblLocations;
    public TableColumn colIdLocation;
    public TextField txtLocationId;
    public TextField txtLocationStartTime;
    public TextField txtLocationStopTime;
    public Button btnAddLocation;
    public Button btnUpdateLocation;
    public Button btnDeleteSelectedLocation;
    public TextField txtSearchText;
    public Button btnFullTextSearch;
    public Button btnUndo;
    public Button btnRedo;
    public TextField txtDateHourStart;
    public TextField txtDateHourEnd;
    public Button btnLocationDateHourSearch;
    public TextField txtSellerProductsIds;
    public TableColumn colSellerProductIds;
    public TextField txtSellerLocationStartTime;
    public TextField txtSellerLocationStopTime;
    public TextField txtDateTimeStart;
    public TextField txtDateTimeEnd;
    public Button btnShowLocationWithSellersAndProducts;
    private ServiceProduct serviceProduct;
    private ServiceSeller serviceSeller;
    private ServiceLocation serviceLocation;
    private UndoRedoManager undoRedoManager;

    private ObservableList<Product> products = FXCollections.observableArrayList();
    private ObservableList<Seller> sellers = FXCollections.observableArrayList();
    private ObservableList<Location> locations = FXCollections.observableArrayList();

    @FXML
    private void initialize() {


        Platform.runLater(() -> {
            products.addAll(serviceProduct.getAllProducts());
            sellers.addAll(serviceSeller.getAllSellers());
            locations.addAll(serviceLocation.getAllLocations());
            tblProducts.setItems(products);
            tblSellers.setItems(sellers);
            tblLocations.setItems(locations);
        });
    }

    public void setServices(ServiceProduct serviceProduct, ServiceSeller serviceSeller, ServiceLocation serviceLocation,
                            UndoRedoManager undoRedoManager) {
        this.serviceProduct = serviceProduct;
        this.serviceSeller = serviceSeller;
        this.serviceLocation = serviceLocation;
        this.undoRedoManager = undoRedoManager;
    }

    public void btnAddProductClick(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(txtProductId.getText());
            String name = txtProductName.getText();
            float price = Float.parseFloat(txtProductPrice.getText());
            float quantity = Float.parseFloat(txtProductQuantity.getText());

            serviceProduct.addProduct(id, name, price, quantity);

            refreshProductsList();
        } catch (RuntimeException rex) {
            Common.showErrorMessage(rex.getMessage());
        }
    }

    public void btnUpdateProductClick(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(txtProductId.getText());
            String name = txtProductName.getText();
            float price = Float.parseFloat(txtProductPrice.getText());
            float quantity = Float.parseFloat(txtProductQuantity.getText());

            serviceProduct.updateProduct(id, name, price, quantity);
            refreshProductsList();
        } catch (RuntimeException rex) {
            Common.showErrorMessage(rex.getMessage());
        }
    }

    public void btnDeleteSelectedProductClick(ActionEvent actionEvent) {
        Product selectedProduct = (Product) tblProducts.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            serviceProduct.deleteProduct(selectedProduct.getIdEntity());
            refreshProductsList();
        }
    }

    public void btnAddSellerClick(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(txtSellerId.getText());
            String name = txtSellerName.getText();
            boolean vegetableSeller = chkVegetableSeller.isSelected();
            boolean fruitSeller = chkFruitSeller.isSelected();
            int locationId = Integer.parseInt(txtSellerLocationId.getText());
            String productsIdsStrings[] = txtSellerProductsIds.getText().split(",");
            List<Integer> productIds = new ArrayList<>();
            for(String productId : productsIdsStrings){
                productIds.add(Integer.parseInt(productId));
            }
            String startTime = txtSellerLocationStartTime.getText();
            String stopTime = txtSellerLocationStopTime.getText();

            serviceSeller.addSeller(id, name, vegetableSeller, fruitSeller, locationId, productIds, startTime, stopTime);

            refreshSellersList();
        } catch (Exception ex) {
            Common.showErrorMessage(ex.getMessage());
        }
    }

    public void btnUpdateSellerClick(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(txtSellerId.getText());
            String name = txtSellerName.getText();
            boolean vegetableSeller = chkVegetableSeller.isSelected();
            boolean fruitSeller = chkFruitSeller.isSelected();
            int locationId = Integer.parseInt(txtSellerLocationId.getText());
            String productsIdsStrings[] = txtSellerProductsIds.getText().split(",");
            List<Integer> productIds = new ArrayList<>();
            for(String productId : productsIdsStrings){
                productIds.add(Integer.parseInt(productId));
            }

            serviceSeller.updateSeller(id, name, vegetableSeller, fruitSeller, locationId, productIds);

            refreshSellersList();
        } catch (RuntimeException rex) {
            Common.showErrorMessage(rex.getMessage());
        }
    }

    public void btnDeleteSelectedSellerClick(ActionEvent actionEvent) {
        Seller selectedSeller = (Seller) tblSellers.getSelectionModel().getSelectedItem();
        if (selectedSeller != null) {
            serviceSeller.deleteSeller(selectedSeller.getIdEntity());
            refreshSellersList();
        }
    }

    public void btnAddLocationClick(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(txtLocationId.getText());
            String startTime = txtLocationStartTime.getText();
            String stopTime = txtLocationStopTime.getText();

            serviceLocation.addLocation(id, startTime, stopTime);

            refreshLocationsList();
        } catch (Exception ex) {
            Common.showErrorMessage(ex.getMessage());
        }
    }

    public void btnUpdateLocationClick(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(txtLocationId.getText());
            String startTime = txtLocationStartTime.getText();
            String stopTime = txtLocationStopTime.getText();

            serviceLocation.updateLocation(id, startTime, stopTime);

            refreshLocationsList();
        } catch (Exception ex) {
            Common.showErrorMessage(ex.getMessage());
        }
    }

    public void btnDeleteSelectedLocationClick(ActionEvent actionEvent) {
        Location selectedLocation = (Location) tblLocations.getSelectionModel().getSelectedItem();
        if (selectedLocation != null) {
            serviceLocation.deleteLocation(selectedLocation.getIdEntity());
            refreshLocationsList();
        }
    }

    private void refreshProductsList() {
        products.clear();
        products.addAll(serviceProduct.getAllProducts());
    }

    private void refreshSellersList() {
        sellers.clear();
        sellers.addAll(serviceSeller.getAllSellers());
    }

    private void refreshLocationsList() {
        locations.clear();
        locations.addAll(serviceLocation.getAllLocations());
    }

    public void btnFullTextSearchClick(ActionEvent actionEvent) {
        List<Product> productsResults = serviceProduct.getProductByText(txtSearchText.getText());
        List<Seller> sellersResults = serviceSeller.getSellersByText(txtSearchText.getText());
        List<Location> locationsResults = serviceLocation.getLocationsByText(txtSearchText.getText());
        products.clear();
        products.addAll(productsResults);

        sellers.clear();
        sellers.addAll(sellersResults);

        locations.clear();
        locations.addAll(locationsResults);
    }

    public void btnUndoClick(ActionEvent actionEvent) {
        this.undoRedoManager.doUndo();

        this.refreshProductsList();
        this.refreshSellersList();
        this.refreshLocationsList();
    }

    public void btnRedoClick(ActionEvent actionEvent) {
        this.undoRedoManager.doRedo();

        this.refreshProductsList();
        this.refreshSellersList();
        this.refreshLocationsList();
    }

    public void btnTransactionDateHourSearchClick(ActionEvent actionEvent) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            LocalDateTime start = LocalDateTime.parse(txtDateHourStart.getText(), formatter);
            LocalDateTime end = LocalDateTime.parse(txtDateHourEnd.getText(), formatter);
            List<Location> searchResults = serviceLocation.getBetweenTwoDateAndTimes(start, end);

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("secondary.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();

            LocationDateSearchResultsController resultsController = fxmlLoader.getController();
            resultsController.setResultsList(searchResults);


            stage.setTitle("Location date search results"); //aici deschide fereastra noua goala
            stage.setScene(scene);
            stage.showAndWait();

        } catch (RuntimeException | IOException rex) {
            Common.showErrorMessage(rex.getMessage());
        }
    }

    public void btnShowSellersWithProductsClick(ActionEvent actionEvent) {
        try {
            List<SellersWithProducts> sellersWithProducts = this.serviceSeller.getSellersWithProducts();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("sellersWithProductsResults.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();

            SellersWithProductsController resultsController = fxmlLoader.getController();
            resultsController.setResultsList(sellersWithProducts);

            stage.setTitle("Sellers with their products"); //aici deschide fereastra noua goala
            stage.setScene(scene);
            stage.showAndWait();

        } catch (RuntimeException | IOException rex) {
            Common.showErrorMessage(rex.getMessage());
        }
    }

    public void btnShowLocationWithSellersAndProductsClick(ActionEvent actionEvent) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            LocalDateTime start = LocalDateTime.parse(txtDateTimeStart.getText(), formatter);
            LocalDateTime end = LocalDateTime.parse(txtDateTimeEnd.getText(), formatter);
            List<LocationsWithSellersAndProducts> locationsWithSellersAndProductsList = this.serviceSeller.getLocationsWithSellersAndProducts(start, end);

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("locationsWithSellersAndProducts.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();

            LocationsWithSellersAndProductsController resultsController = fxmlLoader.getController();
            resultsController.setResultsList(locationsWithSellersAndProductsList);

            stage.setTitle("Locations with Sellers and Products"); //aici deschide fereastra noua goala
            stage.setScene(scene);
            stage.showAndWait();

        } catch (RuntimeException | IOException rex) {
            Common.showErrorMessage(rex.getMessage());
        }
    }
}
