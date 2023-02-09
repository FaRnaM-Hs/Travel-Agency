package service;

import model.City;
import model.Flight;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {

    void increaseSeatsLeft(String flightNumber, int amount);

    void decreaseSeatsLeft(String flightNumber, int amount);

    Flight get(String flightNumber);

    List<Flight> search(City origin, City destination, LocalDate departureDate);
}
