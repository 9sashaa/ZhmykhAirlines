package il.ac.sce.project_aleksandrkisliak;

import il.ac.sce.project_aleksandrkisliak.model.Flight;
import il.ac.sce.project_aleksandrkisliak.model.FlightWithModel;
import il.ac.sce.project_aleksandrkisliak.model.Ticket;
import il.ac.sce.project_aleksandrkisliak.model.User;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MainState {
    private boolean isAuth;
    private User user;
    private List<FlightWithModel> allFlights = new ArrayList<>();

    private SearchInputs searchInputs = new SearchInputs();

    private FlightWithModel selectedFlightToBuy;

    private List<Ticket> selectedSeats = new ArrayList<>();

    private Ticket ticketForPay;

    public void logOutAuth() {
        isAuth = false;
        user = null;
        allFlights = new ArrayList<>();
        searchInputs = new SearchInputs();
        selectedFlightToBuy = null;
    }

    public void setSearchInputs(String from, String to, Date date) {
        this.searchInputs = new SearchInputs(from, to, date);
    }

    public void setSearchInputs(String from, String to) {
        this.searchInputs = new SearchInputs(from, to);
    }

    public SearchInputs getSearchInputs() {
        return searchInputs;
    }

    public void logInAuth(User user) {
        isAuth = true;
        this.user = user;
    }

    public void resetFlights() {
        allFlights = new ArrayList<>();
    }

    public void resetSearchInputs() {
        searchInputs.setFrom("");
        searchInputs.setTo("");
    }

    public List<Ticket> getSelectedSeats() {
        return selectedSeats;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<FlightWithModel> getAllFlights() {
        return allFlights;
    }

    public void setAllFlights(List<FlightWithModel> allFlights) {
        this.allFlights = allFlights;
    }

    public FlightWithModel getSelectedFlightToBuy() {
        return selectedFlightToBuy;
    }

    public Ticket getTicketForPay() {
        return ticketForPay;
    }

    public void setTicketForPay(Ticket ticketForPay) {
        this.ticketForPay = ticketForPay;
    }

    public void setSelectedFlightToBuy(FlightWithModel selectedFlightToBuy) {
        this.selectedFlightToBuy = selectedFlightToBuy;
    }

    public boolean isInSelectedSeats(int flightid, int seat) {
        for (int i = 0; i < selectedSeats.size(); i++) {
            if (selectedSeats.get(i).getFlightid() == flightid
                    && selectedSeats.get(i).getSeatsNumber() == seat) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "MainState{" +
                "isAuth=" + isAuth +
                ", user=" + user +
                ", allFlights=" + allFlights +
                ", searchInputs=" + searchInputs +
                ", selectedFlightToBuy=" + selectedFlightToBuy +
                ", selectedSeats=" + selectedSeats +
                '}';
    }

    public class SearchInputs {
        private String from;
        private String to;
        private Date date;

        public SearchInputs() {
        }

        public SearchInputs(String from, String to) {
            this.from = from;
            this.to = to;
        }

        public SearchInputs(String from, String to, Date date) {
            this.from = from;
            this.to = to;
            this.date = date;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }
}
