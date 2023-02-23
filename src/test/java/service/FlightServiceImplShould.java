package service;

import model.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.fakeDAOs.FakeFlightDAOImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static java.util.Collections.*;
import static model.City.*;
import static org.junit.jupiter.api.Assertions.*;

public class FlightServiceImplShould {

    private FlightService flightService;

    @BeforeEach
    void setUp() {
        flightService = new FlightServiceImpl(new FakeFlightDAOImpl());
    }

    @Test
    void find_multiple_flights() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalTime twelveOClock = LocalTime.of(12, 0, 0);
        LocalDateTime departure = LocalDateTime.of(tomorrow, twelveOClock);

        List<Flight> result = flightService.search(TEHRAN, MASHHAD, tomorrow);

        Flight flight_1 = new Flight("111", TEHRAN, MASHHAD, departure, 750, 65);
        Flight flight_2 = new Flight("333", TEHRAN, MASHHAD, departure, 600, 10);
        assertEquals(List.of(flight_1, flight_2), result);
    }

    @Test
    void find_single_flight() {
        LocalDate threeDaysLater = LocalDate.now().plusDays(3);
        LocalDate fiveDaysLater = LocalDate.now().plusDays(5);
        LocalTime twelveOClock = LocalTime.of(12, 0, 0);
        LocalDateTime departure_1 = LocalDateTime.of(threeDaysLater, twelveOClock);
        LocalDateTime departure_2 = LocalDateTime.of(fiveDaysLater, twelveOClock);

        List<Flight> result_1 = flightService.search(MASHHAD, TEHRAN, threeDaysLater);
        List<Flight> result_2 = flightService.search(RASHT, AHVAZ, fiveDaysLater);

        Flight flight_1 = new Flight("888", MASHHAD, TEHRAN, departure_1, 700, 70);
        Flight flight_2 = new Flight("999", RASHT, AHVAZ, departure_2, 850, 50);
        assertEquals(List.of(flight_1), result_1);
        assertEquals(List.of(flight_2), result_2);
    }

    @Test
    void find_nothing_if_parameters_does_not_match() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        List<Flight> result = flightService.search(RASHT, MASHHAD, tomorrow);

        assertEquals(emptyList(), result);
    }
}
