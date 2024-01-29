package il.ac.sce.project_aleksandrkisliak.controller;

import il.ac.sce.project_aleksandrkisliak.Database.Const;
import il.ac.sce.project_aleksandrkisliak.Database.DatabaseHandler;
import il.ac.sce.project_aleksandrkisliak.MainApp;
import il.ac.sce.project_aleksandrkisliak.MainState;
import il.ac.sce.project_aleksandrkisliak.model.FlightWithModel;
import il.ac.sce.project_aleksandrkisliak.model.FlightWithTicket;
import il.ac.sce.project_aleksandrkisliak.model.Ticket;
import il.ac.sce.project_aleksandrkisliak.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class MyTicketPageController {

    @FXML
    private TableColumn<FlightWithTicket, Date> arrivalDateColumn;

    @FXML
    private TableColumn<FlightWithTicket, Time> arrivalTimeColumn;

    @FXML
    private Circle authAvatar;

    @FXML
    private Button authFirstname;

    @FXML
    private TableColumn<FlightWithTicket, Date> departureDateColumn;

    @FXML
    private TableColumn<FlightWithTicket, Time> departureTimeColumn;

    @FXML
    private TableColumn<FlightWithTicket, Time> flightDurationColumn;

    @FXML
    private TableView<FlightWithTicket> flightList;

    @FXML
    private TableColumn<FlightWithTicket, Integer> ticketidColumn;

    @FXML
    private TableColumn<FlightWithTicket, String> fromCountryColumn;

    @FXML
    private Button logOutButton;

    @FXML
    private TableColumn<FlightWithTicket, Boolean> luggageColumn;

    @FXML
    private TableColumn<FlightWithTicket, Integer> priceColumn;

    @FXML
    private TableColumn<FlightWithTicket, Date> purchaseDateColumn;

    @FXML
    private TableColumn<FlightWithTicket, Integer> seatNumberColumn;

    @FXML
    private TableColumn<FlightWithTicket, String> toCountryColumn;

    @FXML
    void initialize() {
        initializeColumns();

        MainState state = MainApp.getAppState();
        DatabaseHandler databaseHandler = new DatabaseHandler();

        authFirstname.setText(state.getUser().getFirstname());

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

        state.resetFlights();
        ResultSet flightsRow = databaseHandler.getUserTicket(state.getUser().getUserid());

        try {
            FlightWithTicket flight;
            ObservableList<FlightWithTicket> data = FXCollections.observableArrayList();

            while (flightsRow.next()) {
                int flightid = flightsRow.getInt(Const.FLIGHTS_ID);
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

                int ticketid = flightsRow.getInt(Const.TICKETS_ID);
                int flightidTikcet = flightsRow.getInt(Const.TICKETS_FLIGHTID);
                int userid = flightsRow.getInt(Const.TICKETS_USERID);
                int seatNumber = flightsRow.getInt(Const.TICKETS_SEATSNUMBER);
                Date purchaseDate = flightsRow.getDate(Const.TICKETS_PURCHASEDATE);
                boolean withLuggage = flightsRow.getBoolean(Const.TICKETS_WITHLUGGAGE);
                Ticket ticket = new Ticket(ticketid, flightidTikcet, userid, seatNumber, purchaseDate, withLuggage);

                flight = new FlightWithTicket(flightid, departureCountry, destinationCountry, price, departureTime, departureDate, arrivalDate, arrivalTime, availableSeats, totalSeats, duration, airplaneId, ticket
                );

                data.add(flight);
            }

            flightList.setItems(data);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void initializeColumns() {
        ticketidColumn.setCellValueFactory(new PropertyValueFactory<>("ticketid"));
        fromCountryColumn.setCellValueFactory(new PropertyValueFactory<>("departureCountry"));
        toCountryColumn.setCellValueFactory(new PropertyValueFactory<>("destinationCountry"));
        departureDateColumn.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        departureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        arrivalDateColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        flightDurationColumn.setCellValueFactory(new PropertyValueFactory<>("flightDuration"));
        purchaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
        seatNumberColumn.setCellValueFactory(new PropertyValueFactory<>("seatsNumber"));
        luggageColumn.setCellValueFactory(new PropertyValueFactory<>("withLuggage"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
