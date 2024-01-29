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
import il.ac.sce.project_aleksandrkisliak.model.Flight;
import il.ac.sce.project_aleksandrkisliak.model.FlightWithModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ShowAllFlightController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button authFirstname;

    @FXML
    private Button authLogOutButton;

    @FXML
    private TableView<FlightWithModel> flightList;

    @FXML
    private TableColumn<FlightWithModel, Integer> flightidColumn;

    @FXML
    private TableColumn<FlightWithModel, Date> departureDateColumn;

    @FXML
    private TableColumn<FlightWithModel, Date> arrivalDateColumn;

    @FXML
    private TableColumn<FlightWithModel, Integer> availableSeatsColumn;

    @FXML
    private TableColumn<FlightWithModel, Time> flightDurationColumn;

    @FXML
    private TableColumn<FlightWithModel, String> fromCountryColumn;

    @FXML
    private TableColumn<FlightWithModel, Integer> priceColumn;

    @FXML
    private TableColumn<FlightWithModel, String> toCountryColumn;

    @FXML
    private TableColumn<FlightWithModel, String> modelAirplaneColumn;

    @FXML
    private TableColumn<FlightWithModel, Time> arrivalTimeColumn;

    @FXML
    private TableColumn<FlightWithModel, Time> departureTimeColumn;

    @FXML
    void initialize() {
        initializeColumns();

        MainState state = MainApp.getAppState();
        DatabaseHandler databaseHandler = new DatabaseHandler();

        authFirstname.setText(state.getUser().getFirstname());

        authLogOutButton.setOnAction(event -> {
            state.logOutAuth();

            Assets.changePage(
                    getClass().getResource("/il/ac/sce/project_aleksandrkisliak/mainPage.fxml"),
                    (Stage) authLogOutButton.getScene().getWindow());
        });

        authFirstname.setOnAction(event -> {
            Assets.changePage(
                    getClass().getResource("/il/ac/sce/project_aleksandrkisliak/personalAccountPage.fxml"),
                    (Stage) authFirstname.getScene().getWindow());
        });

        state.resetFlights();
        ResultSet flightsRow = databaseHandler.getFlights();

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

            flightList.setItems(data);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeColumns() {
        flightidColumn.setCellValueFactory(new PropertyValueFactory<>("flightid"));
        fromCountryColumn.setCellValueFactory(new PropertyValueFactory<>("departureCountry"));
        toCountryColumn.setCellValueFactory(new PropertyValueFactory<>("destinationCountry"));
        departureDateColumn.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        departureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        arrivalDateColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        flightDurationColumn.setCellValueFactory(new PropertyValueFactory<>("flightDuration"));
        availableSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
        modelAirplaneColumn.setCellValueFactory(new PropertyValueFactory<>("airplaneModel"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
