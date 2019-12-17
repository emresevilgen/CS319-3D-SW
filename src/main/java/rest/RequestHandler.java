package rest;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import models.*;
import rest.models.GeneralResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class RequestHandler implements Requester {

    @Override
    public User login(String username, String password){
        // Login request
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<GeneralResponse<User>> call = apiService.login(username, password);

        call.enqueue(new Callback<GeneralResponse<User>>() {
            // If the connection is valid
            @Override
            public void onResponse(Call<GeneralResponse<User>> call, Response<GeneralResponse<User>> response) {
                if (response.body() != null) {
                    // Get the response
                    GeneralResponse<User> userGeneralResponse = response.body();

                    // If success update lobby and move to see the players
                    if (userGeneralResponse.success) {
                        DataHandler.getInstance().setUser(userGeneralResponse.payload);
                    }
                    // Otherwise error message
                    else
                        showErrorMessage(userGeneralResponse.message);
                }
                // When the response's body is null
                else {
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

            // When connection is lost
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
        return null;
    }

    @Override
    public User signUp(String name, String username, String password) {
        final User[] user = new User[1];

        // Sign up request
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<GeneralResponse<User>> call = apiService.signUp(name, username, password);
        call.enqueue(new Callback<GeneralResponse<User>>() {
            // If the connection is valid
            @Override
            public void onResponse(Call<GeneralResponse<User>> call, Response<GeneralResponse<User>> response) {
                if (response.body() != null) {
                    // Get the response
                    GeneralResponse<User> userGeneralResponse = response.body();

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // If success update lobby and move to see the players
                            if (userGeneralResponse.success) {
                                user[0] = userGeneralResponse.payload;
                            }
                            // Otherwise error message
                            else {
                                showErrorMessage(userGeneralResponse.message);

                            }
                        }
                    });
                }
                // When the response's body is null
                else {
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

            // When connection is lost
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
        return user[0];
    }

    @Override
    public User updateUser(String name, String password, String token) {
        return null;
    }

    @Override
    public Lobby enterLobby(String username, String token, String lobbyCode) {
        // Enter Lobby Request
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Lobby[] lobby = new Lobby[1];
        Call<GeneralResponse<Lobby>> call = apiService.enterLobby(username, token, lobbyCode);
        call.enqueue(new Callback<GeneralResponse<Lobby>>() {

            // If the connection is valid
            @Override
            public void onResponse(Call<GeneralResponse<Lobby>> call, Response<GeneralResponse<Lobby>> response) {
                if (response.body() != null) {

                    // Get the response
                    GeneralResponse<Lobby> userGeneralResponse = response.body();

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // If success update lobby and move to see the players
                            if (userGeneralResponse.success) {
                                lobby[0] = userGeneralResponse.payload;
                            }
                            // Otherwise error message
                            else {
                                showErrorMessage(userGeneralResponse.message);

                            }
                        }
                    });

                }
                // When the response's body is null
                else {
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
            // When connection is lost
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
        return lobby[0];
    }

    @Override
    public Lobby createLobby(String username, String lobbyName, String token, Mode mode) {

        // Create lobby request
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Lobby[] lobby = new Lobby[1];
        Call<GeneralResponse<Lobby>> call = apiService.createLobby(username, lobbyName, token, mode);
        call.enqueue(new Callback<GeneralResponse<Lobby>>() {

            // If the connection is valid
            @Override
            public void onResponse(Call<GeneralResponse<Lobby>> call, Response<GeneralResponse<Lobby>> response) {
                if (response.body() != null) {

                    // Get the response
                    GeneralResponse<Lobby> userGeneralResponse = response.body();

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // If success update lobby and move to see the players
                            if (userGeneralResponse.success) {
                                lobby[0] = userGeneralResponse.payload;
                            }
                            // Otherwise error message
                            else {
                                showErrorMessage(userGeneralResponse.message);

                            }
                        }
                    });
                }
                // When the response's body is null
                else {
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

            // When connection is lost
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
        return lobby[0];
    }

    @Override
    public Lobby getLobby(String username, String token, String lobbyId) {
        final Lobby[] lobby = new Lobby[1];
        // Get the lobby data and when the game starts move to the game screen
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<GeneralResponse<Lobby>> call = apiService.getLobby(username, token, lobbyId);
        call.enqueue(new Callback<GeneralResponse<Lobby>>() {
            // If the connection is valid
            @Override
            public void onResponse(Call<GeneralResponse<Lobby>> call, Response<GeneralResponse<Lobby>> response) {
                if (response.body() != null) {

                    // Get the response
                    GeneralResponse<Lobby> userGeneralResponse = response.body();

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // If success update lobby and move to see the players
                            if (userGeneralResponse.success) {
                                lobby[0] = userGeneralResponse.payload;
                            }
                            // Otherwise error message
                            else {
                                showErrorMessage(userGeneralResponse.message);

                            }
                        }
                    });
                }
                // When the response's body is null
                else {
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

            // When connection is lost
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
        return lobby[0];
    }

    @Override
    public Lobby exitLobby(String username, String token, String lobbyId) {
        return null;
    }

    @Override
    public Lobby getReady(String username, String token, boolean ready, String lobbyId) {
        return null;
    }

    @Override
    public Game startGame(String username, String token, String lobbyCode) {
        return null;
    }

    @Override
    public Game getGameData(String username, String token, String gameId, String playerId) {
        return null;
    }

    @Override
    public Game getReadyGame(String username, String token, String gameId, boolean ready) {
        return null;
    }

    @Override
    public Game commerce(String gameId, String username, String token, String sellerPlayerId, int[] materialTypes, int[] materialAmounts) {
        return null;
    }

    @Override
    public Game pickCard(String gameId, String username, String token, String selectedCardId) {
        return null;
    }


    // Error message
    private void showErrorMessage(String errorMsg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMsg);
        alert.showAndWait();
    }
}
