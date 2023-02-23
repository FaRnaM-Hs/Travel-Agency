package service;

import dao.database.FlightDAO;
import model.City;
import model.Flight;

import java.time.LocalDate;
import java.util.List;

public class FlightServiceImpl implements FlightService {

    private final FlightDAO flightDAO;

    public FlightServiceImpl(FlightDAO flightDAO) {
        this.flightDAO = flightDAO;
    }

    @Override
    public List<Flight> search(City origin, City destination, LocalDate departureDate) {
        return flightDAO.search(origin, destination, departureDate);
    }
}