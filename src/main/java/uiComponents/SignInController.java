package uiComponents;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignInController {

    public Button signUpButton;
    public Button signInButton;
    public TextField usernameField;
    public PasswordField passwordField;

    public void moveToSignUp(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        stage = (Stage) signUpButton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void moveToMainMenu(ActionEvent event) throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();

        signIn(username,password);



    }

    public void signIn(String username, String password){
        /*ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


        Call<UserResponse> call = apiService.login(username, password);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = null;
                if (response.body() != null) {

                    userResponse = response.body();

                    if(userResponse == null){
                        //TODO Show error message
                        System.out.println("EROR!1");
                    } else {
                        System.out.println(userResponse.toString());
                        *//*Stage stage;
                        Parent root = null;

                        stage = (Stage) signInButton.getScene().getWindow();
                        try {
                            root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();*//*
                    }


                } else {
                    //TODO Show error message
                    System.out.println("EROR!2");
                }
            }

            @Override
            public void onFailure(Call<UserResponse>call, Throwable t) {
                // CurrencyLog error here since request failed

            }
        });*/
    }
}
