module com.example.zakatapps {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.zakatapps to javafx.fxml;
    exports com.example.zakatapps;
}