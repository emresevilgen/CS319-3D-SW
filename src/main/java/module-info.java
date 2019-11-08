module uiComponents {
    requires javafx.controls;
    requires javafx.fxml;

    opens uiComponents to javafx.fxml;
    exports uiComponents;
}