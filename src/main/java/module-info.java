module uiComponents {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires retrofit2;
    requires retrofit2.converter.gson;

    opens uiComponents to javafx.fxml;
    exports uiComponents;
}