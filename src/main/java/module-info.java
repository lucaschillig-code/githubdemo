module com.example.wishsolitaire {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.wishsolitaire to javafx.fxml;
    exports com.example.wishsolitaire;
}