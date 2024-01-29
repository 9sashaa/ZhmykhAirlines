package il.ac.sce.project_aleksandrkisliak.controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import il.ac.sce.project_aleksandrkisliak.Database.Const;
import il.ac.sce.project_aleksandrkisliak.Database.DatabaseHandler;
import il.ac.sce.project_aleksandrkisliak.MainApp;
import il.ac.sce.project_aleksandrkisliak.MainState;
import il.ac.sce.project_aleksandrkisliak.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text loginErrorText;

    @FXML
    private Label logo;

    @FXML
    private PasswordField loginPasswordInput;

    @FXML
    private Button loginSignInButton;

    @FXML
    private Button loginSignupButton;

    @FXML
    private TextField loginUsernameInput;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() {

        databaseHandler = new DatabaseHandler();

        loginSignInButton.setOnAction((event) -> {
            loginErrorText.setVisible(false);
            String usernameInput = loginUsernameInput.getText().trim();
            String passwordInput = loginPasswordInput.getText().trim();

            if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
                loginErrorText.setVisible(true);
                loginErrorText.setText("Please Enter all details");
                return;
            }


            User user = new User();
            user.setUsername(usernameInput);
            user.setPassword(passwordInput);
            ResultSet userRow = databaseHandler.getUser(user);

            int counter = 0;
            try {

                while (userRow.next()) {
                    counter++;
                    String firstname = userRow.getString(Const.USERS_FIRSTNAME);
                    String lastname = userRow.getString(Const.USERS_LASTNAME);
                    String username = userRow.getString(Const.USERS_USERNAME);
                    String phoneNumber = userRow.getString(Const.USERS_PHONENUMBER);
                    int countOfTicketsPurchased = userRow.getInt(Const.USERS_COUNTOFTICKETSPURCHASED);
                    int id = userRow.getInt(Const.USERS_ID);
                    boolean isAdmin = userRow.getBoolean(Const.USERS_ISADMIN);

                    user = new User(id, firstname, lastname, username, phoneNumber, passwordInput, isAdmin, countOfTicketsPurchased);


                    MainState state = MainApp.getAppState();
                    state.setAuth(true);
                    state.setUser(user);
                    loginUsernameInput.setStyle("-fx-background-color: #20c920; -fx-text-fill: white; -fx-border-color: white;");
                    loginPasswordInput.setStyle("-fx-background-color: #20c920; -fx-text-fill: white; -fx-border-color: white;");

                    Assets.changePage(
                            getClass().getResource("/il/ac/sce/project_aleksandrkisliak/personalAccountPage.fxml"),
                            (Stage) loginSignInButton.getScene().getWindow());
                }

                if (counter != 1) {
                    loginErrorText.setVisible(true);
                    loginErrorText.setText("Something went wrong! Incorrect username or password!");
                    loginUsernameInput.setStyle("-fx-border-color: red;");
                    loginPasswordInput.setStyle(" -fx-border-color: red;");
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }


        });

        logo.setOnMouseClicked(mouseEvent -> {
            Assets.changePage(
                    getClass().getResource("/il/ac/sce/project_aleksandrkisliak/mainPage.fxml"),
                    (Stage) logo.getScene().getWindow());
        });

        loginSignupButton.setOnAction(event -> {
            Assets.changePage(
                    getClass().getResource("/il/ac/sce/project_aleksandrkisliak/registration.fxml"),
                    (Stage) loginSignupButton.getScene().getWindow());
        });
    }


}