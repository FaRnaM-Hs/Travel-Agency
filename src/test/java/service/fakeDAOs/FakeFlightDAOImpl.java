package service.fakeDAOs;

import dao.database.FlightDAO;
import exceptions.FlightNotFoundException;
import model.City;
import model.Flight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.*;
import static model.City.*;

public class FakeFlightDAOImpl implements FlightDAO {

    private final List<Flight> flights;

    public FakeFlightDAOImpl() {
        this.flights = new ArrayList<>();
        addFlights();

    }

    @Override
    public void increaseSeatsLeft(String flightNumber, int amount) {
        Flight flight = get(flightNumber).orElseThrow(() -> new FlightNotFoundException(flightNumber));
        City origin = flight.getOrigin();
        City destination = flight.getDestination();
        LocalDateTime departure = flight.getDeparture();
        double price = flight.getPrice();
        int seatsLeft = flight.getSeatsLeft();

        flights.remove(flight);
        flights.add(new Flight(flightNumber, origin, destination, departure, price, seatsLeft + amount));
    }

    @Override
    public void decreaseSeatsLeft(String flightNumber, int amount) {
        Flight flight = get(flightNumber).orElseThrow(() -> new FlightNotFoundException(flightNumber));
        City origin = flight.getOrigin();
        City destination = flight.getDestination();
        LocalDateTime departure = flight.getDeparture();
        double price = flight.getPrice();
        int seatsLeft = flight.getSeatsLeft();

        flights.remove(flight);
        flights.add(new Flight(flightNumber, origin, destination, departure, price, seatsLeft - amount));
    }

    @Override
    public Optional<Flight> get(String flightNumber) {
        return flights.stream()
                .filter(flight -> flight.getFlightNumber().equals(flightNumber))
                .findFirst();
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

    private void addFlights() {
        LocalTime twelveOClock = LocalTime.of(12, 0, 0);
        Flight f1 = new Flight("111", TEHRAN, MASHHAD, of(LocalDate.now().plusDays(1), twelveOClock), 750, 65);
        Flight f2 = new Flight("333", TEHRAN, MASHHAD, of(LocalDate.now().plusDays(1), twelveOClock), 600, 10);
        Flight f3 = new Flight("888", MASHHAD, TEHRAN, of(LocalDate.now().plusDays(3), twelveOClock), 700, 70);
        Flight f4 = new Flight("999", RASHT, AHVAZ, of(LocalDate.now().plusDays(5), twelveOClock), 850, 50);
        Flight f5 = new Flight("444", TEHRAN, MASHHAD, of(LocalDate.now().minusDays(1), twelveOClock), 600, 10);
        Flight f6 = new Flight("555", TEHRAN, MASHHAD, of(LocalDate.now().plusDays(2), twelveOClock), 900, 80);
        Flight f7 = new Flight("777", AHVAZ, RASHT, of(LocalDate.now().minusDays(1), twelveOClock), 1000, 5);

        this.flights.add(f1);
        this.flights.add(f2);
        this.flights.add(f3);
        this.flights.add(f4);
        this.flights.add(f5);
        this.flights.add(f6);
        this.flights.add(f7);
    }
}