package uiComponents;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;
import rest.ApiClient;
import rest.ApiInterface;
import rest.models.GeneralResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SignUpController {
    public Button signUpButton;
    public TextField nameField;
    public TextField usernameField;
    public PasswordField passwordField;

    public void moveToMainMenu() {
        Stage stage;
        stage = (Stage) signUpButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        Parent root = null;
        try {
            fileInputStream = new FileInputStream(new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"fxml"+File.separator+"MainMenu.fxml"));
            root = loader.load(fileInputStream);
            if (root != null) {
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void signUp(ActionEvent actionEvent) {
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<GeneralResponse<User>> call = apiService.signUp(name, username, password);
        call.enqueue(new Callback<GeneralResponse<User>>() {
            @Override
            public void onResponse(Call<GeneralResponse<User>> call, Response<GeneralResponse<User>> response) {
                if (response.body() != null) {

                    GeneralResponse<User> userGeneralResponse = response.body();
                    System.out.println(userGeneralResponse.toString());

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (userGeneralResponse.success)
                                moveToMainMenu();
                            else {
                                showErrorMessage(userGeneralResponse.message);

                            }
                        }
                    });

                } else {
                    //TODO Show error message
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            showErrorMessage("There is something wrong with the connection");

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse<User>> call, Throwable t) {
                showErrorMessage("There is something wrong with the connection");

            }
        });

    }

    private void showErrorMessage(String errorMsg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMsg);
        alert.showAndWait();
    }
}

