module com.example.masterquery {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.desktop;

    opens com.example.masterquery to javafx.fxml;
    exports com.example.masterquery;
}