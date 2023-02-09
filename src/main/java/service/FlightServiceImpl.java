package service;

import dao.database.FlightDAO;
import exceptions.FlightNotFoundException;
import exceptions.NotEnoughSeatsException;
import exceptions.TimeException;
import model.City;
import model.Flight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FlightServiceImpl implements FlightService {

    private final FlightDAO flightDAO;

    public FlightServiceImpl(FlightDAO flightDAO) {
        this.flightDAO = flightDAO;
    }

    @Override
    public void increaseSeatsLeft(String flightNumber, int amount) {
        Flight flight = get(flightNumber);
        checkAmount(amount);
        checkFlightTime(flight);

        flightDAO.increaseSeatsLeft(flightNumber, amount);
    }

    @Override
    public void decreaseSeatsLeft(String flightNumber, int amount) {
        Flight flight = get(flightNumber);
        checkAmount(amount);
        checkFlightTime(flight);
        checkSeats(flight, amount);

        flightDAO.decreaseSeatsLeft(flightNumber, amount);
    }

    @Override
    public Flight get(String flightNumber) {
        return flightDAO.get(flightNumber).orElseThrow(() -> new FlightNotFoundException(flightNumber));
    }

    @Override
    public List<Flight> search(City origin, City destination, LocalDate departureDate) {
        return flightDAO.search(origin, destination, departureDate);
    }

    private void checkAmount(int amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Invalid amount.");
    }

    private void checkFlightTime(Flight flight) {
        if (LocalDateTime.now().isAfter(flight.getDeparture()))
            throw new TimeException();
    }

    private void checkSeats(Flight flight, int amount) {
        if (flight.getSeatsLeft() < amount)
            throw new NotEnoughSeatsException(flight.getFlightNumber());
    }
}