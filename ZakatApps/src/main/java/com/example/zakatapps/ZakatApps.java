package com.example.zakatapps;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ZakatApps extends Application {

    private static final double NISAB_CASH = 0.025; // 2.5% Zakat rate for cash
    private static final double NISAB_SHEEP = 0.01; // 1 sheep for every 40 sheep
    private static final double NISAB_COW = 0.02; // 1 cow for every 30 cows
    private static final double NISAB_CAMEL = 0.025; // 1 camel for every 5 camels

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Aplikasi Zakat");

        // Create a GridPane layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setStyle("-fx-background-color: #f0f8ff;"); // Light blue background

        // Create UI elements
        Label titleLabel = new Label("Kalkulator Zakat");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);
        GridPane.setConstraints(titleLabel, 0, 0, 2, 1);

        // Cash Assets
        Label cashLabel = new Label("Total Aset Uang Tunai:");
        GridPane.setConstraints(cashLabel, 0, 1);
        TextField cashField = new TextField();
        cashField.setPromptText("Masukkan Aset Uang Tunai");
        GridPane.setConstraints(cashField, 1, 1);

        // Gold Assets
        Label goldLabel = new Label("Total Aset Emas (gr):");
        GridPane.setConstraints(goldLabel, 0, 2);
        TextField goldField = new TextField();
        goldField.setPromptText("Masukkan Aset Emas dalam gram");
        GridPane.setConstraints(goldField, 1, 2);

        // Silver Assets
        Label silverLabel = new Label("Total Aset Perak (gr):");
        GridPane.setConstraints(silverLabel, 0, 3);
        TextField silverField = new TextField();
        silverField.setPromptText("Masukkan Aset Perak dalam gram");
        GridPane.setConstraints(silverField, 1, 3);

        // Liabilities
        Label liabilitiesLabel = new Label("Total Liabilitas:");
        GridPane.setConstraints(liabilitiesLabel, 0, 4);
        TextField liabilitiesField = new TextField();
        liabilitiesField.setPromptText("Masukkan Total Liabilitas");
        GridPane.setConstraints(liabilitiesField, 1, 4);

        // Animal Inputs
        Label sheepLabel = new Label("Jumlah Domba:");
        GridPane.setConstraints(sheepLabel, 0, 5);
        TextField sheepField = new TextField();
        sheepField.setPromptText("Masukkan Jumlah Domba");
        GridPane.setConstraints(sheepField, 1, 5);

        Label cowLabel = new Label("Jumlah Sapi:");
        GridPane.setConstraints(cowLabel, 0, 6);
        TextField cowField = new TextField();
        cowField.setPromptText("Masukkan Jumlah Sapi");
        GridPane.setConstraints(cowField, 1, 6);

        Label camelLabel = new Label("Jumlah Unta:");
        GridPane.setConstraints(camelLabel, 0, 7);
        TextField camelField = new TextField();
        camelField.setPromptText("Masukkan Jumlah Unta");
        GridPane.setConstraints(camelField, 1, 7);

        // Calculate Button
        Button calculateButton = new Button("Menghitung Zakat");
        calculateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5px;");
        GridPane.setConstraints(calculateButton, 0 , 8);

        // Result Label
        Label resultLabel = new Label("Zakat Yang Harus Dibayarkan : ");
        resultLabel.setFont(new Font("Arial", 16));
        resultLabel.setTextFill(Color.DARKGREEN);
        GridPane.setConstraints(resultLabel, 1, 8);

        // Add action to the button
        calculateButton.setOnAction(e -> {
            try {
                double cashAssets = Double.parseDouble(cashField.getText());
                double goldAssets = Double.parseDouble(goldField.getText()) * 1689000; // Assuming gold price is 1689000 per gram
                double silverAssets = Double.parseDouble(silverField.getText()) * 11000; // Assuming silver price is 11000 per gram
                double liabilities = Double.parseDouble(liabilitiesField.getText());
                int numberOfSheep = Integer.parseInt(sheepField.getText());
                int numberOfCows = Integer.parseInt(cowField.getText());
                int numberOfCamels = Integer.parseInt(camelField.getText());

                double totalAssets = cashAssets + goldAssets + silverAssets - liabilities;
                double zakatDue = calculateZakat(totalAssets, numberOfSheep, numberOfCows, numberOfCamels);

                resultLabel.setText("Zakat Yang Harus Dibayarkan : " + String.format("%.2f", zakatDue));
            } catch (NumberFormatException ex) {
                showAlert("Kesalahan Input", "Tolong Masukkan Angka Yang Valid.");
            }
        });

        // Add all elements to the grid
        grid.getChildren().addAll(titleLabel, cashLabel, cashField, goldLabel, goldField, silverLabel, silverField, liabilitiesLabel, liabilitiesField, sheepLabel, sheepField, cowLabel, cowField, camelLabel, camelField, calculateButton, resultLabel);

        // Create a scene and set it on the stage
        Scene scene = new Scene(grid, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private double calculateZakat(double totalAssets, int numberOfSheep, int numberOfCows, int numberOfCamels) {
        double zakatFromCash = totalAssets < 0 ? 0 : totalAssets * NISAB_CASH;

        // Calculate animal zakat
        double zakatFromAnimals = 0;
        if (numberOfSheep >= 40) {
            zakatFromAnimals += Math.floor(numberOfSheep / 40) * NISAB_SHEEP;
        }
        if (numberOfCows >= 30) {
            zakatFromAnimals += Math.floor(numberOfCows / 30) * NISAB_COW;
        }
        if (numberOfCamels >= 5) {
            zakatFromAnimals += Math.floor(numberOfCamels / 5) * NISAB_CAMEL;
        }

        return zakatFromCash + zakatFromAnimals;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}