package dao.database;

import dao.PropertiesHelper;
import exceptions.MainSQLException;
import model.City;
import model.Flight;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDAOImpl implements FlightDAO {

    private static final String UPDATE_INCREASE_SEATS_LEFT_SQL =
            "UPDATE flights SET seats_left = seats_left + ? WHERE number = ?";
    private static final String UPDATE_DECREASE_SEATS_LEFT_SQL =
            "UPDATE flights SET seats_left = seats_left - ? WHERE number = ?";
    private static final String SELECT_FLIGHT_SQL =
            "SELECT * FROM flights WHERE number = ?";
    private static final String SELECT_FLIGHTS_ORIGIN_DESTINATION_DATE_SQL =
            "SELECT * FROM flights WHERE origin = ? AND destination = ? AND departure BETWEEN ? AND ? ";
    private static final String SELECT_EXISTS_FLIGHT_SQL =
            "SELECT EXISTS(SELECT * FROM flights WHERE number = ?) AS 'exists'";

    private final String host;
    private final String user;
    private final String pass;

    public FlightDAOImpl() {
        PropertiesHelper propertiesHelper = new PropertiesHelper("db-config.properties");
        host = propertiesHelper.getProperty("host");
        user = propertiesHelper.getProperty("user");
        pass = propertiesHelper.getProperty("pass");
    }

    @Override
    public void increaseSeatsLeft(String flightNumber, int amount) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement update = con.prepareStatement(UPDATE_INCREASE_SEATS_LEFT_SQL)) {
            update.setInt(1, amount);
            update.setString(2, flightNumber);
            update.executeUpdate();
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public void decreaseSeatsLeft(String flightNumber, int amount) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement update = con.prepareStatement(UPDATE_DECREASE_SEATS_LEFT_SQL)) {
            update.setInt(1, amount);
            update.setString(2, flightNumber);
            update.executeUpdate();
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public Optional<Flight> get(String flightNumber) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement select = con.prepareStatement(SELECT_FLIGHT_SQL)) {
            select.setString(1, flightNumber);
            ResultSet resultSet = select.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getFlightFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public List<Flight> search(City origin, City destination, LocalDate departureDate) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement select = con.prepareStatement(SELECT_FLIGHTS_ORIGIN_DESTINATION_DATE_SQL)) {
            select.setString(1, origin.name);
            select.setString(2, destination.name);
            select.setString(3, departureDate.toString() + " 00:00:00");
            select.setString(4, departureDate.toString() + " 23:59:59");
            ResultSet resultSet = select.executeQuery();
            List<Flight> flights = new ArrayList<>();
            while (resultSet.next()) {
                flights.add(getFlightFromResultSet(resultSet));
            }
            return flights;
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public boolean isExists(String flightNumber) {
        try (Connection con = DriverManager.getConnection(host, user, pass);
             PreparedStatement select = con.prepareStatement(SELECT_EXISTS_FLIGHT_SQL)) {
            select.setString(1, flightNumber);
            ResultSet resultSet = select.executeQuery();
            resultSet.next();
            return resultSet.getInt("exists") == 1;
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    private Flight getFlightFromResultSet(ResultSet resultSet) {
        try {
            String number = resultSet.getString("number");
            City origin = City.valueOf(resultSet.getString("origin").toUpperCase());
            City destination = City.valueOf(resultSet.getString("destination").toUpperCase());
            LocalDateTime departure = resultSet.getTimestamp("departure").toLocalDateTime();
            double price = resultSet.getDouble("price");
            int seatsLeft = resultSet.getInt("seats_left");
            return new Flight(number, origin, destination, departure, price, seatsLeft);
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }
}