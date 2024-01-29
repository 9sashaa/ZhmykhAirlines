package il.ac.sce.project_aleksandrkisliak.controller;

import java.net.URL;
import java.util.ResourceBundle;

import il.ac.sce.project_aleksandrkisliak.Database.DatabaseHandler;
import il.ac.sce.project_aleksandrkisliak.MainApp;
import il.ac.sce.project_aleksandrkisliak.MainState;
import il.ac.sce.project_aleksandrkisliak.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField regFirstnameInput;

    @FXML
    private TextField regLastnameInput;

    @FXML
    private PasswordField regPasswordInput;

    @FXML
    private TextField regPhonenumberInput;

    @FXML
    private Label errorText;

    @FXML
    private Button regSignInButton;

    @FXML
    private Button regSubmitButton;

    @FXML
    private TextField regUsernameInput;

    @FXML
    void initialize() {
        MainState state = MainApp.getAppState();

        TextFormatter<String> textFormatter = new TextFormatter<>(Assets.filter);
        regPhonenumberInput.setTextFormatter(textFormatter);


        regSubmitButton.setOnAction(event -> {
            errorText.setVisible(false);
            
            String firstname = regFirstnameInput.getText();
            String lastname = regLastnameInput.getText();
            String username = regUsernameInput.getText();
            String phoneNumber = regPhonenumberInput.getText();
            String password = regPasswordInput.getText();

            if (firstname.isEmpty() || lastname.isEmpty() || username.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
                errorText.setVisible(true);
                return;
            }

            User user = Assets.createUser(firstname, lastname, username, phoneNumber, password);

            if (user != null) {
                state.logInAuth(user);

                Assets.changePage(
                        getClass().getResource("/il/ac/sce/project_aleksandrkisliak/personalAccountPage.fxml"),
                        (Stage) regSubmitButton.getScene().getWindow());
            }
        });

        regSignInButton.setOnAction(event -> {
            Assets.changePage(
                    getClass().getResource("/il/ac/sce/project_aleksandrkisliak/login.fxml"),
                    (Stage) regSignInButton.getScene().getWindow());
        });


    }
}
