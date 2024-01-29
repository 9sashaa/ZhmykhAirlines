package il.ac.sce.project_aleksandrkisliak.model;

import java.sql.Date;
import java.sql.Time;

public class FlightWithModel extends Flight {
    private String airplaneModel;

    public FlightWithModel(int flightid, String departureCountry, String destinationCountry, int price, Time departureTime, Date departureDate, Date arrivalDate, Time arrivalTime, int availableSeats, int totalSeats, Time flightDuration, int airplaneid, String airplaneModel) {
        super(flightid, departureCountry, destinationCountry, price, departureTime, departureDate, arrivalDate, arrivalTime, availableSeats, totalSeats, flightDuration, airplaneid);
        this.airplaneModel = airplaneModel;
    }

    public String getAirplaneModel() {
        return airplaneModel;
    }

    public void setAirplaneModel(String airplaneModel) {
        this.airplaneModel = airplaneModel;
    }
}
