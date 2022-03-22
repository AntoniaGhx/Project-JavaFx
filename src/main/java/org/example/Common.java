package org.example;

import javafx.scene.control.Alert;

public class Common {
    public static void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(600, 500);
        alert.setTitle("Error!");
        alert.setHeaderText("Validation error:");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
