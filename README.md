# Zhmykh Airlines

A desktop flight-booking application written in JavaFX. Users register, search for flights by route and date, choose seats on the aircraft, buy and pay for tickets, and manage their personal account and their tickets. A flow for adding new flights is also provided.

## Project Description

The application implements a complete airline user journey as a desktop program with a graphical interface:

- **Registration and sign-in** — create an account and authenticate the user.
- **Flight search** — choose the departure point, destination, and departure date.
- **Flight listing** — a list of all available flights with aircraft information.
- **Seat selection** — interactive selection of available seats in the cabin of the chosen flight.
- **Ticket purchase and payment** — book the selected seats and pay.
- **Personal account** — manage profile data.
- **My tickets** — view and manage purchased tickets.
- **Add a flight** — create new flights in the system.

The application follows the **MVC** architecture: the interface is described in FXML markup, screen logic lives in controllers, domain entities are in models, and data access is encapsulated in a layer that works with a MySQL database. The global session state (authentication, current user, found flights, selected seats) is stored in the `MainState` class.

## Technologies

- **Java 21**
- **JavaFX 21** (`javafx-controls`, `javafx-fxml`) — graphical interface and FXML markup
- **Maven** — project build (Maven Wrapper `mvnw` included)
- **MySQL** — data storage, accessed via JDBC
- **JUnit 5** — unit testing
- **MVC** architecture (Model — View — Controller)

## Installation

Required environment:

- JDK 21
- Maven (or use the bundled `mvnw` / `mvnw.cmd`)
- A MySQL server

Steps:

```bash
# 1. Clone the repository
git clone https://github.com/9sashaa/ZhmykhAirlines.git
cd ZhmykhAirlines

# 2. Create a database in MySQL (the name ZhmykhAirlines is expected by default)
```

Configure the database connection parameters in
`src/main/java/il/ac/sce/project_aleksandrkisliak/Database/Config.java`:

```java
protected String dbHost = "localhost";
protected String dbPort = "3306";
protected String dbUser = "<your_user>";
protected String dbPass = "<your_password>";
protected String dbName = "ZhmykhAirlines";
```

> **Security recommendation:** database credentials should be moved out of the source code into environment variables or an external configuration file that is not committed to version control.

## Usage

Run the application via the JavaFX plugin:

```bash
# Using the Maven Wrapper
./mvnw clean javafx:run

# or with Maven installed
mvn clean javafx:run
```

On startup, the "Zhmykh airlines!" window opens. The typical flow: register or sign in, set the flight search parameters, choose a flight and seats, then book and pay for the ticket.

## Project Structure

```
ZhmykhAirlines/
├── pom.xml                        # Maven and dependency configuration
├── mvnw, mvnw.cmd, .mvn/          # Maven Wrapper
└── src/main/
    ├── java/
    │   ├── module-info.java       # Java module descriptor
    │   └── il/ac/sce/project_aleksandrkisliak/
    │       ├── MainApp.java        # JavaFX application entry point
    │       ├── MainState.java      # Global session state
    │       ├── controller/         # Screen controllers
    │       │   ├── LoginController.java
    │       │   ├── RegistrationController.java
    │       │   ├── MainPageController.java
    │       │   ├── ChooseFlightController.java
    │       │   ├── ShowAllFlightController.java
    │       │   ├── BuyTicketPageController.java
    │       │   ├── MyTicketPageController.java
    │       │   ├── PersonalAccountPageController.java
    │       │   ├── AddNewFlightController.java
    │       │   └── Assets.java
    │       ├── model/              # Domain models
    │       │   ├── User.java
    │       │   ├── Flight.java
    │       │   ├── Airplane.java
    │       │   ├── Ticket.java
    │       │   ├── FlightWithModel.java
    │       │   └── FlightWithTicket.java
    │       ├── Database/           # MySQL access
    │       │   ├── Config.java          # Connection parameters
    │       │   ├── Const.java           # SQL constants
    │       │   └── DatabaseHandler.java # Database queries
    │       └── assets/             # Application resources
    └── resources/                  # FXML markup and interface styles
```
