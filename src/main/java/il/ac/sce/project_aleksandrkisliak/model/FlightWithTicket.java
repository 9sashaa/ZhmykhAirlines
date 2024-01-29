package il.ac.sce.project_aleksandrkisliak.model;

import java.sql.Date;
import java.sql.Time;

public class FlightWithTicket extends Flight {
    private Ticket ticket;

    public FlightWithTicket(int flightid, String departureCountry, String destinationCountry, int price, Time departureTime, Date departureDate, Date arrivalDate, Time arrivalTime, int availableSeats, int totalSeats, Time flightDuration, int airplaneid, Ticket ticket) {
        super(flightid, departureCountry, destinationCountry, price, departureTime, departureDate, arrivalDate, arrivalTime, availableSeats, totalSeats, flightDuration, airplaneid);
        this.ticket = ticket;
    }

    public int getTicketid() {
        return ticket.getTicketid();
    }

    public int getFlightid() {
        return ticket.getFlightid();
    }

    public int getUserid() {
        return ticket.getUserid();
    }

    public int getSeatsNumber() {
        return ticket.getSeatsNumber();
    }

    public Date getPurchaseDate() {
        return ticket.getPurchaseDate();
    }

    public boolean isWithLuggage() {
        return ticket.isWithLuggage();
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
