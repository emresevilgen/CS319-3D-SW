package uiComponents;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.User;
import rest.ApiClient;
import rest.ApiInterface;
import rest.models.GeneralResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
            fileInputStream = new FileInputStream(new File(Constants.MAIN_MENU_FXML));
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
                    try {
                        String errorResponse = response.errorBody().string();
                        GeneralResponse userGeneralResponse =  new Gson().fromJson(errorResponse, GeneralResponse.class);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                showErrorMessage(userGeneralResponse.message);

                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse<User>> call, Throwable t) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        showErrorMessage("There is something wrong with the connection");

                    }
                });
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

