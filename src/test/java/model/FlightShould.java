package model;

import exceptions.FlightNotFoundException;
import org.junit.jupiter.api.Test;

import static java.time.LocalDateTime.MAX;
import static java.time.LocalDateTime.of;
import static model.City.*;
import static org.junit.jupiter.api.Assertions.*;

public class FlightShould {

    @Test
    void return_true_for_empty_flight() {
        assertTrue(Flight.EMPTY.isEmpty());
    }

    @Test
    void throw_exception_if_flight_does_not_exist() {
        Flight flight = new Flight(
                "111",
                TEHRAN,
                RASHT,
                of(2023, 3, 1, 12, 0),
                100,
                55
        );

        assertThrows(
                FlightNotFoundException.class,
                () -> flight.checkExistence(false),
                flight.toString()
        );
    }

    @Test
    void not_throw_anything_if_flight_is_empty_for_checking_existence() {
        Flight flight = Flight.EMPTY;

        assertDoesNotThrow(() -> flight.checkExistence(false));
        assertDoesNotThrow(() -> flight.checkExistence(true));
    }

    @Test
    void not_check_anything_for_empty_flight() {
        Flight empty = Flight.EMPTY;

        assertDoesNotThrow(
                () -> new Flight(
                empty.getFlightNumber(),
                empty.getOrigin(),
                empty.getDestination(),
                empty.getDeparture(),
                empty.getPrice(),
                empty.getSeatsLeft()
                )
        );
    }

    @Test
    void throw_exception_if_flight_number_contains_letters() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Flight("a123b", TEHRAN, ESFAHAN, MAX, 100, 50),
                "Flight Number must have only digits."
        );
    }

    @Test
    void throw_exception_if_flight_number_contains_non_digit_characters() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Flight("@(123)", TEHRAN, ESFAHAN, MAX, 100, 50),
                "Flight Number must have only digits."
        );
    }

    @Test
    void throw_exception_if_flight_number_is_empty() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Flight("", TEHRAN, ESFAHAN, MAX, 100, 50),
                "Flight Number must have only digits."
        );
    }

    @Test
    void check_origin_and_destination_are_not_same() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Flight("696", TEHRAN, TEHRAN, MAX, 70, 30),
                "Origin and Destination cannot be the same."
        );
    }

    @Test
    void throw_exception_if_price_of_flight_is_negative() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Flight("234", RASHT, GORGAN, MAX, -5, 16),
                "Price cannot be negative."
        );
    }

    @Test
    void throw_exception_if_seats_left_is_negative() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Flight("852", GHESHM, AHVAZ, MAX, 45, -10),
                "Seats Left cannot be negative."
        );
    }

    @Test
    void be_created() {
        Flight flight = new Flight(
                "111",
                TEHRAN,
                RASHT,
                of(2023, 3, 1, 12, 0),
                100,
                55
        );

        assertEquals("111", flight.getFlightNumber());
        assertEquals(TEHRAN, flight.getOrigin());
        assertEquals(RASHT, flight.getDestination());
        assertEquals(of(2023, 3, 1, 12, 0), flight.getDeparture());
        assertEquals(100, flight.getPrice());
        assertEquals(55, flight.getSeatsLeft());
        assertEquals("Flight: 111 | Origin: Tehran | Destination: Rasht | Departure: 2023-03-01T12:00", flight.toString());
    }

    @Test
    void be_equal_to_another_flight_if_their_information_are_the_same() {
        Flight flight_1 = new Flight(
                "111",
                TEHRAN,
                RASHT,
                of(2023, 3, 1, 12, 0),
                100,
                55
        );

        Flight flight_2 = new Flight(
                "111",
                TEHRAN,
                RASHT,
                of(2023, 3, 1, 12, 0),
                100,
                55
        );

        assertEquals(flight_1, flight_2);
    }
}