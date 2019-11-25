package uiComponents;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Lobby;
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
import java.util.Optional;
import java.util.ResourceBundle;

import static uiComponents.SceneChanger.*;

public class MainMenuController implements Initializable {

    @FXML
     public Button createLobbyButton;
    @FXML
    public Button joinLobbyButton;
    @FXML
    public Button seeRankingsButton;
    @FXML
    public Button settingsButton;
    @FXML
    public Button creditsButton;
    @FXML
    public Button signOutButton;
    @FXML
    public Button exitButton;


    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #b38632; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #b38632; -fx-opacity: 0.85;";

    public void createLobby(ActionEvent event) throws Exception {
        moveToCreateLobby((Stage)createLobbyButton.getScene().getWindow());
    }

    public void joinLobby(ActionEvent event) throws Exception {
        TextInputDialog dialog = new TextInputDialog("Lobby Code");
        dialog.setTitle("Join to the Existing Lobby");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        ((Stage)dialog.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);

        dialog.setContentText("Enter the code of the lobby:");
        String lobbyCode = null;

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
           lobbyCode = result.get();

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<GeneralResponse<Lobby>> call = apiService.enterLobby(Main.user.userName, Main.user.token, lobbyCode);
            call.enqueue(new Callback<GeneralResponse<Lobby>>() {
                @Override
                public void onResponse(Call<GeneralResponse<Lobby>> call, Response<GeneralResponse<Lobby>> response) {
                    if (response.body() != null) {

                        GeneralResponse<Lobby> userGeneralResponse = response.body();

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (userGeneralResponse.success) {
                                    Main.lobby = userGeneralResponse.payload;
                                    moveToSeeThePlayers((Stage) joinLobbyButton.getScene().getWindow(), false);
                                }
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
                public void onFailure(Call<GeneralResponse<Lobby>> call, Throwable t) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            showErrorMessage("There is something wrong with the connection");

                        }
                    });
                }
            });


        }
    }

    public void rankings(ActionEvent event) throws Exception {
        moveToRankings((Stage)seeRankingsButton.getScene().getWindow());

    }

    public void settings(ActionEvent event) throws Exception {
        moveToSettings((Stage)settingsButton.getScene().getWindow());

    }

    public void credits(ActionEvent event) throws Exception {
        moveToCredits((Stage)creditsButton.getScene().getWindow());

    }

    public void exit(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ((Stage)alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        alert.setTitle("Exit Game");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Do you want to exit?");
        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonNo, buttonYes);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonYes){
            SceneChanger.exit((Stage)exitButton.getScene().getWindow());
        } else {
            alert.close();
        }
    }

    public void signOut(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ((Stage)alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        alert.setTitle("Sign Out");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Do you want to sign out?");
        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonNo, buttonYes);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonYes){
            // send a request to the server
            moveToSignIn((Stage)signOutButton.getScene().getWindow());
        } else {
            alert.close();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createLobbyButton.setOnMouseEntered(e -> createLobbyButton.setStyle(HOVERED_BUTTON_STYLE));
        createLobbyButton.setOnMouseExited(e -> createLobbyButton.setStyle(IDLE_BUTTON_STYLE));
        joinLobbyButton.setOnMouseEntered(e -> joinLobbyButton.setStyle(HOVERED_BUTTON_STYLE));
        joinLobbyButton.setOnMouseExited(e -> joinLobbyButton.setStyle(IDLE_BUTTON_STYLE));
        seeRankingsButton.setOnMouseEntered(e -> seeRankingsButton.setStyle(HOVERED_BUTTON_STYLE));
        seeRankingsButton.setOnMouseExited(e -> seeRankingsButton.setStyle(IDLE_BUTTON_STYLE));
        settingsButton.setOnMouseEntered(e -> settingsButton.setStyle(HOVERED_BUTTON_STYLE));
        settingsButton.setOnMouseExited(e -> settingsButton.setStyle(IDLE_BUTTON_STYLE));
        creditsButton.setOnMouseEntered(e -> creditsButton.setStyle(HOVERED_BUTTON_STYLE));
        creditsButton.setOnMouseExited(e -> creditsButton.setStyle(IDLE_BUTTON_STYLE));
        signOutButton.setOnMouseEntered(e -> signOutButton.setStyle(HOVERED_BUTTON_STYLE));
        signOutButton.setOnMouseExited(e -> signOutButton.setStyle(IDLE_BUTTON_STYLE));
        exitButton.setOnMouseEntered(e -> exitButton.setStyle(HOVERED_BUTTON_STYLE));
        exitButton.setOnMouseExited(e -> exitButton.setStyle(IDLE_BUTTON_STYLE));
    }

    private void showErrorMessage(String errorMsg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMsg);
        alert.showAndWait();
    }

}
