package il.ac.sce.project_aleksandrkisliak.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;
import java.util.ResourceBundle;

import il.ac.sce.project_aleksandrkisliak.Database.Const;
import il.ac.sce.project_aleksandrkisliak.Database.DatabaseHandler;
import il.ac.sce.project_aleksandrkisliak.MainApp;
import il.ac.sce.project_aleksandrkisliak.MainState;
import il.ac.sce.project_aleksandrkisliak.model.FlightWithModel;
import il.ac.sce.project_aleksandrkisliak.model.Ticket;
import il.ac.sce.project_aleksandrkisliak.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class BuyTicketPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Circle authAvatar;

    @FXML
    private Button authFirstname;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField buyTicketFirstnameInput;

    @FXML
    private TextField buyTicketLastnameInput;

    @FXML
    private TextField buyTicketPhoneNumber;

    @FXML
    private Label departureTime;

    @FXML
    private Label seatNumberInfoInput;

    @FXML
    private Label seatNumberInfoText;

    @FXML
    private Label duration;

    @FXML
    private Label flightid;

    @FXML
    private Label errorText;

    @FXML
    private Label fromToCountries;

    @FXML
    private Label price;

    @FXML
    private Button signInButton;

    @FXML
    private Button selectSeatsButton;

    @FXML
    private Button payButton;

    @FXML
    private Button paymentCancelButton;

    @FXML
    private PasswordField paymentCardCCV;

    @FXML
    private TextField paymentCardMonth;

    @FXML
    private TextField paymentCardName;

    @FXML
    private TextField paymentCardNumber;

    @FXML
    private TextField paymentCardYear;

    @FXML
    private AnchorPane paymentPanel;

    @FXML
    private Button paymentSubmitButton;

    @FXML
    private Button signUpButton;

    @FXML
    private Button logOutButton;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField usernameInput;

    @FXML
    private Label totalPrice;

    @FXML
    private CheckBox withLuggage;

    @FXML
    private Label typeOfAirplane;

    @FXML
    private AnchorPane selectSeatsPanel;

    private FlightWithModel selectedFlight;
    private User user;
    private MainState state;
    private int selectedSeat = -1;
    DatabaseHandler db;

    @FXML
    void initialize() {
        db = new DatabaseHandler();
        state = MainApp.getAppState();
        selectedFlight = state.getSelectedFlightToBuy();
        user = state.getUser();

        TextFormatter<String> textFormatter = new TextFormatter<>(Assets.filter);
        TextFormatter<String> textFormatter1 = new TextFormatter<>(Assets.filter);
        TextFormatter<String> textFormatter2 = new TextFormatter<>(Assets.filter);
        TextFormatter<String> textFormatter3 = new TextFormatter<>(Assets.filter);
        TextFormatter<String> textFormatter4 = new TextFormatter<>(Assets.filter);
        buyTicketPhoneNumber.setTextFormatter(textFormatter);
        paymentCardCCV.setTextFormatter(textFormatter1);
        paymentCardMonth.setTextFormatter(textFormatter2);
        paymentCardYear.setTextFormatter(textFormatter3);
        paymentCardNumber.setTextFormatter(textFormatter4);

        initializePurchasedTickets();
        initializeFlightInfo();
        initializeSeats();

        if (state.isAuth()) {
            String firstname = user.getFirstname();
            String lastname = user.getLastname();
            String phoneNumber = user.getPhoneNumber();

            initializeAuth();
            buyTicketFirstnameInput.setText(firstname);
            buyTicketFirstnameInput.setDisable(true);
            buyTicketLastnameInput.setText(lastname);
            buyTicketLastnameInput.setDisable(true);
            buyTicketPhoneNumber.setText(phoneNumber);
            buyTicketPhoneNumber.setDisable(true);
        } else {
            signInButton.setVisible(true);
            signUpButton.setVisible(true);
            usernameInput.setVisible(true);
            passwordInput.setVisible(true);
        }

        selectSeatsButton.setOnAction(event -> {
            errorText.setVisible(false);

            if (state.isAuth()) {
                withLuggage.setDisable(true);
                scrollPane.setVisible(true);
                selectSeatsButton.setVisible(false);
                payButton.setVisible(true);
                return;
            }

            if (!buyTicketFirstnameInput.getText().trim().isEmpty()
                    && !buyTicketLastnameInput.getText().trim().isEmpty()
                    && !buyTicketPhoneNumber.getText().trim().isEmpty()
                    && !usernameInput.getText().trim().isEmpty()
                    && !passwordInput.getText().trim().isEmpty()) {
                buyTicketFirstnameInput.setDisable(true);
                buyTicketLastnameInput.setDisable(true);
                buyTicketPhoneNumber.setDisable(true);
                passwordInput.setDisable(true);
                usernameInput.setDisable(true);
                withLuggage.setDisable(true);
                scrollPane.setVisible(true);
                selectSeatsButton.setVisible(false);
                payButton.setVisible(true);
            } else {
                errorText.setVisible(true);
                errorText.setText("Please enter all details");
            }
        });

        payButton.setOnAction(event -> {
            Ticket ticket;
            if (selectedSeat != -1) {
                if (state.isAuth()) {
                    ticket = new Ticket(selectedFlight.getFlightid(),
                            user.getUserid(), selectedSeat,
                            Date.valueOf(LocalDate.now()),
                            withLuggage.isSelected());
                } else {
                    ticket = new Ticket(selectedFlight.getFlightid(),
                            selectedSeat,
                            Date.valueOf(LocalDate.now()),
                            withLuggage.isSelected());
                }

                state.getSelectedSeats().add(ticket);
                state.setTicketForPay(ticket);
                seatNumberInfoInput.setText(ticket.getSeatsNumber() + "");
                seatNumberInfoInput.setVisible(true);
                seatNumberInfoText.setVisible(true);
                scrollPane.setVisible(false);
                payButton.setVisible(false);
                paymentPanel.setVisible(true);
            }
        });

        paymentCancelButton.setOnAction(event -> {
            payButton.setVisible(true);
            scrollPane.setVisible(true);
            seatNumberInfoInput.setVisible(false);
            seatNumberInfoText.setVisible(false);
            paymentPanel.setVisible(false);
            state.getSelectedSeats().remove(state.getTicketForPay());
            state.setTicketForPay(null);
        });

        paymentSubmitButton.setOnAction(event -> {
            usernameInput.setDisable(true);
            errorText.setVisible(false);

            if (paymentCardNumber.getText().isEmpty() || paymentCardYear.getText().isEmpty() || paymentCardCCV.getText().isEmpty() || paymentCardMonth.getText().isEmpty()) {
                errorText.setVisible(true);
                errorText.setText("Wrong card details!");
                return;
            }

            if (paymentCardNumber.getText().length() != 16) {
                errorText.setVisible(true);
                errorText.setText("Wrong card number!");
                return;
            }


            if (Integer.parseInt(paymentCardYear.getText() + "") >= LocalDate.now().getYear()) {
                if (Integer.parseInt(paymentCardYear.getText() + "") == LocalDate.now().getYear()) {
                    int month = Integer.parseInt(paymentCardMonth.getText() + "");

                    if (month >= 0 && month <= 12) {
                        if (month <= LocalDate.now().getMonthValue()) {

                            errorText.setVisible(true);
                            errorText.setText("Wrong card month!");
                            return;
                        }
                    } else {
                        errorText.setVisible(true);
                        errorText.setText("Wrong card month!");
                        return;
                    }
                }
            } else {
                errorText.setVisible(true);
                errorText.setText("Wrong card year!");
                return;
            }

            if (paymentCardCCV.getText().length() != 3) {
                errorText.setVisible(true);
                errorText.setText("Wrong card CCV!");
                return;
            }

            if (!state.isAuth()) {
                String firstname = buyTicketFirstnameInput.getText().trim();
                String lastname = buyTicketLastnameInput.getText().trim();
                String username = usernameInput.getText().trim();
                String phoneNumber = buyTicketPhoneNumber.getText().trim();
                String password = passwordInput.getText().trim();

                User user = Assets.createUser(firstname, lastname, username, phoneNumber, password);

                if (user != null) {
                    state.logInAuth(user);
                    state.getTicketForPay().setUserid(user.getUserid());
                    this.user = user;
                    initializeAuth();

                } else {
                    errorText.setVisible(true);
                    errorText.setText("Username is already exist!");
                    usernameInput.setDisable(false);
                    return;
                }
            }

            int res = db.createTicket(state.getTicketForPay());

            if (res != -1) {

                int resAvailableSeats = db.updateAvailableSeats(selectedFlight.getFlightid(), selectedFlight.getAvailableSeats() - 1);

                if (resAvailableSeats != -1) {
                    state.getSelectedSeats().remove(state.getTicketForPay());
                    state.setTicketForPay(null);
                    Assets.changePage(
                            getClass().getResource("/il/ac/sce/project_aleksandrkisliak/personalAccountPage.fxml"),
                            (Stage) paymentSubmitButton.getScene().getWindow());
                    return;
                }
            }
            errorText.setVisible(true);
            errorText.setText("Something went wrong! Try later");

        });

        logOutButton.setOnAction(event -> {
            state.logOutAuth();

            if (state.getTicketForPay() != null) {
                state.getSelectedSeats().remove(state.getTicketForPay());
                state.setTicketForPay(null);
            }

            Assets.changePage(
                    getClass().getResource("/il/ac/sce/project_aleksandrkisliak/mainPage.fxml"),
                    (Stage) logOutButton.getScene().getWindow());
        });

        authFirstname.setOnAction(event -> {
            if (state.getTicketForPay() != null) {
                state.getSelectedSeats().remove(state.getTicketForPay());
                state.setTicketForPay(null);
            }

            Assets.changePage(
                    getClass().getResource("/il/ac/sce/project_aleksandrkisliak/personalAccountPage.fxml"),
                    (Stage) authFirstname.getScene().getWindow());
        });

        signUpButton.setOnAction(event -> {
            if (state.getTicketForPay() != null) {
                state.getSelectedSeats().remove(state.getTicketForPay());
                state.setTicketForPay(null);
            }

            Assets.changePage(
                    getClass().getResource("/il/ac/sce/project_aleksandrkisliak/registration.fxml"),
                    (Stage) signUpButton.getScene().getWindow());

        });

        signInButton.setOnAction(event -> {
            if (state.getTicketForPay() != null) {
                state.getSelectedSeats().remove(state.getTicketForPay());
                state.setTicketForPay(null);
            }

            Assets.changePage(
                    getClass().getResource("/il/ac/sce/project_aleksandrkisliak/login.fxml"),
                    (Stage) signInButton.getScene().getWindow());
        });
    }

    private void initializeFlightInfo() {
        fromToCountries.setText(selectedFlight.getDepartureCountry() + " -> " + selectedFlight.getDestinationCountry());
        duration.setText(selectedFlight.getFlightDuration() + "");
        flightid.setText(selectedFlight.getFlightid() + "");
        departureTime.setText(selectedFlight.getDepartureTime().toString());
        typeOfAirplane.setText(selectedFlight.getAirplaneModel());
        price.setText("$" + (selectedFlight.getPrice() - 121));
        totalPrice.setText("$" + selectedFlight.getPrice());
    }

    private void initializePurchasedTickets() {
        ResultSet ticketsRow = db.getTicketsOfFlight(selectedFlight.getFlightid());
        Ticket ticket;
        try {
            while (ticketsRow.next()) {
                int ticketid = ticketsRow.getInt(Const.TICKETS_ID);
                int flightid = ticketsRow.getInt(Const.TICKETS_FLIGHTID);
                int userid = ticketsRow.getInt(Const.TICKETS_USERID);
                Date purchaseDate = ticketsRow.getDate(Const.TICKETS_PURCHASEDATE);
                int seat = ticketsRow.getInt(Const.TICKETS_SEATSNUMBER);
                boolean withLuggage = ticketsRow.getBoolean(Const.TICKETS_WITHLUGGAGE);
                ticket = new Ticket(ticketid, flightid, userid, seat, purchaseDate, withLuggage);

                state.getSelectedSeats().add(ticket);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeSeats() {
        RadioButton radioButton;
        ToggleGroup group = new ToggleGroup();
        boolean isMore = false;

        for (int i = 0, y = 0, x = 0, multi = 1; i < selectedFlight.getAvailableSeats() + 1; i++) {
            if (i % 21 == 0) {
                multi = i / 21;
                isMore = true;
                y += 30;
            }

            if (isMore) {
                x = i - (21 * multi);
            } else {
                x = i;
            }
            x *= 45;

            radioButton = new RadioButton(i + "");
            radioButton.setLayoutX(x + 80);
            radioButton.setLayoutY(y);
            radioButton.setToggleGroup(group);
            radioButton.setDisable(state.isInSelectedSeats(selectedFlight.getFlightid(), i));
            radioButton.setTextFill(radioButton.isDisable() ? Paint.valueOf("red") : Paint.valueOf("green"));

            int finalI = i;

            radioButton.setOnAction(event -> {
                selectedSeat = finalI;
            });
            selectSeatsPanel.getChildren().add(radioButton);
        }
    }

    private void initializeAuth() {
        logOutButton.setVisible(true);
        authAvatar.setVisible(true);
        authFirstname.setVisible(true);
        authFirstname.setText(user.getFirstname());
        signInButton.setVisible(false);
        signUpButton.setVisible(false);
    }
}
