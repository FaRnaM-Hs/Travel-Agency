package service.daoDoubleTestHelpers;

import dao.database.FlightDAO;
import model.City;
import model.Flight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class FlightDAOImplDouble implements FlightDAO {

    private final Set<Flight> flights;

    public FlightDAOImplDouble() {
        Flight flight_1 = new Flight("111", City.TEHRAN, City.MASHHAD, LocalDateTime.MAX, 750000, 65);
        Flight flight_2 = new Flight("222", City.MASHHAD, City.TEHRAN, LocalDateTime.MAX.minusDays(2), 700000, 70);
        Flight flight_3 = new Flight("234", City.TABRIZ, City.AHVAZ, LocalDateTime.MIN, 850000, 50);
        this.flights = Set.of(flight_1, flight_2, flight_3);
    }

    @Override
    public void increaseSeatsLeft(String flightNumber, int amount) {

    }

    @Override
    public void decreaseSeatsLeft(String flightNumber, int amount) {

    }

    @Override
    public Optional<Flight> get(String flightNumber) {
        return flights.stream()
                .filter(flight -> flight.getFlightNumber().equals(flightNumber))
                .findFirst();
    }

    @Override
    public List<Flight> getAll() {
        return flights.stream().toList();
    }

    @Override
    public List<Flight> search(City origin, City destination) {
        return flights.stream()
                .filter(flight -> flight.getOrigin().equals(origin) && flight.getDestination().equals(destination))
                .toList();
    }

    @Override
    public List<Flight> search(City origin, City destination, LocalDate departureDate) {
        return flights.stream()
                .filter(flight -> flight.getOrigin().equals(origin) && flight.getDestination().equals(destination))
                .filter(flight -> flight.getDeparture().toLocalDate().equals(departureDate))
                .toList();
    }

    @Override
    public boolean isExists(String flightNumber) {
        return flights.stream().anyMatch(flight -> flight.getFlightNumber().equals(flightNumber));
    }
}
