package uiComponents;

import com.google.gson.Gson;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.DataHandler;
import models.Lobby;
import rest.ApiClient;
import rest.ApiInterface;
import rest.Requester;
import rest.ServerConnectionHandler;
import rest.models.GeneralResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SeeThePlayers2Scene implements Initializable {
    @FXML
    public Button leaveButton;
    @FXML
    public Button readyButton;
    @FXML
    public Label firstNameLabel;
    @FXML
    public ImageView firstPlayerStatus;
    @FXML
    public Label secondNameLabel;
    @FXML
    public ImageView secondPlayerStatus;
    @FXML
    public Label thirdNameLabel;
    @FXML
    public ImageView thirdPlayerStatus;
    @FXML
    public Label fourthNameLabel;
    @FXML
    public ImageView fourthPlayerStatus;
    @FXML
    public Label lobbyCode;

    // Button colors for hovered and not
    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #817277; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #817277; -fx-opacity: 0.85;";

    // Label array to change the usernames and states of the players
    Label [] labelsName = new Label[4];
    Label [] labelsState = new Label[4];

    // To send a request at every second
    Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
        // Update the lobby info at every second
        @Override
        public void handle(ActionEvent event) {
            update();
        }
    }));

    private boolean showError = true;

    // Be ready button listener
    public void beReady(ActionEvent event) throws Exception {
        //------------------------
        //------------------------
        //send request to the server
        //------------------------
        //------------------------
        DataHandler dataHandler = DataHandler.getInstance();

        Thread requestThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Requester requester = ServerConnectionHandler.getInstance().getRequester();
                GeneralResponse<Lobby> lobbyResponse = requester.getReady(dataHandler.getUser().userName, dataHandler.getUser().token, true);
                if (lobbyResponse != null) {
                    if (lobbyResponse.success) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                DataHandler.getInstance().setLobby(lobbyResponse.payload);
                            }
                        });
                    }
                    else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                showErrorMessage(lobbyResponse.message);
                            }
                        });
                    }
                }
                else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            showErrorMessage("There is something wrong with the connection");
                        }
                    });
                }
            }
        });
        requestThread.start();


    }

    // Leave button listener
    public void leave(ActionEvent event) throws Exception {
        //------------------------
        //------------------------
        // send a request to the server and move to the main menu
        //------------------------
        //------------------------

        DataHandler dataHandler = DataHandler.getInstance();

        Thread requestThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Requester requester = ServerConnectionHandler.getInstance().getRequester();
                GeneralResponse<Lobby> lobbyResponse = requester.exitLobby(dataHandler.getUser().userName, dataHandler.getUser().token);
                if (lobbyResponse != null) {
                    if (lobbyResponse.success) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                dataHandler.setLobby(lobbyResponse.payload);
                                // To stop the requests and move to main menu
                                timeLine.stop();

                                dataHandler.setLobby(null);
                                SceneHandler.getInstance().moveToMainMenu();
                            }
                        });
                    }
                    else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                showErrorMessage(lobbyResponse.message);
                            }
                        });
                    }
                }
                else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            showErrorMessage("There is something wrong with the connection");
                        }
                    });
                }
            }
        });
        requestThread.start();
    }

    // Send request and update the lobby object
    public void update() {

        DataHandler dataHandler = DataHandler.getInstance();

        if (dataHandler.getLobby().lobbyAdmin.equals(dataHandler.getUser().userName)){
            timeLine.stop();
            SceneHandler.getInstance().moveToSeeThePlayers(true);
            return;
        }

        Thread requestThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Requester requester = ServerConnectionHandler.getInstance().getRequester();
                GeneralResponse<Lobby> lobbyResponse = requester.getLobby(dataHandler.getUser().userName, dataHandler.getUser().token);
                if (lobbyResponse != null) {
                    if (lobbyResponse.success) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                DataHandler.getInstance().setLobby(lobbyResponse.payload);
                            }
                        });
                    }
                    else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                showErrorMessage(lobbyResponse.message);
                            }
                        });
                    }
                }
                else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            showErrorMessage("There is something wrong with the connection");
                        }
                    });
                }
            }
        });
        requestThread.start();

        try {
            FileInputStream inputStream = new FileInputStream(Constants.CROSS_IMAGE);
            FileInputStream inputStream2 = new FileInputStream(Constants.CHECK_IMAGE);
            //Change status of the users
            firstPlayerStatus.setImage(null);
            if(dataHandler.getLobby().lobbyUsers.length > 0 &&  dataHandler.getLobby().lobbyUsers[0] !=  null)
            {
                if (!dataHandler.getLobby().lobbyUsers[0].isReady || !dataHandler.getLobby().lobbyUsers[0].isActive){
                    Image crossImage = new Image(inputStream);
                    firstPlayerStatus.setImage(crossImage);
                }
                else{
                    Image checkImage = new Image(inputStream2);
                    firstPlayerStatus.setImage(checkImage);
                }
            }
            secondPlayerStatus.setImage(null);
            if(dataHandler.getLobby().lobbyUsers.length > 1 &&  dataHandler.getLobby().lobbyUsers[1] !=  null)
            {
                if (!dataHandler.getLobby().lobbyUsers[1].isReady || !dataHandler.getLobby().lobbyUsers[1].isActive){
                    Image crossImage = new Image(inputStream);
                    secondPlayerStatus.setImage(crossImage);
                }
                else{
                    Image checkImage = new Image(inputStream2);
                    secondPlayerStatus.setImage(checkImage);
                }
            }
            thirdPlayerStatus.setImage(null);
            if(dataHandler.getLobby().lobbyUsers.length > 2 &&  dataHandler.getLobby().lobbyUsers[2] !=  null)
            {
                if (!dataHandler.getLobby().lobbyUsers[2].isReady || !dataHandler.getLobby().lobbyUsers[2].isActive){
                    Image crossImage = new Image(inputStream);
                    thirdPlayerStatus.setImage(crossImage);
                }
                else{
                    Image checkImage = new Image(inputStream2);
                    thirdPlayerStatus.setImage(checkImage);
                }
            }
            fourthPlayerStatus.setImage(null);
            if(dataHandler.getLobby().lobbyUsers.length == 4 &&  dataHandler.getLobby().lobbyUsers[3] !=  null)
            {
                if (!dataHandler.getLobby().lobbyUsers[3].isReady || !dataHandler.getLobby().lobbyUsers[3].isActive){
                    Image crossImage = new Image(inputStream);
                    fourthPlayerStatus.setImage(crossImage);
                }
                else{
                    Image checkImage = new Image(inputStream2);
                    fourthPlayerStatus.setImage(checkImage);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Lobby lobby = dataHandler.getLobby();

        // Clear the labels
        for (int i = 0; i < labelsName.length; i++)
            labelsName[i].setText("");

        // Update the labels with the response
        for (int i = 0; i < lobby.lobbyUsers.length; i++) {
            labelsName[i].setText(lobby.lobbyUsers[i].username);
        }

        // -------------------
        // -------------------
        // if game starts then
        //         timeLine.stop();
        // -------------------
        // -------------------

    }

    // Initializing function
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Start sending requests
        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();

        // Setting the mouse entered and exited listeners for hover effect
        leaveButton.setOnMouseEntered(e -> leaveButton.setStyle(HOVERED_BUTTON_STYLE));
        leaveButton.setOnMouseExited(e -> leaveButton.setStyle(IDLE_BUTTON_STYLE));
        readyButton.setOnMouseEntered(e -> readyButton.setStyle(HOVERED_BUTTON_STYLE));
        readyButton.setOnMouseExited(e -> readyButton.setStyle(IDLE_BUTTON_STYLE));

        //Write the lobby code
        lobbyCode.setText("The lobby code: "+ DataHandler.getInstance().getLobby().lobbyCode );

        // Initialize the label arrays
        labelsName[0] = firstNameLabel;
        labelsName[1] = secondNameLabel;
        labelsName[2] = thirdNameLabel;
        labelsName[3] = fourthNameLabel;

        // Get the lobby data and initialize the people,
        update();
    }

    // Error message
    private void showErrorMessage(String errorMsg){
        if (showError) {
            showError = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(errorMsg);
            alert.initOwner(readyButton.getScene().getWindow());
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK || result.get() == ButtonType.CLOSE)
                showError = true;
        }
    }
}
