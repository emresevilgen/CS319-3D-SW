package uiComponents;

import javafx.event.ActionEvent;

import javafx.scene.control.Button;
import javafx.stage.Stage;


import static uiComponents.SceneChanger.moveToMainMenu;

public class SettingsController {
    public Button cancelButton;
    public Button saveButton;


    public void save(ActionEvent event) throws Exception {
        moveToMainMenu((Stage)saveButton.getScene().getWindow());
    }

    public void cancel(ActionEvent event) throws Exception {
        moveToMainMenu((Stage)cancelButton.getScene().getWindow());
    }
}
