package rest;

import com.google.gson.Gson;
import models.*;
import rest.models.GeneralResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class RequestHandler implements Requester {

    @Override
    public GeneralResponse<User> login(String username, String password){
        final GeneralResponse<User>[] user = new GeneralResponse[1];
        final boolean[] finished = {false};

        // Login request
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<GeneralResponse<User>> call = apiService.login(username, password);

        call.enqueue(new Callback<GeneralResponse<User>>() {
            // If the connection is valid
            @Override
            public void onResponse(Call<GeneralResponse<User>> call, Response<GeneralResponse<User>> response) {
                if (response.body() != null) {
                    // Get the response
                    user[0] = response.body();
                }
                else {
                    try {
                        String errorResponse = response.errorBody().string();
                        GeneralResponse userGeneralResponse = new Gson().fromJson(errorResponse, GeneralResponse.class);
                        user[0] = new GeneralResponse<User>();
                        user[0].message = userGeneralResponse.message;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                finished[0] = true;
            }

            // When connection is lost
            @Override
            public void onFailure(Call<GeneralResponse<User>> call, Throwable t) {
                finished[0] = true;
            }
        });

        while (!finished[0]) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return user[0];
    }

    @Override
    public GeneralResponse<User> signUp(String name, String username, String password) {
        final GeneralResponse<User>[] user = new GeneralResponse[1];
        final boolean[] finished = {false};

        // Sign up request
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<GeneralResponse<User>> call = apiService.signUp(name, username, password);
        call.enqueue(new Callback<GeneralResponse<User>>() {
            // If the connection is valid
            @Override
            public void onResponse(Call<GeneralResponse<User>> call, Response<GeneralResponse<User>> response) {
                if (response.body() != null) {
                    // Get the response
                    user[0] = response.body();
                }
                else {
                    try {
                        String errorResponse = response.errorBody().string();
                        GeneralResponse userGeneralResponse = new Gson().fromJson(errorResponse, GeneralResponse.class);
                        user[0] = new GeneralResponse<User>();
                        user[0].message = userGeneralResponse.message;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                finished[0] = true;
            }


            // When connection is lost
            @Override
            public void onFailure(Call<GeneralResponse<User>> call, Throwable t) {
                finished[0] = true;
            }

        });

        while (!finished[0]) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return user[0];
    }

    @Override
    public GeneralResponse<User> updateUser(String name, String password, String token) {
        return null;
    }

    @Override
    public GeneralResponse<Lobby> enterLobby(String username, String token, String lobbyCode) {
        final GeneralResponse<Lobby>[] lobby = new GeneralResponse[1];
        final boolean[] finished = {false};

        // Enter Lobby Request
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GeneralResponse<Lobby>> call = apiService.enterLobby(username, token, lobbyCode);
        call.enqueue(new Callback<GeneralResponse<Lobby>>() {

            // If the connection is valid
            @Override
            public void onResponse(Call<GeneralResponse<Lobby>> call, Response<GeneralResponse<Lobby>> response) {
                if (response.body() != null) {
                    // Get the response
                    lobby[0] = response.body();
                }
                else {
                    try {
                        String errorResponse = response.errorBody().string();
                        GeneralResponse userGeneralResponse = new Gson().fromJson(errorResponse, GeneralResponse.class);
                        lobby[0] = new GeneralResponse<Lobby>();
                        lobby[0].message = userGeneralResponse.message;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                finished[0] = true;
            }

            // When connection is lost
            @Override
            public void onFailure(Call<GeneralResponse<Lobby>> call, Throwable t) {
                finished[0] = true;
            }

        });

        while (!finished[0]) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return lobby[0];
    }

    @Override
    public GeneralResponse<Lobby> createLobby(String username, String lobbyName, String token, Mode mode) {
        final GeneralResponse<Lobby>[] lobby = new GeneralResponse[1];
        final boolean[] finished = {false};

        // Create lobby request
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GeneralResponse<Lobby>> call = apiService.createLobby(username, lobbyName, token, mode);
        call.enqueue(new Callback<GeneralResponse<Lobby>>() {

            // If the connection is valid
            @Override
            public void onResponse(Call<GeneralResponse<Lobby>> call, Response<GeneralResponse<Lobby>> response) {
                if (response.body() != null) {
                    // Get the response
                    lobby[0] = response.body();
                }
                else {
                    try {
                        String errorResponse = response.errorBody().string();
                        GeneralResponse userGeneralResponse = new Gson().fromJson(errorResponse, GeneralResponse.class);
                        lobby[0] = new GeneralResponse<Lobby>();
                        lobby[0].message = userGeneralResponse.message;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                finished[0] = true;
            }

            // When connection is lost
            @Override
            public void onFailure(Call<GeneralResponse<Lobby>> call, Throwable t) {
                finished[0] = true;
            }
        });

        while (!finished[0]) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return lobby[0];
    }

    @Override
    public GeneralResponse<Lobby> getLobby(String username, String token, String lobbyId) {
        final GeneralResponse<Lobby>[] lobby = new GeneralResponse[1];
        final boolean[] finished = {false};

        // Get the lobby data and when the game starts move to the game screen
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<GeneralResponse<Lobby>> call = apiService.getLobby(username, token, lobbyId);
        call.enqueue(new Callback<GeneralResponse<Lobby>>() {
            // If the connection is valid
            @Override
            public void onResponse(Call<GeneralResponse<Lobby>> call, Response<GeneralResponse<Lobby>> response) {
                if (response.body() != null) {
                    // Get the response
                    lobby[0] = response.body();
                }
                else {

                    try {
                        String errorResponse = response.errorBody().string();
                        GeneralResponse userGeneralResponse = new Gson().fromJson(errorResponse, GeneralResponse.class);
                        lobby[0] = new GeneralResponse<Lobby>();
                        lobby[0].message = userGeneralResponse.message;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                finished[0] = true;
            }

            // When connection is lost
            @Override
            public void onFailure(Call<GeneralResponse<Lobby>> call, Throwable t) {
                finished[0] = true;
            }
        });

        while (!finished[0]) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return lobby[0];
    }

    @Override
    public GeneralResponse<Lobby> exitLobby(String username, String token, String lobbyId) {
        return null;
    }

    @Override
    public GeneralResponse<Lobby> getReady(String username, String token, boolean ready, String lobbyId) {
        return null;
    }

    @Override
    public GeneralResponse<Game> startGame(String username, String token, String lobbyCode) {
        return null;
    }

    @Override
    public GeneralResponse<Game> getGameData(String username, String token, String gameId, String playerId) {
        return null;
    }

    @Override
    public GeneralResponse<Game> getReadyGame(String username, String token, String gameId, boolean ready) {
        return null;
    }

    @Override
    public GeneralResponse<Game> commerce(String gameId, String username, String token, String sellerPlayerId, int[] materialTypes, int[] materialAmounts) {
        return null;
    }

    @Override
    public GeneralResponse<Game> pickCard(String gameId, String username, String token, String selectedCardId) {
        return null;
    }
}
