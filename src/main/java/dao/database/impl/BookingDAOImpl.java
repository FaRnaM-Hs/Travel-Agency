package dao.database.impl;

import dao.ConfigReaderException;
import dao.database.BookingDAO;
import dao.database.FlightDAO;
import dao.database.impl.exceptions.MainSQLException;
import dao.database.impl.exceptions.NoSuchFlightException;
import model.Flight;
import model.Gender;
import model.Passenger;
import model.Reservation;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class BookingDAOImpl implements BookingDAO {

    private static final String INSERT_RESERVATION_SQL =
            "INSERT INTO reservations"
            + " (nation_code, first_name, last_name, gender, birthday, phone_number, address,"
            + " outbound_flight_number, return_flight_number, no_tickets)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_RESERVATION_NATION_CODE_OUTBOUND_FLIGHT_SQL =
            "DELETE FROM reservations WHERE nation_code = ? AND outbound_flight_number = ? AND return_flight_number IS NULL";
    private static final String DELETE_RESERVATION_NATION_CODE_TWO_FLIGHTS_SQL =
            "DELETE FROM reservations WHERE nation_code = ? AND outbound_flight_number = ? AND return_flight_number = ?";
    private static final String UPDATE_NO_TICKETS_NATION_CODE_OUTBOUND_FLIGHT_SQL =
            "UPDATE reservations SET no_tickets = no_tickets + ?"
                    + " WHERE nation_code = ? AND outbound_flight_number = ? AND return_flight_number IS NULL";
    private static final String UPDATE_NO_TICKETS_NATION_CODE_TWO_FLIGHTS_SQL =
            "UPDATE reservations SET no_tickets = no_tickets + ?"
                    + " WHERE nation_code = ? AND outbound_flight_number = ? AND return_flight_number = ?";
    private static final String SELECT_RESERVATION_NATION_CODE_OUTBOUND_FLIGHT_SQL =
            "SELECT * FROM reservations WHERE nation_code = ? AND outbound_flight_number = ? AND return_flight_number IS NULL";
    private static final String SELECT_RESERVATION_NATION_CODE_TWO_FLIGHTS_SQL =
            "SELECT * FROM reservations WHERE nation_code = ? AND outbound_flight_number = ? AND return_flight_number = ?";
    private static final String SELECT_ALL_RESERVATIONS_SQL =
            "SELECT * FROM reservations";
    private static final String SELECT_RESERVATIONS_NATION_CODE_SQL =
            "SELECT * FROM reservations WHERE nation_code LIKE ?";
    private static final String SELECT_RESERVATIONS_NAME_SQL =
            "SELECT * FROM reservations WHERE first_name LIKE ? AND last_name LIKE ?";
    private static final String SELECT_RESERVATIONS_FLIGHT_SQL =
            "SELECT * FROM reservations WHERE outbound_flight_number LIKE ? OR return_flight_number LIKE ?";
    private static final String SELECT_EXISTS_RESERVATION_NATION_CODE_OUTBOUND_FLIGHT_SQL =
            "SELECT EXISTS(SELECT * FROM reservations WHERE"
            + " nation_code = ? AND outbound_flight_number = ? AND return_flight_number IS NULL) AS 'exists'";
    private static final String SELECT_EXISTS_RESERVATION_NATION_CODE_TWO_FLIGHTS_SQL =
            "SELECT EXISTS(SELECT * FROM reservations WHERE"
            + " nation_code = ? AND outbound_flight_number = ? AND return_flight_number = ?) AS 'exists'";

    private final String host;
    private final String user;
    private final String pass;

    private final FlightDAO flightDAO;

    public BookingDAOImpl() {
        try {
            FileReader reader = new FileReader("db-config.properties");
            Properties properties = new Properties();
            properties.load(reader);
            host = properties.getProperty("host");
            user = properties.getProperty("user");
            pass = properties.getProperty("pass");
        } catch (IOException e) {
            throw new ConfigReaderException(e);
        }

        flightDAO = new FlightDAOImpl();
    }

    @Override
    public void book(Reservation reservation) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement insert = con.prepareStatement(INSERT_RESERVATION_SQL)) {
            insert.setString(1, reservation.getPassenger().getNationCode());
            insert.setString(2, reservation.getPassenger().getFirstName());
            insert.setString(3, reservation.getPassenger().getLastName());
            insert.setString(4, reservation.getPassenger().getGender().name);
            insert.setString(5, reservation.getPassenger().getBirthday().toString());
            insert.setString(6, reservation.getPassenger().getPhoneNumber());
            insert.setString(7, reservation.getPassenger().getAddress());
            insert.setString(8, reservation.getOutboundFlight().getFlightNumber());
            Flight returnFlight = reservation.getReturnFlight();
            insert.setString(9, returnFlight.isEmpty() ? null : returnFlight.getFlightNumber());
            insert.setInt(10, reservation.getNumberOfTickets());
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public void cancel(String nationCode, String outboundFlightNumber) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement delete = con.prepareStatement(DELETE_RESERVATION_NATION_CODE_OUTBOUND_FLIGHT_SQL)) {
            delete.setString(1, nationCode);
            delete.setString(2, outboundFlightNumber);
            delete.executeUpdate();
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public void cancel(String nationCode, String outboundFlightNumber, String returnFlightNumber) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement delete = con.prepareStatement(DELETE_RESERVATION_NATION_CODE_TWO_FLIGHTS_SQL)) {
            delete.setString(1, nationCode);
            delete.setString(2, outboundFlightNumber);
            delete.setString(3, returnFlightNumber);
            delete.executeUpdate();
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public void increaseNumberOfTickets(String nationCode, String outboundFlightNumber, int amount) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement update = con.prepareStatement(UPDATE_NO_TICKETS_NATION_CODE_OUTBOUND_FLIGHT_SQL)) {
            update.setInt(1, amount);
            update.setString(2, nationCode);
            update.setString(3, outboundFlightNumber);
            update.executeUpdate();
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public void increaseNumberOfTickets(String nationCode, String outboundFlightNumber, String returnFlightNumber, int amount) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement update = con.prepareStatement(UPDATE_NO_TICKETS_NATION_CODE_TWO_FLIGHTS_SQL)) {
            update.setInt(1, amount);
            update.setString(2, nationCode);
            update.setString(3, outboundFlightNumber);
            update.setString(4, returnFlightNumber);
            update.executeUpdate();
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public Optional<Reservation> get(String nationCode, String outboundFlightNumber) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement select = con.prepareStatement(SELECT_RESERVATION_NATION_CODE_OUTBOUND_FLIGHT_SQL)) {
            select.setString(1, nationCode);
            select.setString(2, outboundFlightNumber);
            ResultSet resultSet = select.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getReservationFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public Optional<Reservation> get(String nationCode, String outboundFlightNumber, String returnFlightNumber) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement select = con.prepareStatement(SELECT_RESERVATION_NATION_CODE_TWO_FLIGHTS_SQL)) {
            select.setString(1, nationCode);
            select.setString(2, outboundFlightNumber);
            select.setString(3, returnFlightNumber);
            ResultSet resultSet = select.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getReservationFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public List<Reservation> getAll() {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement select = con.prepareStatement(SELECT_ALL_RESERVATIONS_SQL)) {
            ResultSet resultSet = select.executeQuery();
            List<Reservation> reservations = new ArrayList<>();
            while (resultSet.next()) {
                reservations.add(getReservationFromResultSet(resultSet));
            }
            return reservations;
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public List<Reservation> searchByNationCode(String nationCodeKey) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement select = con.prepareStatement(SELECT_RESERVATIONS_NATION_CODE_SQL)) {
            select.setString(1, "%" + nationCodeKey + "%");
            ResultSet resultSet = select.executeQuery();
            List<Reservation> reservations = new ArrayList<>();
            while (resultSet.next()) {
                reservations.add(getReservationFromResultSet(resultSet));
            }
            return reservations;
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public List<Reservation> searchByName(String firstNameKey, String lastNameKey) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement select = con.prepareStatement(SELECT_RESERVATIONS_NAME_SQL)) {
            select.setString(1, "%" + firstNameKey + "%");
            select.setString(2, "%" + lastNameKey + "%");
            ResultSet resultSet = select.executeQuery();
            List<Reservation> reservations = new ArrayList<>();
            while (resultSet.next()) {
                reservations.add(getReservationFromResultSet(resultSet));
            }
            return reservations;
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public List<Reservation> searchByFlight(String flightNumber) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement select = con.prepareStatement(SELECT_RESERVATIONS_FLIGHT_SQL)) {
            select.setString(1, "%" + flightNumber + "%");
            select.setString(2, "%" + flightNumber + "%");
            ResultSet resultSet = select.executeQuery();
            List<Reservation> reservations = new ArrayList<>();
            while (resultSet.next()) {
                reservations.add(getReservationFromResultSet(resultSet));
            }
            return reservations;
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public boolean isExists(String nationCode, String outboundFlightNumber) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement select = con.prepareStatement(SELECT_EXISTS_RESERVATION_NATION_CODE_OUTBOUND_FLIGHT_SQL)) {
            select.setString(1, nationCode);
            select.setString(2, outboundFlightNumber);
            ResultSet resultSet = select.executeQuery();
            resultSet.next();
            return resultSet.getInt("exists") == 1;
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public boolean isExists(String nationCode, String outboundFlightNumber, String returnFlightNumber) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement select = con.prepareStatement(SELECT_EXISTS_RESERVATION_NATION_CODE_TWO_FLIGHTS_SQL)) {
            select.setString(1, nationCode);
            select.setString(2, outboundFlightNumber);
            select.setString(3, returnFlightNumber);
            ResultSet resultSet = select.executeQuery();
            resultSet.next();
            return resultSet.getInt("exists") == 1;
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    private Reservation getReservationFromResultSet(ResultSet resultSet) {
        try {
            Flight outboundFlight = getOutboundFlightFromResultSet(resultSet);
            Flight returnFlight = getReturnFlightFromResultSet(resultSet);
            Passenger passenger = getPassengerFromResultSet(resultSet);
            int numberOfTickets = resultSet.getInt("no_tickets");
            return new Reservation(outboundFlight, returnFlight, passenger, numberOfTickets);
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    private Flight getOutboundFlightFromResultSet(ResultSet resultSet) {
        try {
            String flightNumber = resultSet.getString("outbound_flight_number");
            return flightDAO.get(flightNumber).orElseThrow(() -> new NoSuchFlightException(flightNumber));
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    private Flight getReturnFlightFromResultSet(ResultSet resultSet) {
        try {
            String flightNumber = resultSet.getString("return_flight_number");
            return (flightNumber == null)
                    ? Flight.EMPTY
                    : flightDAO.get(flightNumber).orElseThrow(() -> new NoSuchFlightException(flightNumber));
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    private Passenger getPassengerFromResultSet(ResultSet resultSet) {
        try {
            String nationCode = resultSet.getString("nation_code");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            Gender gender = Gender.valueOf(resultSet.getString("gender").toUpperCase());
            LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
            String phoneNumber = resultSet.getString("phone_number");
            String address = resultSet.getString("address");
            return new Passenger(nationCode, firstName, lastName, gender, birthday, phoneNumber, address);
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }
}