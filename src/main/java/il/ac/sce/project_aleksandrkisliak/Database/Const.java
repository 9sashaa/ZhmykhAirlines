package il.ac.sce.project_aleksandrkisliak.Database;

public class Const {
    public static final String USERS_TABLE = "users";
    public static final String FLIGHTS_TABLE = "flights";
    public static final String AIRPLANES_TABLE = "airplanes";
    public static final String TICKETS_TABLE = "tickets";


    // Column users
    public static final String USERS_ID = "userid";
    public static final String USERS_FIRSTNAME = "firstname";
    public static final String USERS_LASTNAME = "lastname";
    public static final String USERS_USERNAME = "username";
    public static final String USERS_PASSWORD = "password";
    public static final String USERS_PHONENUMBER = "phonenumber";
    public static final String USERS_ISADMIN = "isAdmin";
    public static final String USERS_COUNTOFTICKETSPURCHASED = "countOfTicketsPurchased";


    // Column flights
    public static final String FLIGHTS_ID = "flightid";
    public static final String FLIGHTS_DEPARTURECOUNTRY = "departureCountry";
    public static final String FLIGHTS_DESTINATIONCOUNTRY = "destinationCountry";
    public static final String FLIGHTS_PRICE = "price";
    public static final String FLIGHTS_DEPARTURETIME = "departureTime";
    public static final String FLIGHTS_DEPARTUREDATE = "departureDate";
    public static final String FLIGHTS_ARRIVALDATE = "arrivalDate";
    public static final String FLIGHTS_ARRIVALTIME = "arrivalTime";
    public static final String FLIGHTS_AVAILABLESEATS = "availableSeats";
    public static final String FLIGHTS_TOTALSEATS = "totalSeats";
    public static final String FLIGHTS_AIRPLANEID = "airplaneid";
    public static final String FLIGHTS_FLIGHTDURATION = "flightDuration";

    // Column airplanes
    public static final String AIRPLANES_ID = "idairplane";
    public static final String AIRPLANES_MODEL = "model";
    public static final String AIRPLANES_CAPACITY = "capacity";

    // Column tickets
    public static final String TICKETS_ID = "ticketid";
    public static final String TICKETS_FLIGHTID = "flightid";
    public static final String TICKETS_USERID = "userid";
    public static final String TICKETS_SEATSNUMBER = "seatsNumber";
    public static final String TICKETS_PURCHASEDATE = "purchaseDate";
    public static final String TICKETS_WITHLUGGAGE = "withLuggage";


}
