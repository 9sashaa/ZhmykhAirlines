package il.ac.sce.project_aleksandrkisliak.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ResourceBundle;

import il.ac.sce.project_aleksandrkisliak.Database.Const;
import il.ac.sce.project_aleksandrkisliak.Database.DatabaseHandler;
import il.ac.sce.project_aleksandrkisliak.MainApp;
import il.ac.sce.project_aleksandrkisliak.MainState;
import il.ac.sce.project_aleksandrkisliak.model.FlightWithModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker mainPageDepartDate;

    @FXML
    private Text errorText;

    @FXML
    private TextField mainPageFromWhereInput;

    @FXML
    private Button mainPageSearchButton;

    @FXML
    private Button mainPageSigninButton;

    @FXML
    private Button mainPageSignupButton;

    @FXML
    private TextField mainPageWhereToInput;

    @FXML
    void initialize() {
        errorText.setVisible(false);
        MainState state = MainApp.getAppState();
        DatabaseHandler db = new DatabaseHandler();


        mainPageDepartDate.setDayCellFactory(Assets.setBoundariesDays());
        mainPageDepartDate.setEditable(false);

        mainPageSearchButton.setOnAction(event -> {
            String fromWhere = mainPageFromWhereInput.getText().trim();
            String toWhere = mainPageWhereToInput.getText().trim();

            if (!fromWhere.isEmpty() && !toWhere.isEmpty()) {
                ResultSet flightsRow;
                if (mainPageDepartDate.getValue() != null) {
                    Date deptDate = Date.valueOf(mainPageDepartDate.getValue());
                    flightsRow = db.getFlights(fromWhere, toWhere, deptDate);
                    state.setSearchInputs(fromWhere, toWhere, deptDate);
                } else {
                    flightsRow = db.getFlightsWithoutDate(fromWhere, toWhere);
                    state.setSearchInputs(fromWhere, toWhere);
                }

                errorText.setVisible(false);
                state.resetFlights();

                int counter = 0;
                try {
                    FlightWithModel flight;
                    ObservableList<FlightWithModel> data = FXCollections.observableArrayList();

                    while (flightsRow.next()) {
                        counter++;
                        int id = flightsRow.getInt(Const.FLIGHTS_ID);
                        String departureCountry = flightsRow.getString(Const.FLIGHTS_DEPARTURECOUNTRY);
                        String destinationCountry = flightsRow.getString(Const.FLIGHTS_DESTINATIONCOUNTRY);
                        int price = flightsRow.getInt(Const.FLIGHTS_PRICE);
                        Date departureDate = flightsRow.getDate(Const.FLIGHTS_DEPARTUREDATE);
                        Time departureTime = flightsRow.getTime(Const.FLIGHTS_DEPARTURETIME);
                        Date arrivalDate = flightsRow.getDate(Const.FLIGHTS_ARRIVALDATE);
                        Time arrivalTime = flightsRow.getTime(Const.FLIGHTS_ARRIVALTIME);
                        Time duration = flightsRow.getTime(Const.FLIGHTS_FLIGHTDURATION);
                        int availableSeats = flightsRow.getInt(Const.FLIGHTS_AVAILABLESEATS);
                        int totalSeats = flightsRow.getInt(Const.FLIGHTS_TOTALSEATS);
                        int airplaneId = flightsRow.getInt(Const.FLIGHTS_AIRPLANEID);
                        String airplaneModel = flightsRow.getString(Const.AIRPLANES_MODEL);

                        flight = new FlightWithModel(id, departureCountry, destinationCountry, price, departureTime, departureDate, arrivalDate, arrivalTime, availableSeats, totalSeats, duration, airplaneId, airplaneModel);

                        data.add(flight);
                        state.getAllFlights().add(flight);
                    }

                    if (counter > 0) {
                        Assets.changePage(
                                getClass().getResource("/il/ac/sce/project_aleksandrkisliak/chooseFlightPage.fxml"),
                                (Stage) mainPageSearchButton.getScene().getWindow());
                    } else {
                        errorText.setVisible(true);
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                errorText.setVisible(true);
            }

        });


        mainPageSignupButton.setOnAction(event -> {
            Assets.changePage(
                    getClass().getResource("/il/ac/sce/project_aleksandrkisliak/registration.fxml"),
                    (Stage) mainPageSignupButton.getScene().getWindow());

        });

        mainPageSigninButton.setOnAction(event -> {
            Assets.changePage(
                    getClass().getResource("/il/ac/sce/project_aleksandrkisliak/login.fxml"),
                    (Stage) mainPageSigninButton.getScene().getWindow());
        });
    }

}
