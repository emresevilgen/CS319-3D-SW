module uiComponents {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires retrofit2;
    requires gson;
    requires retrofit2.converter.gson;
    requires java.sql;
    requires okhttp3;
    requires okio;
    requires freetts;

    opens uiComponents to javafx.fxml;
    exports uiComponents;
    exports rest.models;
    exports models;
}