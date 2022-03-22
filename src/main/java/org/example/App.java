package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.domain.*;
import org.example.repository.IRepository;
import org.example.repository.InMemoryRepository;
import org.example.service.ServiceLocation;
import org.example.service.ServiceSeller;
import org.example.service.ServiceProduct;
import org.example.service.UndoRedoManager;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {

    private  Scene scene;

    @Override
    public void start(Stage stage) throws IOException, ParseException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("primary.fxml"));
        Parent parentFxml = fxmlLoader.load();
        this.scene = new Scene(parentFxml, 640, 480);

        IRepository<Product> productIRepository = new InMemoryRepository<>();
        IRepository<Seller> sellerIRepository = new InMemoryRepository<>();
        IRepository<Location> locationIRepository = new InMemoryRepository<>();
        UndoRedoManager undoRedoManager = new UndoRedoManager();
        LocationValidator locationValidator = new LocationValidator();
        SellerValidator sellerValidator = new SellerValidator();
        ProductValidator productValidator = new ProductValidator();

        ServiceProduct serviceProduct = new ServiceProduct(productIRepository, sellerIRepository, locationIRepository, undoRedoManager, productValidator);
        ServiceSeller serviceSeller = new ServiceSeller(sellerIRepository, productIRepository, locationIRepository, undoRedoManager, sellerValidator);
        ServiceLocation serviceLocation = new ServiceLocation(locationIRepository, productIRepository, sellerIRepository, undoRedoManager, locationValidator);

        serviceProduct.addProduct(1, "apples", 10, 20);
        serviceProduct.addProduct(2, "oranges", 40, 60);
        serviceProduct.addProduct(3, "tomatoes", 100, 50);

        List<Integer> productList1 = new ArrayList<Integer>();
        productList1.add(1);
        productList1.add(2);

        List<Integer> productList2 = new ArrayList<Integer>();
        productList2.add(1);
        productList2.add(3);

        serviceLocation.addLocation(65, "20.03.2021 15:32", "20.04.2021 15:15");
        serviceLocation.addLocation(28,"26.03.2021 19:40", "26.05.2021 19:40");

        serviceSeller.addSeller(1,"Ana", true, true, 65, productList1, "20.03.2021 15:32", "20.04.2021 15:15");
        serviceSeller.addSeller(3,"Maria", false, true,  2, productList2, "01.02.2021 15:15", "02.02.2021 15:15");

        PrimaryController primaryController = fxmlLoader.getController();
        primaryController.setServices(serviceProduct, serviceSeller, serviceLocation, undoRedoManager);

        stage.setTitle("Market manager");
        stage.setScene(this.scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}