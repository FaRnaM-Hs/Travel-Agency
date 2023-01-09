package service;

import exceptions.NoSuchFlightException;
import exceptions.NotEnoughSeatsException;
import exceptions.TimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.daoDoubleTestHelpers.FlightDAOImplDouble;
import service.impl.FlightServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

public class FlightServiceImplShould {

    private FlightService flightService;

    @BeforeEach
    void setUp() {
        flightService = new FlightServiceImpl(new FlightDAOImplDouble());
    }

    @Test
    void check_flight_exists_before_increasing_seats() {
        assertThrows(NoSuchFlightException.class, () -> flightService.increaseSeatsLeft("555", 10), "555");

        assertDoesNotThrow(() -> flightService.increaseSeatsLeft("111", 5));
    }

    @Test
    void check_amount_be_positive_before_increasing_seats() {
        assertThrows(IllegalArgumentException.class, () -> flightService.increaseSeatsLeft("111", 0), "Invalid amount.");

        assertThrows(IllegalArgumentException.class, () -> flightService.increaseSeatsLeft("111", -10), "Invalid amount.");

        assertDoesNotThrow(() -> flightService.increaseSeatsLeft("111", 5));
    }

    @Test
    void check_if_now_is_after_flight_departure_before_increasing_seats() {
        assertThrows(TimeException.class, () -> flightService.increaseSeatsLeft("234", 2));

        assertDoesNotThrow(() -> flightService.increaseSeatsLeft("111", 2));
    }

    @Test
    void check_flight_exists_before_decreasing_seats() {
        assertThrows(NoSuchFlightException.class, () -> flightService.decreaseSeatsLeft("555", 10), "555");

        assertDoesNotThrow(() -> flightService.decreaseSeatsLeft("111", 5));
    }

    @Test
    void check_amount_be_positive_before_decreasing_seats() {
        assertThrows(IllegalArgumentException.class, () -> flightService.decreaseSeatsLeft("111", 0), "Invalid amount.");

        assertThrows(IllegalArgumentException.class, () -> flightService.decreaseSeatsLeft("111", -10), "Invalid amount.");

        assertDoesNotThrow(() -> flightService.decreaseSeatsLeft("111", 5));
    }

    @Test
    void check_if_now_is_after_flight_departure_before_decreasing_seats() {
        assertThrows(TimeException.class, () -> flightService.decreaseSeatsLeft("234", 2));

        assertDoesNotThrow(() -> flightService.decreaseSeatsLeft("111", 2));
    }

    @Test
    void check_flight_has_enough_seats_before_decreasing_seats() {
        assertThrows(NotEnoughSeatsException.class, () -> flightService.decreaseSeatsLeft("111", 66),
                "111");

        assertDoesNotThrow(() -> flightService.decreaseSeatsLeft("111", 65));
    }

    @Test
    void throw_no_such_flight_exception_if_a_flight_does_not_exist() {
        assertThrows(NoSuchFlightException.class, () -> flightService.get("123"), "123");

        assertEquals( "111", flightService.get("111").getFlightNumber());
    }
}
