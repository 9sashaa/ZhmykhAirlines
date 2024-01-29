package il.ac.sce.project_aleksandrkisliak.model;

import java.sql.Date;
import java.sql.Time;

public class Flight {
    private int flightid;
    private String departureCountry;
    private String destinationCountry;
    private int price;
    private Time departureTime;
    private Date departureDate;

    private Date arrivalDate;
    private Time arrivalTime;
    private int availableSeats;
    private int totalSeats;
    private Time flightDuration;
    private int airplaneid;

    public Flight(String departureCountry, String destinationCountry, int price, Time departureTime, Date departureDate, Date arrivalDate, Time arrivalTime, int availableSeats, int totalSeats, Time flightDuration, int airplaneid) {
        this.departureCountry = departureCountry;
        this.destinationCountry = destinationCountry;
        this.price = price;
        this.departureTime = departureTime;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
        this.totalSeats = totalSeats;
        this.flightDuration = flightDuration;
        this.airplaneid = airplaneid;
    }

    public Flight(int flightid, String departureCountry, String destinationCountry, int price, Time departureTime, Date departureDate, Date arrivalDate, Time arrivalTime, int availableSeats, int totalSeats, Time flightDuration, int airplaneid) {
        this.flightid = flightid;
        this.departureCountry = departureCountry;
        this.destinationCountry = destinationCountry;
        this.price = price;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.flightDuration = flightDuration;
        this.availableSeats = availableSeats;
        this.totalSeats = totalSeats;
        this.airplaneid = airplaneid;
    }

    public void setFlightid(int flightid) {
        this.flightid = flightid;
    }

    public int getFlightid() {
        return flightid;
    }

    public String getDepartureCountry() {
        return departureCountry;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public int getPrice() {
        return price;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public Time getFlightDuration() {
        return flightDuration;
    }

    public int getAirplaneid() {
        return airplaneid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        return flightid == flight.flightid;
    }

    @Override
    public int hashCode() {
        return flightid;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightid=" + flightid +
                ", departureCountry='" + departureCountry + '\'' +
                ", destinationCountry='" + destinationCountry + '\'' +
                ", price=" + price +
                ", departureTime=" + departureTime +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                ", arrivalTime=" + arrivalTime +
                ", availableSeats=" + availableSeats +
                ", totalSeats=" + totalSeats +
                ", flightDuration=" + flightDuration +
                ", airplaneid='" + airplaneid + '\'' +
                '}';
    }
}
