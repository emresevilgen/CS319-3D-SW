package rest;

import com.google.gson.Gson;
import models.*;
import rest.models.GeneralResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

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

        Gson gson = new Gson();
        String modeJson = gson.toJson(mode);

        // Create lobby request
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GeneralResponse<Lobby>> call = apiService.createLobby(username, lobbyName, token, modeJson);
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
                        if (userGeneralResponse != null)
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
    public GeneralResponse<Lobby> getLobby(String username, String token) {
        final GeneralResponse<Lobby>[] lobby = new GeneralResponse[1];
        final boolean[] finished = {false};

        // Get the lobby data and when the game starts move to the game screen
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<GeneralResponse<Lobby>> call = apiService.getLobby(username, token);
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
    public GeneralResponse<Lobby> exitLobby(String username, String token) {

        final GeneralResponse<Lobby>[] lobby = new GeneralResponse[1];
        final boolean[] finished = {false};

        // Exit lobby request
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GeneralResponse<Lobby>> call = apiService.exitLobby(username, token);
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
                        if (userGeneralResponse != null)
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
    public GeneralResponse<Lobby> getReady(String username, String token, boolean ready) {
        final GeneralResponse<Lobby>[] lobby = new GeneralResponse[1];
        final boolean[] finished = {false};

        // Get Ready lobby request
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        int i = 0;
        if (ready)
            i = 1;
        Call<GeneralResponse<Lobby>> call = apiService.getReady(username, token, i);
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
                        if (userGeneralResponse != null)
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
    public GeneralResponse<Game> getGameData(String username, String token) {
        final GeneralResponse<Game>[] game = new GeneralResponse[1];
        final boolean[] finished = {false};

        // Get Game lobby request
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GeneralResponse<Game>> call = apiService.getGameData(username, token);
        call.enqueue(new Callback<GeneralResponse<Game>>() {
            // If the connection is valid
            @Override
            public void onResponse(Call<GeneralResponse<Game>> call, Response<GeneralResponse<Game>> response) {
                if (response.body() != null) {
                    // Get the response
                    game[0] = response.body();
                }
                else {
                    try {
                        String errorResponse = response.errorBody().string();
                        GeneralResponse userGeneralResponse = new Gson().fromJson(errorResponse, GeneralResponse.class);
                        game[0] = new GeneralResponse<Game>();
                        if (userGeneralResponse != null)
                            game[0].message = userGeneralResponse.message;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                finished[0] = true;
            }

            // When connection is lost
            @Override
            public void onFailure(Call<GeneralResponse<Game>> call, Throwable t) {
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
        return game[0];
    }

    @Override
    public GeneralResponse<Game> commerce(String username, String token, Boolean isWithLeft, Materials commerceMaterials) {
        final GeneralResponse<Game>[] game = new GeneralResponse[1];
        final boolean[] finished = {false};

        // Get Game lobby request
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Gson gson = new Gson();
        String jsonMaterials = gson.toJson(commerceMaterials);
        int i = 0;
        if (isWithLeft)
            i = 1;
        Call<GeneralResponse<Game>> call = apiService.commerce(username, token, i, jsonMaterials);
        call.enqueue(new Callback<GeneralResponse<Game>>() {
            // If the connection is valid
            @Override
            public void onResponse(Call<GeneralResponse<Game>> call, Response<GeneralResponse<Game>> response) {
                if (response.body() != null) {
                    // Get the response
                    game[0] = response.body();
                }
                else {
                    try {
                        String errorResponse = response.errorBody().string();
                        GeneralResponse userGeneralResponse = new Gson().fromJson(errorResponse, GeneralResponse.class);
                        game[0] = new GeneralResponse<Game>();
                        if (userGeneralResponse != null)
                            game[0].message = userGeneralResponse.message;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                finished[0] = true;
            }

            // When connection is lost
            @Override
            public void onFailure(Call<GeneralResponse<Game>> call, Throwable t) {
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
        return game[0];    }

    @Override
    public GeneralResponse<Game> useCard(String username, String token, String cardId, int selectionType, boolean freeBuilding) {
        final GeneralResponse<Game>[] game = new GeneralResponse[1];
        final boolean[] finished = {false};

        // Get Game lobby request
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        int i = 0;
        if (freeBuilding)
            i = 1;
        Call<GeneralResponse<Game>> call = apiService.useCard(username, token, cardId, selectionType, i);
        call.enqueue(new Callback<GeneralResponse<Game>>() {
            // If the connection is valid
            @Override
            public void onResponse(Call<GeneralResponse<Game>> call, Response<GeneralResponse<Game>> response) {
                if (response.body() != null) {
                    // Get the response
                    game[0] = response.body();
                }
                else {
                    try {
                        String errorResponse = response.errorBody().string();
                        GeneralResponse userGeneralResponse = new Gson().fromJson(errorResponse, GeneralResponse.class);
                        game[0] = new GeneralResponse<Game>();
                        if (userGeneralResponse != null)
                            game[0].message = userGeneralResponse.message;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                finished[0] = true;
            }

            // When connection is lost
            @Override
            public void onFailure(Call<GeneralResponse<Game>> call, Throwable t) {
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
        return game[0];
    }

    @Override
    public GeneralResponse<Game> exitGame(String username, String token) {
        final GeneralResponse<Game>[] game = new GeneralResponse[1];
        final boolean[] finished = {false};

        // Get Game lobby request
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GeneralResponse<Game>> call = apiService.exitGame(username, token);
        call.enqueue(new Callback<GeneralResponse<Game>>() {
            // If the connection is valid
            @Override
            public void onResponse(Call<GeneralResponse<Game>> call, Response<GeneralResponse<Game>> response) {
                if (response.body() != null) {
                    // Get the response
                    game[0] = response.body();
                }
                else {
                    try {
                        String errorResponse = response.errorBody().string();
                        GeneralResponse userGeneralResponse = new Gson().fromJson(errorResponse, GeneralResponse.class);
                        game[0] = new GeneralResponse<Game>();
                        if (userGeneralResponse != null)
                            game[0].message = userGeneralResponse.message;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                finished[0] = true;
            }

            // When connection is lost
            @Override
            public void onFailure(Call<GeneralResponse<Game>> call, Throwable t) {
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
        return game[0];    }
}
