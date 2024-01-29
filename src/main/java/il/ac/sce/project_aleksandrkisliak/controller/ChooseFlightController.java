package il.ac.sce.project_aleksandrkisliak.controller;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import il.ac.sce.project_aleksandrkisliak.Database.Const;
import il.ac.sce.project_aleksandrkisliak.MainApp;
import il.ac.sce.project_aleksandrkisliak.MainState;
import il.ac.sce.project_aleksandrkisliak.Database.DatabaseHandler;

import il.ac.sce.project_aleksandrkisliak.model.Flight;
import il.ac.sce.project_aleksandrkisliak.model.FlightWithModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChooseFlightController {


    @FXML
    private Circle chooseFlightAuthAvatar;

    @FXML
    private Button chooseFlightAuthFirstname;

    @FXML
    private DatePicker chooseFlightDepartDate;

    @FXML
    private TextField chooseFlightFromWhereInput;

    @FXML
    private Button chooseFlightLogOutButton;

    @FXML
    private Button chooseFlightSearchButton;

    @FXML
    private Button chooseFlightSigninButton;

    @FXML
    private Button chooseFlightSignupButton;

    @FXML
    private TextField chooseFlightWhereToInput;

    @FXML
    private Text errorText;

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
    private TableColumn<FlightWithModel, Integer> flightDurationColumn;

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
    private Button selectFlightButton;


    @FXML
    void initialize() {
        initializeColumns();

        MainState state = MainApp.getAppState();
        DatabaseHandler db = new DatabaseHandler();


        chooseFlightDepartDate.setDayCellFactory(Assets.setBoundariesDays());
        chooseFlightDepartDate.setEditable(false);
        
        if (!state.getSearchInputs().getFrom().isEmpty()) {
            chooseFlightFromWhereInput.setText(state.getSearchInputs().getFrom());
        }
        if (!state.getSearchInputs().getTo().isEmpty()) {
            chooseFlightWhereToInput.setText(state.getSearchInputs().getTo());
        }
        if (state.getSearchInputs().getDate() != null) {
            chooseFlightDepartDate.setValue(state.getSearchInputs().getDate().toLocalDate());
        }

        if (state.getAllFlights().size() != 0) {
            ObservableList<FlightWithModel> data = FXCollections.observableArrayList();
            data.setAll(state.getAllFlights());
            flightList.setItems(data);
        }

        if (state.isAuth()) {
            chooseFlightLogOutButton.setVisible(true);
            chooseFlightAuthAvatar.setVisible(true);
            chooseFlightAuthFirstname.setVisible(true);
            chooseFlightAuthFirstname.setText(state.getUser().getFirstname());
        } else {
            chooseFlightSigninButton.setVisible(true);
            chooseFlightSignupButton.setVisible(true);
        }

        chooseFlightSignupButton.setOnAction(event -> {
            Assets.changePage(
                    getClass().getResource("/il/ac/sce/project_aleksandrkisliak/registration.fxml"),
                    (Stage) chooseFlightSignupButton.getScene().getWindow());

        });

        chooseFlightSigninButton.setOnAction(event -> {
            Assets.changePage(
                    getClass().getResource("/il/ac/sce/project_aleksandrkisliak/login.fxml"),
                    (Stage) chooseFlightSigninButton.getScene().getWindow());
        });

        selectFlightButton.setOnAction(event -> {
            errorText.setVisible(false);
            var selectionModel = flightList.getSelectionModel();
            FlightWithModel selectedItem = selectionModel.getSelectedItem();

            if (selectedItem != null) {
                if (selectedItem.getAvailableSeats() != 0) {
                    state.setSelectedFlightToBuy(selectedItem);
                    Assets.changePage(
                            getClass().getResource("/il/ac/sce/project_aleksandrkisliak/buyTicketPage.fxml"),
                            (Stage) selectFlightButton.getScene().getWindow());
                } else {
                    errorText.setVisible(true);
                    errorText.setText("There are no seats on this flight!");
                }
            }
        });


        chooseFlightLogOutButton.setOnAction(event -> {
            state.logOutAuth();

            Assets.changePage(
                    getClass().getResource("/il/ac/sce/project_aleksandrkisliak/mainPage.fxml"),
                    (Stage) chooseFlightLogOutButton.getScene().getWindow());
        });

        chooseFlightAuthFirstname.setOnAction(event -> {
            Assets.changePage(
                    getClass().getResource("/il/ac/sce/project_aleksandrkisliak/personalAccountPage.fxml"),
                    (Stage) chooseFlightAuthFirstname.getScene().getWindow());
        });


        chooseFlightSearchButton.setOnAction(event -> {

            String fromWhere = chooseFlightFromWhereInput.getText().trim();
            String toWhere = chooseFlightWhereToInput.getText().trim();

            if (!fromWhere.isEmpty() && !toWhere.isEmpty()) {
                ResultSet flightsRow;
                if (chooseFlightDepartDate.getValue() != null) {
                    Date deptDate = Date.valueOf(chooseFlightDepartDate.getValue());
                    flightsRow = db.getFlights(fromWhere, toWhere, deptDate);
                    state.setSearchInputs(fromWhere, toWhere, deptDate);
                } else {
                    flightsRow = db.getFlightsWithoutDate(fromWhere, toWhere);
                    state.setSearchInputs(fromWhere, toWhere);
                }

                errorText.setVisible(false);
                errorText.setText("No flights found!");
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
                        flightList.setItems(data);
                    } else {
                        flightList.setItems(FXCollections.observableArrayList());
                        errorText.setVisible(true);
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                errorText.setVisible(true);
            }
        });
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
