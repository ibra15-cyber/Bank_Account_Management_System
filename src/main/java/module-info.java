module com.ibra.bankingapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens com.ibra.bankingapp to javafx.fxml;
    exports com.ibra.bankingapp;
}