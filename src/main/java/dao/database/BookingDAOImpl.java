package dao.database;

import dao.PropertiesHelper;
import exceptions.MainSQLException;
import exceptions.FlightNotFoundException;
import model.Flight;
import model.Gender;
import model.Passenger;
import model.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.*;

public class BookingDAOImpl implements BookingDAO {

    private static final String INSERT_RESERVATION_SQL =
            "INSERT INTO reservations"
                    + " (nation_code, first_name, last_name, gender, birthday, phone_number, address,"
                    + " outbound_flight_number, return_flight_number, no_tickets)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_RESERVATION_SQL =
            "DELETE FROM reservations WHERE nation_code = ? AND outbound_flight_number = ? AND return_flight_number %s;";
    private static final String UPDATE_NO_TICKETS_SQL =
            "UPDATE reservations SET no_tickets = no_tickets + ?"
                    + " WHERE nation_code = ? AND outbound_flight_number = ? AND return_flight_number %s";
    private static final String SELECT_RESERVATIONS_BY_NATION_CODE_SQL =
            "SELECT * FROM reservations WHERE nation_code LIKE ?";
    private static final String SELECT_RESERVATIONS_BY_NAME_SQL =
            "SELECT * FROM reservations WHERE first_name LIKE ? AND last_name LIKE ?";
    private static final String SELECT_RESERVATIONS_BY_FLIGHT_NUMBER_SQL =
            "SELECT * FROM reservations WHERE outbound_flight_number LIKE ? OR return_flight_number LIKE ?";
    private static final String SELECT_EXISTS_RESERVATION_SQL =
            "SELECT EXISTS(SELECT * FROM reservations WHERE"
                    + " nation_code = ? AND outbound_flight_number = ? AND return_flight_number %s) AS 'exists'";

    private final String host;
    private final String user;
    private final String pass;

    private final FlightDAO flightDAO;

    public BookingDAOImpl() {
        PropertiesHelper propertiesHelper = new PropertiesHelper("db-config.properties");
        host = propertiesHelper.getProperty("host");
        user = propertiesHelper.getProperty("user");
        pass = propertiesHelper.getProperty("pass");
        flightDAO = new FlightDAOImpl();
    }

    @Override
    public void book(Reservation reservation) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement insert = con.prepareStatement(INSERT_RESERVATION_SQL)) {
            insert.setString(1, reservation.getPassenger().getNationalCode());
            insert.setString(2, reservation.getPassenger().getFirstName());
            insert.setString(3, reservation.getPassenger().getLastName());
            insert.setString(4, reservation.getPassenger().getGender().name);
            insert.setString(5, reservation.getPassenger().getBirthday().toString());
            insert.setString(6, reservation.getPassenger().getPhoneNumber());
            insert.setString(7, reservation.getPassenger().getAddress());
            insert.setString(8, reservation.getOutboundFlight().getFlightNumber());
            insert.setString(9, reservation.getReturnFlight().getFlightNumber());
            insert.setInt(10, reservation.getTicket());
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public void cancel(String nationalCode, String outboundFlightNumber, String returnFlightNumber) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement delete = con.prepareStatement(getFormattedQuery(DELETE_RESERVATION_SQL, returnFlightNumber))) {
            delete.setString(1, nationalCode);
            delete.setString(2, outboundFlightNumber);
            delete.executeUpdate();
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public void increaseTicket(String nationalCode, String outboundFlightNumber, String returnFlightNumber, int amount) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement update = con.prepareStatement(getFormattedQuery(UPDATE_NO_TICKETS_SQL, returnFlightNumber))) {
            update.setInt(1, amount);
            update.setString(2, nationalCode);
            update.setString(3, outboundFlightNumber);
            update.executeUpdate();
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public List<Reservation> findByNationalCode(String nationalCodeKey) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement select = con.prepareStatement(SELECT_RESERVATIONS_BY_NATION_CODE_SQL)) {
            select.setString(1, "%" + nationalCodeKey + "%");
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
    public List<Reservation> findByName(String firstNameKey, String lastNameKey) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement select = con.prepareStatement(SELECT_RESERVATIONS_BY_NAME_SQL)) {
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
    public List<Reservation> findByFlight(String flightNumber) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement select = con.prepareStatement(SELECT_RESERVATIONS_BY_FLIGHT_NUMBER_SQL)) {
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
    public boolean isExists(String nationalCode, String outboundFlightNumber, String returnFlightNumber) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement select = con.prepareStatement(getFormattedQuery(SELECT_EXISTS_RESERVATION_SQL, returnFlightNumber))) {
            select.setString(1, nationalCode);
            select.setString(2, outboundFlightNumber);
            ResultSet resultSet = select.executeQuery();
            resultSet.next();
            return resultSet.getInt("exists") == 1;
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    private String getFormattedQuery(String query, String returnFlightNumber) {
        return format(query, (returnFlightNumber != null) ? format("= '%s'", returnFlightNumber) : "IS NULL");
    }

    private Reservation getReservationFromResultSet(ResultSet resultSet) {
        try {
            Flight outboundFlight = getOutboundFlightFromResultSet(resultSet);
            Flight returnFlight = getReturnFlightFromResultSet(resultSet);
            Passenger passenger = getPassengerFromResultSet(resultSet);
            int ticket = resultSet.getInt("no_tickets");
            return new Reservation(outboundFlight, returnFlight, passenger, ticket);
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    private Flight getOutboundFlightFromResultSet(ResultSet resultSet) {
        try {
            String flightNumber = resultSet.getString("outbound_flight_number");
            return flightDAO.get(flightNumber).orElseThrow(() -> new FlightNotFoundException(flightNumber));
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    private Flight getReturnFlightFromResultSet(ResultSet resultSet) {
        try {
            String flightNumber = resultSet.getString("return_flight_number");
            return (flightNumber == null)
                    ? Flight.EMPTY
                    : flightDAO.get(flightNumber).orElseThrow(() -> new FlightNotFoundException(flightNumber));
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    private Passenger getPassengerFromResultSet(ResultSet resultSet) {
        try {
            String nationalCode = resultSet.getString("nation_code");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            Gender gender = Gender.valueOf(resultSet.getString("gender").toUpperCase());
            LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
            String phoneNumber = resultSet.getString("phone_number");
            String address = resultSet.getString("address");
            return new Passenger(nationalCode, firstName, lastName, gender, birthday, phoneNumber, address);
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }
}