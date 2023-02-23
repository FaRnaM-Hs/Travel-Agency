package dao.database;

import model.City;
import model.Flight;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FlightDAO {

    void increaseSeatsLeft(String flightNumber, int amount);

    void decreaseSeatsLeft(String flightNumber, int amount);

    Optional<Flight> get(String flightNumber);

    List<Flight> search(City origin, City destination, LocalDate departureDate);

    boolean isExists(String flightNumber);
}