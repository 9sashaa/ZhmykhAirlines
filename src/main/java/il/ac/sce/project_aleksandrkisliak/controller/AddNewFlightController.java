package il.ac.sce.project_aleksandrkisliak.controller;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import il.ac.sce.project_aleksandrkisliak.Database.Const;
import il.ac.sce.project_aleksandrkisliak.Database.DatabaseHandler;
import il.ac.sce.project_aleksandrkisliak.MainApp;
import il.ac.sce.project_aleksandrkisliak.MainState;
import il.ac.sce.project_aleksandrkisliak.model.Airplane;
import il.ac.sce.project_aleksandrkisliak.model.Flight;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddNewFlightController {

    @FXML
    private MenuButton airplaneMenu;

    @FXML
    private DatePicker arrDate;

    @FXML
    private TextField arrTime;

    @FXML
    private Button authFirstname;

    @FXML
    private TextField depCountry;

    @FXML
    private DatePicker depDate;

    @FXML
    private TextField depTime;

    @FXML
    private TextField destCountry;

    @FXML
    private TextField duration;

    @FXML
    private Button logOutButton;

    @FXML
    private TextField price;

    @FXML
    private Button submitButton;

    @FXML
    private TextField totalSeats;

    @FXML
    private Text maxCapacityText;

    @FXML
    private Text errorText;

    private Airplane airplaneSelected;

    private DatabaseHandler databaseHandler = new DatabaseHandler();

    @FXML
    void initialize() {
        MainState state = MainApp.getAppState();
        initializeAirplanes();

        authFirstname.setText(state.getUser().getFirstname());

        TextFormatter<String> textFormatter = new TextFormatter<>(Assets.filter);
        totalSeats.setTextFormatter(textFormatter);

        depDate.setDayCellFactory(Assets.setBoundariesDays());
        depDate.setEditable(false);
        arrDate.setEditable(false);
        arrDate.setDisable(true);

        depDate.setOnAction(event -> {
            if (arrDate.isDisable()) {
                arrDate.setDisable(false);
                arrDate.setDayCellFactory(Assets.setBoundariesDays(depDate.getValue()));
                return;
            }

            if (arrDate.getValue().isBefore(depDate.getValue())) {
                arrDate.setValue(depDate.getValue());
            }

            arrDate.setDayCellFactory(Assets.setBoundariesDays(depDate.getValue()));
        });

        logOutButton.setOnAction(event -> {
            state.logOutAuth();
            Assets.changePage(
                    getClass().getResource("/il/ac/sce/project_aleksandrkisliak/mainPage.fxml"),
                    (Stage) logOutButton.getScene().getWindow());
        });

        authFirstname.setOnAction(event -> {
            Assets.changePage(
                    getClass().getResource("/il/ac/sce/project_aleksandrkisliak/personalAccountPage.fxml"),
                    (Stage) authFirstname.getScene().getWindow());
        });

        submitButton.setOnAction(event -> {
            errorText.setVisible(false);
            if (Integer.parseInt(totalSeats.getText()) > airplaneSelected.getCapacity()) {
                errorText.setText("Error! The number of seats cannot exceed the airplane capacity - " + airplaneSelected.getCapacity());
                errorText.setVisible(true);
                return;
            }

            try {
                Flight flight = createFlight();

                if (flight != null) {
                    Assets.changePage(
                            getClass().getResource("/il/ac/sce/project_aleksandrkisliak/personalAccountPage.fxml"),
                            (Stage) submitButton.getScene().getWindow());
                }
            } catch (NullPointerException error) {
                errorText.setText("Error! All fields are not filled in");
                errorText.setVisible(true);
            }


        });


    }


    private Flight createFlight() {
        String departureCountry = depCountry.getText().trim();
        String destinationCountry = destCountry.getText().trim();
        Date departureDate = Date.valueOf(depDate.getValue());
        Date arrivalDate = Date.valueOf(arrDate.getValue());
        Time departureTime = Time.valueOf(depTime.getText() + ":00");
        Time arrivalTime = Time.valueOf(arrTime.getText() + ":00");
        Time durationFlight = Time.valueOf(duration.getText() + ":00");
        int seats = Integer.parseInt(totalSeats.getText());
        int idairplane = airplaneSelected.getIdairplane();
        int priceFlight = Integer.parseInt(price.getText());

        Flight flight = new Flight(departureCountry, destinationCountry, priceFlight, departureTime, departureDate, arrivalDate, arrivalTime, seats, seats, durationFlight, idairplane);

        int result = databaseHandler.createFlight(flight);
        if (result != -1) {
            flight.setFlightid(result);
            return flight;
        }

        return null;
    }

    private void initializeAirplanes() {
        ResultSet airplaneRow = databaseHandler.getAirplanes();
        int counter = 0;
        try {
            while (airplaneRow.next()) {
                counter++;
                int id = airplaneRow.getInt(Const.AIRPLANES_ID);
                String model = airplaneRow.getString(Const.AIRPLANES_MODEL);
                int capacity = airplaneRow.getInt(Const.AIRPLANES_CAPACITY);

                Airplane airplane = new Airplane(id, model, capacity);

                MenuItem item = new MenuItem(model);

                item.setOnAction(event -> {
                    airplaneSelected = airplane;
                    airplaneMenu.setText(model);
                    totalSeats.setDisable(false);
                    maxCapacityText.setVisible(true);
                    maxCapacityText.setText("Max airplane capacity: " + capacity);
                    totalSeats.setText(capacity + "");
                });

                airplaneMenu.getItems().add(item);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
