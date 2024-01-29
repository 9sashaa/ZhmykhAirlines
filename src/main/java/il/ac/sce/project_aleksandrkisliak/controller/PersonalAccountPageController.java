package il.ac.sce.project_aleksandrkisliak.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import il.ac.sce.project_aleksandrkisliak.Database.Const;
import il.ac.sce.project_aleksandrkisliak.Database.DatabaseHandler;
import il.ac.sce.project_aleksandrkisliak.MainApp;
import il.ac.sce.project_aleksandrkisliak.MainState;
import il.ac.sce.project_aleksandrkisliak.model.Airplane;
import il.ac.sce.project_aleksandrkisliak.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

public class PersonalAccountPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button LogOutButton;

    @FXML
    private Text authFirstname;

    @FXML
    private Text countOfTicketsPurchased;

    @FXML
    private Text isAdminTitle;

    @FXML
    private Button myTicketsButton;

    @FXML
    private Text adminPanelErrorText;

    @FXML
    private Text adminPanelSuccessText;

    @FXML
    private Text phoneNumber;

    @FXML
    private AnchorPane changePriceInfoPanel;

    @FXML
    private Button searchForTicketsButton;

    @FXML
    private Text userName;

    @FXML
    private Button adminPanelAddFlightButton;

    @FXML
    private Line adminPanelLine;

    @FXML
    private Button adminPanelDeleteFlightButton;

    @FXML
    private Button adminPanelShowAllFlights;

    @FXML
    private Text deleteFlightInfoDeprDate;

    @FXML
    private Text deleteFlightInfoFrom;

    @FXML
    private Text deleteFlightInfoId;

    @FXML
    private Text deleteFlightInfoTo;

    @FXML
    private TextField deleteFlightInput;

    @FXML
    private AnchorPane deleteFlightPanel;

    @FXML
    private Label adminPanelTitle;

    @FXML
    private Button adminPanelChangeSeatsButton;

    @FXML
    private TextField changeSeatsFlightId;

    @FXML
    private Text adminPanelTotalSeats;

    @FXML
    private Text adminPanelAvailableSeats;

    @FXML
    private AnchorPane adminPanelFlightInfoPanel;

    @FXML
    private Text adminPanelFlightId;

    @FXML
    private Button adminPanelAddAirplaneButton;

    @FXML
    private AnchorPane deleteFlightInfoPanel;

    @FXML
    private AnchorPane addAirplanePanel;

    @FXML
    private TextField addAirplanePanelCapacity;

    @FXML
    private TextField addAirplanePanelModel;

    @FXML
    private Button adminPanelChangeSeatsCancelButton;

    @FXML
    private Button adminPanelChangePriceButton;

    @FXML
    private Text adminPanelAirplaneCapacity;

    @FXML
    private Text changePriceFlightID;

    @FXML
    private TextField changePriceInput;

    @FXML
    private AnchorPane changePricePanel;

    @FXML
    private Text changePricePrice;

    private boolean isShowDeleteFlightPanel = false;
    private boolean isSearchedDeleteFlight = false;
    private boolean isShowChangePricePanel = false;
    private boolean isSearchedChangePrice = false;
    private boolean isChangeSeats = false;
    private boolean isSearchedChangeSeats = false;
    private boolean isShowAirplanePanel = false;
    private int flightId;

    @FXML
    void initialize() {
        MainState state = MainApp.getAppState();
        DatabaseHandler db = new DatabaseHandler();
        AtomicInteger availableSeats = new AtomicInteger();
        AtomicInteger totalSeats = new AtomicInteger();
        AtomicInteger capacity = new AtomicInteger();

        state.resetSearchInputs();
        state.resetFlights();


        if (state.isAuth()) {
            User user = state.getUser();
            authFirstname.setText(user.getFirstname() + " " + user.getLastname());
            userName.setText(user.getUsername());
            phoneNumber.setText(user.getPhoneNumber());
            countOfTicketsPurchased.setText("" + user.getCountOfTicketsPurchased());
            initializeAdmin(user.isAdmin());
        }

        adminPanelShowAllFlights.setOnAction(event -> {
            Assets.changePage(
                    getClass().getResource("/il/ac/sce/project_aleksandrkisliak/showAllFlights.fxml"),
                    (Stage) adminPanelShowAllFlights.getScene().getWindow());
        });

        myTicketsButton.setOnAction(event -> {
            Assets.changePage(
                    getClass().getResource("/il/ac/sce/project_aleksandrkisliak/myTicketPage.fxml"),
                    (Stage) myTicketsButton.getScene().getWindow());
        });

        LogOutButton.setOnAction(event -> {
            state.logOutAuth();
            resetAdminPanelChangeSeats();

            Assets.changePage(
                    getClass().getResource("/il/ac/sce/project_aleksandrkisliak/mainPage.fxml"),
                    (Stage) LogOutButton.getScene().getWindow());
        });

        searchForTicketsButton.setOnAction(event -> {
            resetAdminPanelChangeSeats();

            Assets.changePage(
                    getClass().getResource("/il/ac/sce/project_aleksandrkisliak/chooseFlightPage.fxml"),
                    (Stage) searchForTicketsButton.getScene().getWindow());
        });

        adminPanelAddFlightButton.setOnAction(event -> {
            resetAdminPanelChangeSeats();

            Assets.changePage(
                    getClass().getResource("/il/ac/sce/project_aleksandrkisliak/addNewFlight.fxml"),
                    (Stage) adminPanelAddFlightButton.getScene().getWindow());
        });

        adminPanelChangeSeatsCancelButton.setOnAction(event -> {
                    resetAdminPanelChangeSeats();

                    if (isShowAirplanePanel) {
                        resetAddAirplanePanel();
                    }

                    if (isShowChangePricePanel) {
                        resetChangePricePanel();
                    }

                    if (isShowDeleteFlightPanel) {
                        resetDeleteFlightPanel();
                    }
                }
        );

        adminPanelDeleteFlightButton.setOnAction(event -> {
            adminPanelErrorText.setVisible(false);

            if (isShowAirplanePanel) {
                resetAddAirplanePanel();
            }

            if (isShowChangePricePanel) {
                resetChangePricePanel();
            }

            if (!isShowDeleteFlightPanel && !isSearchedDeleteFlight) {
                resetAdminPanelChangeSeats();
                isShowDeleteFlightPanel = true;
                deleteFlightPanel.setVisible(true);
                adminPanelChangeSeatsCancelButton.setVisible(true);
                adminPanelDeleteFlightButton.setText("Search");
                return;
            }

            if (isShowDeleteFlightPanel && !isSearchedDeleteFlight) {
                flightId = Integer.parseInt(deleteFlightInput.getText().trim() + "");
                ResultSet resultSet = db.getFlightToDelete(flightId);


                int counter = 0;
                try {
                    while (resultSet.next()) {
                        counter++;
                        String fromCountry = resultSet.getString(Const.FLIGHTS_DEPARTURECOUNTRY);
                        String toCountry = resultSet.getString(Const.FLIGHTS_DESTINATIONCOUNTRY);
                        Date deptDate = resultSet.getDate(Const.FLIGHTS_DEPARTUREDATE);

                        deleteFlightInfoId.setText(flightId + "");
                        deleteFlightInfoFrom.setText(fromCountry);
                        deleteFlightInfoTo.setText(toCountry);
                        deleteFlightInfoDeprDate.setText(deptDate.toString());

                        isSearchedDeleteFlight = true;
                        deleteFlightInput.setDisable(true);
                        deleteFlightInfoPanel.setVisible(true);

                        adminPanelDeleteFlightButton.setText("Submit");
                    }

                    if (counter != 1) {
                        adminPanelErrorText.setText("No such flight exists!");
                        adminPanelErrorText.setVisible(true);
                    }
                    return;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


            if (isShowDeleteFlightPanel && isSearchedDeleteFlight) {
                adminPanelErrorText.setVisible(false);

                int res = db.deleteFlight(flightId);

                if (res != -1) {
                    isShowDeleteFlightPanel = false;
                    isSearchedDeleteFlight = false;
                    resetDeleteFlightPanel();
                    adminPanelSuccessText.setVisible(true);
                } else {
                    adminPanelErrorText.setVisible(true);
                    adminPanelErrorText.setText("You cannot delete this flight!");
                }
            }
        });

        adminPanelChangePriceButton.setOnAction(event -> {
            adminPanelErrorText.setVisible(false);

            if (isShowAirplanePanel) {
                resetAddAirplanePanel();
            }

            if (isShowDeleteFlightPanel) {
                resetDeleteFlightPanel();
            }

            if (!isShowChangePricePanel && !isSearchedChangePrice) {
                resetAdminPanelChangeSeats();
                isShowChangePricePanel = true;
                changePricePanel.setVisible(true);
                adminPanelChangeSeatsCancelButton.setVisible(true);
                adminPanelChangePriceButton.setText("Search");
                return;
            }

            if (isShowChangePricePanel && !isSearchedChangePrice) {
                flightId = Integer.parseInt(changePriceInput.getText().trim() + "");
                ResultSet resultSet = db.getPriceFlight(flightId);

                int counter = 0;
                try {
                    while (resultSet.next()) {
                        counter++;
                        int currentPrice = resultSet.getInt(Const.FLIGHTS_PRICE);
                        changePricePrice.setText(currentPrice + "");
                        changePriceFlightID.setText(flightId + "");

                        isSearchedChangePrice = true;
                        changePriceInfoPanel.setVisible(true);
                        changePriceInput.setText("");
                        changePriceInput.setPromptText("Price");
                        adminPanelChangePriceButton.setText("Submit");
                    }

                    if (counter != 1) {
                        adminPanelErrorText.setText("No such flight exists!");
                        adminPanelErrorText.setVisible(true);
                    }
                    return;
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

            if (isShowChangePricePanel && isSearchedChangePrice) {
                adminPanelErrorText.setVisible(false);

                int newPrice = Integer.parseInt(changePriceInput.getText());
                if (newPrice >= 0) {

                    int res = db.updatePrice(flightId, newPrice);

                    if (res != -1) {
                        isSearchedChangePrice = false;
                        isShowChangePricePanel = false;
                        resetChangePricePanel();
                        adminPanelSuccessText.setVisible(true);
                    } else {
                        adminPanelErrorText.setVisible(true);
                        adminPanelErrorText.setText("Something went wrong!");
                    }
                } else {
                    adminPanelErrorText.setText("The value must be at least 0");
                    adminPanelErrorText.setVisible(true);
                }

            }


        });


        adminPanelAddAirplaneButton.setOnAction(event -> {
            adminPanelSuccessText.setVisible(false);

            if (isShowChangePricePanel) {
                resetChangePricePanel();
            }

            if (isShowDeleteFlightPanel) {
                resetDeleteFlightPanel();
            }

            if (!isShowAirplanePanel) {
                resetAddAirplanePanel();
                isShowAirplanePanel = true;
                addAirplanePanel.setVisible(true);
                adminPanelChangeSeatsCancelButton.setVisible(true);
                return;
            }

            String airModel = addAirplanePanelModel.getText();
            int airCapacity = Integer.parseInt(addAirplanePanelCapacity.getText());

            Airplane airplane = new Airplane(airModel, airCapacity);
            int res = db.createAirplane(airplane);
            if (res != -1) {
                adminPanelSuccessText.setVisible(true);
                resetAddAirplanePanel();
                adminPanelChangeSeatsCancelButton.setVisible(false);
            }

        });

        adminPanelChangeSeatsButton.setOnAction(event -> {
            adminPanelShowAllFlights.setVisible(false);
            adminPanelSuccessText.setVisible(false);
            adminPanelErrorText.setVisible(false);

            if (isShowAirplanePanel) {
                resetAddAirplanePanel();
            }

            if (isShowChangePricePanel) {
                resetChangePricePanel();
            }

            if (isShowDeleteFlightPanel) {
                resetDeleteFlightPanel();
            }

            // first
            if (!isChangeSeats && !isSearchedChangeSeats) {
                isChangeSeats = true;
                changeSeatsFlightId.setVisible(true);
                adminPanelChangeSeatsButton.setText("Search");
                adminPanelChangeSeatsCancelButton.setVisible(true);
                return;
            }

            // second
            if (isChangeSeats && !isSearchedChangeSeats) {
                flightId = Integer.parseInt(changeSeatsFlightId.getText().trim() + "");
                ResultSet resultSet = db.getSeatsFlight(flightId);

                int counter = 0;
                try {
                    while (resultSet.next()) {
                        counter++;
                        totalSeats.set(resultSet.getInt(Const.FLIGHTS_TOTALSEATS));
                        availableSeats.set(resultSet.getInt(Const.FLIGHTS_AVAILABLESEATS));
                        capacity.set(resultSet.getInt(Const.AIRPLANES_CAPACITY));

                        adminPanelAirplaneCapacity.setText(String.valueOf(capacity.get()));
                        adminPanelTotalSeats.setText(String.valueOf(totalSeats.get()));
                        adminPanelAvailableSeats.setText(String.valueOf(availableSeats.get()));
                        adminPanelFlightId.setText(String.valueOf(flightId));

                        isSearchedChangeSeats = true;
                        adminPanelFlightInfoPanel.setVisible(true);
                        changeSeatsFlightId.setText("");
                        changeSeatsFlightId.setPromptText("Number of seats");
                        adminPanelChangeSeatsButton.setText("Submit");
                    }

                    if (counter != 1) {
                        adminPanelErrorText.setText("No such flight exists!");
                        adminPanelErrorText.setVisible(true);
                    }
                    return;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            // third
            if (isChangeSeats && isSearchedChangeSeats) {
                adminPanelErrorText.setVisible(false);

                int newValueSeats = Integer.parseInt(changeSeatsFlightId.getText());
                int minValue = totalSeats.get() - availableSeats.get();

                if (newValueSeats > capacity.get()) {
                    adminPanelErrorText.setText("The value cannot exceed the airplane capacity - " + capacity.get());
                    adminPanelErrorText.setVisible(true);
                    return;
                }


                if (newValueSeats >= minValue) {
                    int res = db.updateSeats(flightId, newValueSeats, newValueSeats - minValue);

                    if (res != -1) {
                        isSearchedChangeSeats = false;
                        isChangeSeats = false;
                        resetAdminPanelChangeSeats();
                        adminPanelSuccessText.setVisible(true);
                    } else {
                        adminPanelErrorText.setVisible(true);
                        adminPanelErrorText.setText("Something went wrong!");
                    }
                } else {
                    adminPanelErrorText.setText("The value must be at least " + minValue);
                    adminPanelErrorText.setVisible(true);
                }
            }
        });
    }


    private void initializeAdmin(boolean isAdmin) {
        isAdminTitle.setVisible(isAdmin);
        adminPanelLine.setVisible(isAdmin);
        adminPanelTitle.setVisible(isAdmin);
        adminPanelChangeSeatsButton.setVisible(isAdmin);
        adminPanelAddFlightButton.setVisible(isAdmin);
        adminPanelAddAirplaneButton.setVisible(isAdmin);
        adminPanelChangePriceButton.setVisible(isAdmin);
        adminPanelShowAllFlights.setVisible(isAdmin);
        adminPanelDeleteFlightButton.setVisible(isAdmin);
        TextFormatter<String> textFormatter = new TextFormatter<>(Assets.filter);
        changeSeatsFlightId.setTextFormatter(textFormatter);
    }

    private void resetAdminPanelChangeSeats() {
        adminPanelShowAllFlights.setVisible(true);
        adminPanelSuccessText.setVisible(false);
        changeSeatsFlightId.setPromptText("Flight id");
        adminPanelFlightInfoPanel.setVisible(false);
        isChangeSeats = false;
        isSearchedChangeSeats = false;
        changeSeatsFlightId.setVisible(false);
        adminPanelErrorText.setVisible(false);
        adminPanelChangeSeatsButton.setText("Change seats");
        changeSeatsFlightId.setText("");
        adminPanelChangeSeatsCancelButton.setVisible(false);
    }

    private void resetAddAirplanePanel() {
        addAirplanePanel.setVisible(false);
        isShowAirplanePanel = false;
        addAirplanePanelCapacity.setText("");
        addAirplanePanelModel.setText("");
        adminPanelChangeSeatsCancelButton.setVisible(false);
    }

    private void resetChangePricePanel() {
        isShowChangePricePanel = false;
        changePriceInput.setText("");
        changePricePanel.setVisible(false);
        changePriceInfoPanel.setVisible(false);
        changePriceInput.setPromptText("Flight ID");
        adminPanelChangePriceButton.setText("Change Price");
        adminPanelChangeSeatsCancelButton.setVisible(false);
    }

    private void resetDeleteFlightPanel() {
        isShowDeleteFlightPanel = false;
        isSearchedDeleteFlight = false;
        deleteFlightInput.setText("");
        deleteFlightInfoId.setText("");
        deleteFlightInfoTo.setText("");
        deleteFlightInfoDeprDate.setText("");
        deleteFlightInfoFrom.setText("");
        adminPanelDeleteFlightButton.setText("Delete flight");
        deleteFlightPanel.setVisible(false);
        deleteFlightInfoPanel.setVisible(false);
        adminPanelChangeSeatsCancelButton.setVisible(false);
        deleteFlightInput.setDisable(false);
    }

}
