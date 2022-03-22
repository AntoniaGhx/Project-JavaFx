package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.domain.SellersWithProducts;

import java.util.List;

public class SellersWithProductsController {
    public TableView tblSellersWithProducts;
    public TableColumn colProductNames;
    public TableColumn colNameSeller;

    public void setResultsList(List<SellersWithProducts> sellersWithProducts) {
        ObservableList<SellersWithProducts> results = FXCollections.observableArrayList();
        results.addAll(sellersWithProducts);
        tblSellersWithProducts.setItems(results);
    }
}
