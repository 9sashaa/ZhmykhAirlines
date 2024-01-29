package il.ac.sce.project_aleksandrkisliak.controller;

import il.ac.sce.project_aleksandrkisliak.Database.DatabaseHandler;
import il.ac.sce.project_aleksandrkisliak.model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class Assets {

    public static void changePage(URL url, Stage stage) {
        FXMLLoader loader = new FXMLLoader(url);

        try {
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Callback<DatePicker, DateCell> setBoundariesDays() {
        return new Callback<>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #7C8dB0;");
                        }
                    }
                };
            }
        };
    }

    public static Callback<DatePicker, DateCell> setBoundariesDays(LocalDate before) {
        return new Callback<>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isBefore(before)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #7C8dB0;");
                        }
                    }
                };
            }
        };
    }

    public static UnaryOperator<TextFormatter.Change> filter = change -> {
        String newText = change.getControlNewText();
        if (Pattern.matches("[0-9]*", newText)) {
            return change;
        }
        return null;
    };

    public static User createUser(String firstname, String lastname, String username, String phoneNumber, String password) {
        DatabaseHandler databaseHandler = new DatabaseHandler();

        User user = new User(firstname, lastname, username, phoneNumber, password);

        int res = databaseHandler.signUpUser(user);

        if (res != -1) {
            user.setUserid(res);
            return user;
        }

        return null;
    }
}
