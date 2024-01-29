package il.ac.sce.project_aleksandrkisliak.model;

import java.sql.Date;

public class Ticket {
    private int ticketid;

    private int flightid;
    private int userid;

    private int seatsNumber;
    private Date purchaseDate;
    private boolean withLuggage;

    public Ticket(int ticketid, int flightid, int userid, int seatsNumber, Date purchaseDate, boolean withLuggage) {
        this.ticketid = ticketid;
        this.flightid = flightid;
        this.userid = userid;
        this.seatsNumber = seatsNumber;
        this.purchaseDate = purchaseDate;
        this.withLuggage = withLuggage;
    }

    public Ticket(int flightid, int seatsNumber, Date purchaseDate, boolean withLuggage) {
        this.flightid = flightid;
        this.seatsNumber = seatsNumber;
        this.purchaseDate = purchaseDate;
        this.withLuggage = withLuggage;
    }

    public Ticket(int flightid, int userid, int seatsNumber, Date purchaseDate, boolean withLuggage) {
        this.flightid = flightid;
        this.userid = userid;
        this.seatsNumber = seatsNumber;
        this.purchaseDate = purchaseDate;
        this.withLuggage = withLuggage;
    }

    public int getTicketid() {
        return ticketid;
    }

    public void setTicketid(int ticketid) {
        this.ticketid = ticketid;
    }

    public int getFlightid() {
        return flightid;
    }

    public void setFlightid(int flightid) {
        this.flightid = flightid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(int seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public boolean isWithLuggage() {
        return withLuggage;
    }

    public void setWithLuggage(boolean withLuggage) {
        this.withLuggage = withLuggage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        return ticketid == ticket.ticketid;
    }

    @Override
    public int hashCode() {
        return ticketid;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketid=" + ticketid +
                ", flightid=" + flightid +
                ", userid=" + userid +
                ", seatsNumber=" + seatsNumber +
                ", purchaseDate=" + purchaseDate +
                ", withLuggage=" + withLuggage +
                '}';
    }
}
