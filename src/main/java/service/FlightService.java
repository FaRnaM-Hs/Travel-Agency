package service;

import model.City;
import model.Flight;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {

    List<Flight> search(City origin, City destination, LocalDate departureDate);
}