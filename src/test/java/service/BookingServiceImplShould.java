package service;

import exceptions.NoSuchReservationException;
import exceptions.NotEnoughSeatsException;
import exceptions.TimeException;
import model.Flight;
import model.Gender;
import model.Passenger;
import model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.daoDoubleTestHelpers.BookingDAOImplDouble;
import service.daoDoubleTestHelpers.FlightDAOImplDouble;
import service.impl.BookingServiceImpl;
import service.impl.FlightServiceImpl;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class BookingServiceImplShould {

    private BookingService bookingService;
    private FlightService flightService;

    @BeforeEach
    void setUp() {
        bookingService = new BookingServiceImpl(new BookingDAOImplDouble(), new FlightDAOImplDouble());
        flightService = new FlightServiceImpl(new FlightDAOImplDouble());
    }

    @Test
    void check_booking_time_be_2_hours_before_outbound_flight_departure() {
        Reservation oldReservation = new Reservation(flightService.get("234"), Flight.EMPTY, getPassenger(), 1);
        Reservation newReservation = new Reservation(flightService.get("111"), Flight.EMPTY, getPassenger(), 1);

        assertThrows(TimeException.class, () -> bookingService.book(oldReservation));

        assertDoesNotThrow(() -> bookingService.book(newReservation));
    }

    @Test
    void check_outbound_flight_is_before_return_flight_in_round_trip_reservations() {
        Reservation badReservation = new Reservation(flightService.get("111"), flightService.get("222"), getPassenger(), 1);
        Reservation normalReservation = new Reservation(flightService.get("222"), flightService.get("111"), getPassenger(), 1);

        assertThrows(TimeException.class, () -> bookingService.book(badReservation));

        assertDoesNotThrow(() -> bookingService.book(normalReservation));
    }

    @Test
    void throw_exception_if_number_of_tickets_are_negative_or_zero() {
        Reservation zeroReservation = new Reservation(flightService.get("222"), flightService.get("111"), getPassenger(), 0);
        Reservation negativeReservation = new Reservation(flightService.get("222"), flightService.get("111"), getPassenger(), -10);

        assertThrows(IllegalArgumentException.class, () -> bookingService.book(zeroReservation), "Tickets cannot be zero or negative");

        assertThrows(IllegalArgumentException.class, () -> bookingService.book(negativeReservation), "Tickets cannot be zero or negative");
    }

    @Test
    void check_there_is_enough_seats_for_both_round_trip_and_one_way_reservations() {
        Reservation badOneWay = new Reservation(flightService.get("222"), Flight.EMPTY, getPassenger(), 71);
        Reservation badRoundTrip = new Reservation(flightService.get("222"), flightService.get("111"), getPassenger(), 66);
        Reservation normalOneWay = new Reservation(flightService.get("222"), Flight.EMPTY, getPassenger(), 5);
        Reservation normalRoundTrip = new Reservation(flightService.get("222"), flightService.get("111"), getPassenger(), 5);

        assertThrows(NotEnoughSeatsException.class, () -> bookingService.book(badOneWay), "222");

        assertThrows(NotEnoughSeatsException.class, () -> bookingService.book(badRoundTrip), "111");

        assertDoesNotThrow(() -> bookingService.book(normalOneWay));

        assertDoesNotThrow(() -> bookingService.book(normalRoundTrip));
    }

    @Test
    void check_reservation_exists_before_canceling() {
        Reservation oneWayReservation = new Reservation(flightService.get("222"), Flight.EMPTY, getPassenger(), 1);
        Reservation roundTripReservation = new Reservation(flightService.get("222"), flightService.get("111"), getPassenger(), 1);
        bookingService.book(oneWayReservation);
        bookingService.book(roundTripReservation);

        assertThrows(NoSuchReservationException.class, () -> bookingService.cancel("1234567890", "333"));

        assertThrows(NoSuchReservationException.class, () -> bookingService.cancel("1234567890", "222", "333"));

        assertDoesNotThrow(() -> bookingService.cancel("1234567890", "222"));

        assertDoesNotThrow(() -> bookingService.cancel("1234567890", "222", "111"));
    }

    @Test
    void throw_exception_when_getting_reservation_if_does_not_exist() {
        Reservation oneWayReservation = new Reservation(flightService.get("222"), Flight.EMPTY, getPassenger(), 1);
        Reservation roundTripReservation = new Reservation(flightService.get("222"), flightService.get("111"), getPassenger(), 1);
        bookingService.book(oneWayReservation);
        bookingService.book(roundTripReservation);

        assertThrows(NoSuchReservationException.class, () -> bookingService.get("1234567890", "333"));

        assertThrows(NoSuchReservationException.class, () -> bookingService.get("1234567890", "222", "333"));

        assertDoesNotThrow(() -> bookingService.cancel("1234567890", "222"));

        assertDoesNotThrow(() -> bookingService.cancel("1234567890", "222", "111"));
    }

    private Passenger getPassenger() {
        String nationCode = "1234567890";
        String firstName = "Farnam";
        String lastName = "Hasanzadeh";
        Gender gender = Gender.MALE;
        LocalDate birthday = LocalDate.of(2004, 1, 17);
        String phoneNumber = "09123456789";
        String address = "Rasht, Guilan, Iran";
        return new Passenger(nationCode, firstName, lastName, gender, birthday, phoneNumber, address);
    }
}
