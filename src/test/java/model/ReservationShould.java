package model;

import exceptions.FlightNotFoundException;
import exceptions.NotEnoughSeatsException;
import exceptions.ReservationNotFoundException;
import exceptions.TimeException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.LocalDate.*;
import static model.City.*;
import static model.Gender.*;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationShould {

    @Test
    void return_false_if_return_flight_is_empty() {
        Reservation reservation = new Reservation(getTwoDaysLaterFlight(), Flight.EMPTY, getPassenger(), 1);

        assertFalse(reservation.isRoundTrip());
    }

    @Test
    void return_true_if_return_flight_is_not_empty() {
        Reservation reservation = new Reservation(getTwoDaysLaterFlight(), getMaxTimeFlight(), getPassenger(), 1);

        assertTrue(reservation.isRoundTrip());
    }

    @Test
    void throw_exception_if_outbound_flight_does_not_exist_before_booking() {
        Reservation reservation = new Reservation(getTwoDaysLaterFlight(), getMaxTimeFlight(), getPassenger(), 1);

        assertThrows(
                FlightNotFoundException.class,
                () -> reservation.checkBeforeBooking(false, true),
                reservation.getOutboundFlight().toString()
        );
    }

    @Test
    void throw_exception_if_return_flight_does_not_exist_before_booking() {
        Reservation reservation = new Reservation(getTwoDaysLaterFlight(), getMaxTimeFlight(), getPassenger(), 1);

        assertThrows(
                FlightNotFoundException.class,
                () -> reservation.checkBeforeBooking(true, false),
                reservation.getReturnFlight().toString()
        );
    }

    @Test
    void not_throw_anything_if_reservation_is_one_way_and_pass_false_for_return_flight_existence_before_booking() {
        Reservation reservation = new Reservation(getTwoDaysLaterFlight(), Flight.EMPTY, getPassenger(), 1);

        assertDoesNotThrow(() -> reservation.checkBeforeBooking(true, false));
    }

    @Test
    void be_able_to_check_outbound_flight_departure_is_after_booking_time_before_booking() {
        Reservation reservation = new Reservation(getMinTimeFlight(), Flight.EMPTY, getPassenger(), 1);

        assertThrows(TimeException.class, () -> reservation.checkBeforeBooking(true, true));
    }

    @Test
    void be_able_to_check_there_is_enough_seats_for_outbound_flight_in_one_way_reservations_before_booking() {
        Reservation reservation = new Reservation(getMaxTimeFlight(), Flight.EMPTY, getPassenger(), 66);

        assertThrows(NotEnoughSeatsException.class, () -> reservation.checkBeforeBooking(true, true));
    }

    @Test
    void be_able_to_check_there_is_enough_seats_for_outbound_flight_in_round_trip_reservations() {
        Reservation reservation = new Reservation(getTwoDaysLaterFlight(), getMaxTimeFlight(), getPassenger(), 71);

        assertThrows(NotEnoughSeatsException.class, () -> reservation.checkBeforeBooking(true, true));
    }

    @Test
    void be_able_to_check_there_is_enough_seats_for_return_flight_in_round_trip_reservations() {
        Reservation reservation = new Reservation(getTwoDaysLaterFlight(), getMaxTimeFlight(), getPassenger(), 66);

        assertThrows(NotEnoughSeatsException.class, () -> reservation.checkBeforeBooking(true, true));
    }

    @Test
    void be_able_to_check_reservation_exists_before_canceling() {
        Reservation reservation = new Reservation(getTwoDaysLaterFlight(), getMaxTimeFlight(), getPassenger(), 1);

        assertThrows(
                ReservationNotFoundException.class,
                () -> reservation.checkBeforeCanceling(false),
                reservation.toString()
        );
    }

    @Test
    void be_able_to_check_outbound_flight_departure_is_after_booking_time_before_canceling() {
        Reservation reservation = new Reservation(getMinTimeFlight(), Flight.EMPTY, getPassenger(), 1);

        assertThrows(TimeException.class, () -> reservation.checkBeforeCanceling(true));
    }

    @Test
    void throw_exception_when_outbound_flight_is_empty() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Reservation(Flight.EMPTY, getMaxTimeFlight(), getPassenger(), 1),
                "Outbound Flight cannot be empty."
        );
    }

    @Test
    void not_throw_anything_when_return_flight_is_empty() {
        assertDoesNotThrow(() -> new Reservation(getMinTimeFlight(), Flight.EMPTY, getPassenger(), 1));
    }

    @Test
    void throw_time_exception_when_outbound_flight_is_after_return_flight_in_round_trip_reservations() {
        assertThrows(
                TimeException.class,
                () -> new Reservation(getMaxTimeFlight(), getMinTimeFlight(), getPassenger(), 1)
        );
    }

    @Test
    void not_throw_time_exception_when_outbound_flight_is_before_return_flight() {
        assertDoesNotThrow(() -> new Reservation(getMinTimeFlight(), getMinTimeFlight(), getPassenger(), 1));
    }

    @Test
    void throw_exception_if_number_of_tickets_is_zero() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Reservation(getMinTimeFlight(), getMaxTimeFlight(), getPassenger(), 0),
                "Tickets cannot be zero or negative."
        );
    }

    @Test
    void throw_exception_if_number_of_tickets_is_negative() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Reservation(getMinTimeFlight(), getMaxTimeFlight(), getPassenger(), -10),
                "Tickets cannot be zero or negative."
        );
    }

    @Test
    void be_created() {
        Flight outboundFlight = getTwoDaysLaterFlight();
        Flight returnFlight = getMaxTimeFlight();
        Reservation reservation = new Reservation(outboundFlight, returnFlight, getPassenger(), 1);

        assertEquals(outboundFlight, reservation.getOutboundFlight());
        assertEquals(returnFlight, reservation.getReturnFlight());
        assertEquals(getPassenger(), reservation.getPassenger());
        assertEquals(1, reservation.getTicket());
        assertEquals("Reservation:" +
                " [Passenger: 1234567890 | Outbound Flight: 555 | Return Flight: 111 | Ticket: 1]", reservation.toString());
    }

    @Test
    void be_equal_to_another_reservation_with_the_same_information() {
        Flight outboundFlight = getTwoDaysLaterFlight();
        Flight returnFlight = getMaxTimeFlight();
        Reservation reservation_1 = new Reservation(outboundFlight, returnFlight, getPassenger(), 1);
        Reservation reservation_2 = new Reservation(outboundFlight, returnFlight, getPassenger(), 1);

        assertEquals(reservation_1, reservation_2);
    }

    private Flight getMaxTimeFlight() {
        return new Flight("111", TEHRAN, MASHHAD, LocalDateTime.MAX, 80, 65);
    }

    private Flight getMinTimeFlight() {
        return new Flight("333", MASHHAD, TEHRAN, LocalDateTime.MIN, 90, 50);
    }

    private Flight getTwoDaysLaterFlight() {
        return new Flight("555", MASHHAD, TEHRAN, LocalDateTime.now().plusDays(2), 100, 70);
    }

    private Passenger getPassenger() {
        String nationalCode = "1234567890";
        String firstName = "Farnam";
        String lastName = "Hasanzadeh";
        LocalDate birthday = of(2004, 1, 17);
        String phoneNumber = "09123456789";
        String address = "Rasht, Guilan, Iran";
        return new Passenger(nationalCode, firstName, lastName, MALE, birthday, phoneNumber, address);
    }
}
