package il.ac.sce.project_aleksandrkisliak.Database;

import il.ac.sce.project_aleksandrkisliak.model.Airplane;
import il.ac.sce.project_aleksandrkisliak.model.Flight;
import il.ac.sce.project_aleksandrkisliak.model.Ticket;
import il.ac.sce.project_aleksandrkisliak.model.User;

import java.sql.*;

public class DatabaseHandler extends Config {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
//        dbConnection = DriverManager.getConnection("jdbc:mysql://localhost/?user=" + dbUser + "&password=" + dbPass);
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public int signUpUser(User user) {
        String insert = "INSERT INTO " + Const.USERS_TABLE +
                "(" + Const.USERS_FIRSTNAME + "," + Const.USERS_LASTNAME + "," + Const.USERS_USERNAME
                + "," + Const.USERS_PHONENUMBER + "," + Const.USERS_PASSWORD + ")"
                + "VALUES(?,?,?,?,?)";


        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.getMessage();
        }
        return -1;
    }

    public ResultSet getUser(User user) {
        ResultSet resultSet = null;
        if (!user.getUsername().isEmpty() || !user.getPassword().isEmpty()) {
            String query = "SELECT * FROM " + Const.USERS_TABLE
                    + " WHERE " + Const.USERS_USERNAME + "=?" + " AND " + Const.USERS_PASSWORD + "=?";

            try {
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                resultSet = preparedStatement.executeQuery();

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please enter your credentials");
        }

        return resultSet;
    }

    public int createFlight(Flight flight) {
        String insert = "INSERT INTO " + Const.FLIGHTS_TABLE +
                "(" + Const.FLIGHTS_DEPARTURECOUNTRY + "," + Const.FLIGHTS_DESTINATIONCOUNTRY + "," + Const.FLIGHTS_PRICE
                + "," + Const.FLIGHTS_DEPARTUREDATE + "," + Const.FLIGHTS_ARRIVALDATE + "," + Const.FLIGHTS_ARRIVALTIME
                + "," + Const.FLIGHTS_DEPARTURETIME + "," + Const.FLIGHTS_FLIGHTDURATION + "," + Const.FLIGHTS_AVAILABLESEATS
                + "," + Const.FLIGHTS_TOTALSEATS + "," + Const.FLIGHTS_AIRPLANEID + ")"
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, flight.getDepartureCountry().toUpperCase());
            preparedStatement.setString(2, flight.getDestinationCountry().toUpperCase());
            preparedStatement.setInt(3, flight.getPrice());
            preparedStatement.setDate(4, flight.getDepartureDate());
            preparedStatement.setDate(5, flight.getArrivalDate());
            preparedStatement.setTime(6, flight.getArrivalTime());
            preparedStatement.setTime(7, flight.getDepartureTime());
            preparedStatement.setTime(8, flight.getFlightDuration());
            preparedStatement.setInt(9, flight.getAvailableSeats());
            preparedStatement.setInt(10, flight.getAvailableSeats());
            preparedStatement.setInt(11, flight.getAirplaneid());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public ResultSet getFlights() {
        ResultSet resultSet = null;
        String query = "SELECT * " +
                " FROM " + Const.FLIGHTS_TABLE + "," + Const.AIRPLANES_TABLE +
                " WHERE " + Const.FLIGHTS_AIRPLANEID + "=" + Const.AIRPLANES_ID;

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getAirplanes() {
        ResultSet resultSet = null;

        String query = "SELECT * FROM " + Const.AIRPLANES_TABLE;

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getSeatsFlight(int id) {
        ResultSet resultSet = null;

        String query = "SELECT " + Const.FLIGHTS_ID + "," + Const.FLIGHTS_AVAILABLESEATS + "," + Const.FLIGHTS_TOTALSEATS + "," + Const.AIRPLANES_CAPACITY +
                " FROM " + Const.FLIGHTS_TABLE + "," + Const.AIRPLANES_TABLE
                + " WHERE " + Const.FLIGHTS_ID + "=? AND " + Const.AIRPLANES_ID + "=" + Const.FLIGHTS_AIRPLANEID;

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public int updateSeats(int id, int countOfSeats, int availableSeats) {

        String update = "UPDATE " + Const.FLIGHTS_TABLE +
                " SET " + Const.FLIGHTS_TOTALSEATS + "=?," + Const.FLIGHTS_AVAILABLESEATS + "=?" +
                " WHERE " + Const.FLIGHTS_ID + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
            preparedStatement.setInt(1, countOfSeats);
            preparedStatement.setInt(2, availableSeats);
            preparedStatement.setInt(3, id);

            return preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public int createAirplane(Airplane airplane) {
        String insert = "INSERT INTO " + Const.AIRPLANES_TABLE +
                "(" + Const.AIRPLANES_MODEL + "," + Const.AIRPLANES_CAPACITY + ")"
                + "VALUES(?,?)";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, airplane.getModel());
            preparedStatement.setInt(2, airplane.getCapacity());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public ResultSet getPriceFlight(int id) {
        ResultSet resultSet = null;

        String query = "SELECT " + Const.FLIGHTS_ID + "," + Const.FLIGHTS_PRICE +
                " FROM " + Const.FLIGHTS_TABLE
                + " WHERE " + Const.FLIGHTS_ID + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public int updatePrice(int id, int price) {

        String update = "UPDATE " + Const.FLIGHTS_TABLE +
                " SET " + Const.FLIGHTS_PRICE + "=? " +
                " WHERE " + Const.FLIGHTS_ID + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
            preparedStatement.setInt(1, price);
            preparedStatement.setInt(2, id);

            return preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ResultSet getFlightToDelete(int id) {
        ResultSet resultSet = null;

        String query = "SELECT " + Const.FLIGHTS_ID + "," + Const.FLIGHTS_DEPARTURECOUNTRY + "," + Const.FLIGHTS_DESTINATIONCOUNTRY + "," + Const.FLIGHTS_DEPARTUREDATE +
                " FROM " + Const.FLIGHTS_TABLE
                + " WHERE " + Const.FLIGHTS_ID + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public int deleteFlight(int id) {
        int resultSet = -1;

        String delete = "DELETE FROM " + Const.FLIGHTS_TABLE +
                " WHERE " + Const.FLIGHTS_ID + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(delete);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.getMessage();
        }

        return resultSet;
    }

    public ResultSet getFlights(String from, String to, Date depDate) {
        ResultSet resultSet = null;

        String query = "SELECT * " +
                " FROM " + Const.FLIGHTS_TABLE + "," + Const.AIRPLANES_TABLE +
                " WHERE " + Const.FLIGHTS_AIRPLANEID + "=" + Const.AIRPLANES_ID + " AND " +
                Const.FLIGHTS_DEPARTURECOUNTRY + "=? AND " + Const.FLIGHTS_DESTINATIONCOUNTRY + "=? AND " +
                Const.FLIGHTS_DEPARTUREDATE + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString(1, from.toUpperCase());
            preparedStatement.setString(2, to.toUpperCase());
            preparedStatement.setDate(3, depDate);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getFlightsWithoutDate(String from, String to) {
        ResultSet resultSet = null;

        String query = "SELECT * " +
                " FROM " + Const.FLIGHTS_TABLE + "," + Const.AIRPLANES_TABLE +
                " WHERE " + Const.FLIGHTS_AIRPLANEID + "=" + Const.AIRPLANES_ID + " AND " +
                Const.FLIGHTS_DEPARTURECOUNTRY + "=? AND " + Const.FLIGHTS_DESTINATIONCOUNTRY + "=?" + " AND departureDate > CURDATE()";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString(1, from.toUpperCase());
            preparedStatement.setString(2, to.toUpperCase());
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public int createTicket(Ticket ticket) {
        String insert = "INSERT INTO " + Const.TICKETS_TABLE +
                "(" + Const.TICKETS_FLIGHTID + "," + Const.TICKETS_USERID + "," + Const.TICKETS_SEATSNUMBER + "," + Const.TICKETS_PURCHASEDATE + "," + Const.TICKETS_WITHLUGGAGE + ")"
                + "VALUES(?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, ticket.getFlightid());
            preparedStatement.setInt(2, ticket.getUserid());
            preparedStatement.setInt(3, ticket.getSeatsNumber());
            preparedStatement.setDate(4, ticket.getPurchaseDate());
            preparedStatement.setBoolean(5, ticket.isWithLuggage());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public ResultSet getTicketsOfFlight(int id) {
        ResultSet resultSet = null;

        String query = "SELECT * " +
                " FROM " + Const.TICKETS_TABLE +
                " WHERE " + Const.TICKETS_FLIGHTID + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getUserTicket(int id) {
        ResultSet resultSet = null;

        String query = "SELECT * " +
                " FROM " + Const.TICKETS_TABLE + " ts ," + Const.FLIGHTS_TABLE + " fs " +
                " WHERE " + Const.TICKETS_USERID + "=?" +
                " AND ts." + Const.TICKETS_FLIGHTID + "=fs." + Const.FLIGHTS_ID;

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public int updateAvailableSeats(int id, int curr) {

        String update = "UPDATE " + Const.FLIGHTS_TABLE +
                " SET " + Const.FLIGHTS_AVAILABLESEATS + "=? " +
                " WHERE " + Const.FLIGHTS_ID + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
            preparedStatement.setInt(1, curr);
            preparedStatement.setInt(2, id);

            return preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
