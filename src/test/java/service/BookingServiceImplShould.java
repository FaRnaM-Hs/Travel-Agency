package service;

import dao.database.FlightDAO;
import exceptions.FlightNotFoundException;
import exceptions.NotEnoughSeatsException;
import exceptions.ReservationNotFoundException;
import exceptions.TimeException;
import model.Flight;
import model.Passenger;
import model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.fakeDAOs.FakeBookingDAOImpl;
import service.fakeDAOs.FakeFlightDAOImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static java.time.LocalDate.of;
import static model.City.*;
import static model.Gender.MALE;
import static org.junit.jupiter.api.Assertions.*;

public class BookingServiceImplShould {

    private BookingService bookingService;
    private FlightDAO flightDAO;

    @BeforeEach
    void setUp() {
        flightDAO = new FakeFlightDAOImpl();
        bookingService = new BookingServiceImpl(new FakeBookingDAOImpl(), flightDAO);
    }

    @Test
    void throw_exception_if_outbound_flight_does_not_exist_in_the_booking() {
        Reservation reservation = new Reservation(getNonExistenceFlight(), Flight.EMPTY, getPassenger_1(), 1);

        assertThrows(
                FlightNotFoundException.class,
                () -> bookingService.book(reservation),
                getNonExistenceFlight().toString()
        );
    }

    @Test
    void throw_exception_if_return_flight_does_not_exist_in_the_booking() {
        Reservation reservation = new Reservation(flightDAO.get("999").get(), getNonExistenceFlight(), getPassenger_1(), 1);

        assertThrows(
                FlightNotFoundException.class,
                () -> bookingService.book(reservation),
                getNonExistenceFlight().toString()
        );
    }

    @Test
    void throw_exception_if_booking_time_is_after_outbound_flight_departure() {
        Reservation reservation = new Reservation(flightDAO.get("444").get(), flightDAO.get("888").get(), getPassenger_1(), 1);

        assertThrows(TimeException.class, () -> bookingService.book(reservation));
    }

    @Test
    void throw_exception_if_tickets_are_more_than_outbound_flight_capacity_in_one_way_reservations() {
        Reservation reservation = new Reservation(flightDAO.get("333").get(), Flight.EMPTY, getPassenger_1(), 11);

        assertThrows(
                NotEnoughSeatsException.class,
                () -> bookingService.book(reservation),
                reservation.getOutboundFlight().toString()
        );
    }

    @Test
    void throw_exception_if_tickets_are_more_than_outbound_flight_capacity_in_round_trip_reservations() {
        Reservation reservation = new Reservation(flightDAO.get("333").get(), flightDAO.get("888").get(), getPassenger_1(), 11);

        assertThrows(
                NotEnoughSeatsException.class,
                () -> bookingService.book(reservation),
                reservation.getOutboundFlight().toString()
        );
    }

    @Test
    void throw_exception_if_tickets_are_more_than_return_flight_capacity_in_round_trip_reservations() {
        Reservation reservation = new Reservation(flightDAO.get("555").get(), flightDAO.get("888").get(), getPassenger_1(), 71);

        assertThrows(
                NotEnoughSeatsException.class,
                () -> bookingService.book(reservation),
                reservation.getReturnFlight().toString()
        );
    }

    @Test
    void decrease_only_outbound_flight_seats_left_for_one_way_reservations_in_the_booking() {
        Reservation reservation = new Reservation(flightDAO.get("111").get(), Flight.EMPTY, getPassenger_1(), 1);

        bookingService.book(reservation);

        assertEquals(64, flightDAO.get("111").get().getSeatsLeft());
    }

    @Test
    void decrease_both_outbound_and_return_flight_seats_left_for_round_trip_reservations_in_the_booking() {
        Reservation reservation = new Reservation(flightDAO.get("111").get(), flightDAO.get("888").get(), getPassenger_1(), 1);

        bookingService.book(reservation);

        assertEquals(64, flightDAO.get("111").get().getSeatsLeft());
        assertEquals(69, flightDAO.get("888").get().getSeatsLeft());
    }

    @Test
    void increase_tickets_of_the_one_way_reservation_if_the_reservation_exists_in_the_booking() {
        Reservation reservation_1 = new Reservation(flightDAO.get("111").get(), Flight.EMPTY, getPassenger_1(), 2);
        Reservation reservation_2 = new Reservation(flightDAO.get("111").get(), Flight.EMPTY, getPassenger_1(), 1);

        bookingService.book(reservation_1);
        bookingService.book(reservation_2);

        List<Reservation> reservations = bookingService.findByNationalCode("1234567890");
        assertEquals(1, reservations.size());
        assertEquals(3, reservations.get(0).getTicket());
    }

    @Test
    void increase_tickets_of_the_round_trip_reservation_if_the_reservation_exists_in_the_booking() {
        Reservation reservation_1 = new Reservation(flightDAO.get("111").get(), flightDAO.get("888").get(), getPassenger_1(), 2);
        Reservation reservation_2 = new Reservation(flightDAO.get("111").get(), flightDAO.get("888").get(), getPassenger_1(), 1);

        bookingService.book(reservation_1);
        bookingService.book(reservation_2);

        List<Reservation> reservations = bookingService.findByNationalCode("1234567890");
        assertEquals(1, reservations.size());
        assertEquals(3, reservations.get(0).getTicket());
    }

    @Test
    void book_new_one_way_reservation_if_the_reservation_does_not_exist() {
        Reservation reservation_1 = new Reservation(flightDAO.get("111").get(), flightDAO.get("888").get(), getPassenger_1(), 1);
        Reservation reservation_2 = new Reservation(flightDAO.get("888").get(), Flight.EMPTY, getPassenger_1(), 1);

        bookingService.book(reservation_1);
        bookingService.book(reservation_2);

        List<Reservation> reservations = bookingService.findByNationalCode("1234567890");
        assertEquals(2, reservations.size());
        assertEquals(reservation_1, reservations.get(0));
        assertEquals(reservation_2, reservations.get(1));
    }

    @Test
    void book_round_trip_reservation_if_the_reservation_does_not_exist() {
        Reservation reservation_1 = new Reservation(flightDAO.get("888").get(), Flight.EMPTY, getPassenger_1(), 1);
        Reservation reservation_2 = new Reservation(flightDAO.get("111").get(), flightDAO.get("888").get(), getPassenger_1(), 1);

        bookingService.book(reservation_1);
        bookingService.book(reservation_2);

        List<Reservation> reservations = bookingService.findByNationalCode("1234567890");
        assertEquals(2, reservations.size());
        assertEquals(reservation_1, reservations.get(0));
        assertEquals(reservation_2, reservations.get(1));
    }

    @Test
    void throw_exception_before_canceling_reservation_if_it_does_not_exist() {
        Reservation reservation = new Reservation(flightDAO.get("111").get(), flightDAO.get("888").get(), getPassenger_1(), 1);

        assertThrows(
                ReservationNotFoundException.class,
                () -> bookingService.cancel(reservation),
                reservation.toString()
        );
    }

    @Test
    void cancel_and_remove_one_way_reservation() {
        Reservation reservation_1 = new Reservation(flightDAO.get("111").get(), Flight.EMPTY, getPassenger_1(), 1);
        Reservation reservation_2 = new Reservation(flightDAO.get("111").get(), flightDAO.get("888").get(), getPassenger_1(), 1);

        bookingService.book(reservation_1);
        bookingService.book(reservation_2);
        bookingService.cancel(reservation_1);

        List<Reservation> reservations = bookingService.findByNationalCode("1234567890");
        assertEquals(1, reservations.size());
        assertEquals(reservation_2, reservations.get(0));
    }

    @Test
    void cancel_and_remove_round_trip_reservation() {
        Reservation reservation_1 = new Reservation(flightDAO.get("111").get(), flightDAO.get("888").get(), getPassenger_1(), 1);
        Reservation reservation_2 = new Reservation(flightDAO.get("111").get(), Flight.EMPTY, getPassenger_1(), 1);

        bookingService.book(reservation_1);
        bookingService.book(reservation_2);
        bookingService.cancel(reservation_1);

        List<Reservation> reservations = bookingService.findByNationalCode("1234567890");
        assertEquals(1, reservations.size());
        assertEquals(reservation_2, reservations.get(0));
    }

    @Test
    void increase_only_outbound_flight_seats_left_for_one_way_reservations_in_the_canceling() {
        Reservation reservation = new Reservation(flightDAO.get("111").get(), Flight.EMPTY, getPassenger_1(), 1);

        bookingService.book(reservation);
        bookingService.cancel(reservation);

        assertEquals(65, flightDAO.get("111").get().getSeatsLeft());
    }

    @Test
    void increase_both_outbound_and_return_flight_seats_left_for_round_trip_reservations_in_the_canceling() {
        Reservation reservation = new Reservation(flightDAO.get("111").get(), flightDAO.get("888").get(), getPassenger_1(), 1);

        bookingService.book(reservation);
        bookingService.cancel(reservation);

        assertEquals(65, flightDAO.get("111").get().getSeatsLeft());
        assertEquals(70, flightDAO.get("888").get().getSeatsLeft());
    }

    @Test
    void find_reservations_by_matching_passengers_national_code() {
        Reservation reservation_1 = new Reservation(flightDAO.get("111").get(), flightDAO.get("888").get(), getPassenger_1(), 1);
        Reservation reservation_2 = new Reservation(flightDAO.get("111").get(), Flight.EMPTY, getPassenger_1(), 1);
        Reservation reservation_3 = new Reservation(flightDAO.get("888").get(), Flight.EMPTY, getPassenger_1(), 1);
        Reservation reservation_4 = new Reservation(flightDAO.get("999").get(), Flight.EMPTY, getPassenger_2(), 1);
        Reservation reservation_5 = new Reservation(flightDAO.get("555").get(), flightDAO.get("888").get(), getPassenger_2(), 1);

        bookingService.book(reservation_1);
        bookingService.book(reservation_2);
        bookingService.book(reservation_3);
        bookingService.book(reservation_4);
        bookingService.book(reservation_5);

        List<Reservation> reservations = bookingService.findByNationalCode("1234567890");
        assertEquals(3, reservations.size());
        assertEquals(reservation_1, reservations.get(0));
        assertEquals(reservation_2, reservations.get(1));
        assertEquals(reservation_3, reservations.get(2));
    }

    @Test
    void find_reservations_by_matching_passengers_first_and_last_name() {
        Reservation reservation_1 = new Reservation(flightDAO.get("111").get(), flightDAO.get("888").get(), getPassenger_1(), 1);
        Reservation reservation_2 = new Reservation(flightDAO.get("111").get(), Flight.EMPTY, getPassenger_1(), 1);
        Reservation reservation_3 = new Reservation(flightDAO.get("888").get(), Flight.EMPTY, getPassenger_1(), 1);
        Reservation reservation_4 = new Reservation(flightDAO.get("999").get(), Flight.EMPTY, getPassenger_2(), 1);
        Reservation reservation_5 = new Reservation(flightDAO.get("555").get(), flightDAO.get("888").get(), getPassenger_2(), 1);

        bookingService.book(reservation_1);
        bookingService.book(reservation_2);
        bookingService.book(reservation_3);
        bookingService.book(reservation_4);
        bookingService.book(reservation_5);

        List<Reservation> reservations = bookingService.findByName("Ali", "Daei");
        assertEquals(2, reservations.size());
        assertEquals(reservation_4, reservations.get(0));
        assertEquals(reservation_5, reservations.get(1));
    }

    @Test
    void find_reservations_by_matching_outbound_or_return_flight_numbers() {
        Reservation reservation_1 = new Reservation(flightDAO.get("111").get(), flightDAO.get("888").get(), getPassenger_1(), 1);
        Reservation reservation_2 = new Reservation(flightDAO.get("111").get(), Flight.EMPTY, getPassenger_1(), 1);
        Reservation reservation_3 = new Reservation(flightDAO.get("888").get(), Flight.EMPTY, getPassenger_1(), 1);
        Reservation reservation_4 = new Reservation(flightDAO.get("999").get(), Flight.EMPTY, getPassenger_2(), 1);
        Reservation reservation_5 = new Reservation(flightDAO.get("555").get(), flightDAO.get("888").get(), getPassenger_2(), 1);

        bookingService.book(reservation_1);
        bookingService.book(reservation_2);
        bookingService.book(reservation_3);
        bookingService.book(reservation_4);
        bookingService.book(reservation_5);

        List<Reservation> reservations = bookingService.findByFlight("888");
        assertEquals(3, reservations.size());
        assertEquals(reservation_1, reservations.get(0));
        assertEquals(reservation_3, reservations.get(1));
        assertEquals(reservation_5, reservations.get(2));
    }

    private Flight getNonExistenceFlight() {
        LocalTime twelveOClock = LocalTime.of(12, 0, 0);
        return new Flight("100", AHVAZ, RASHT, LocalDateTime.of(LocalDate.now().plusDays(12), twelveOClock), 50, 2);
    }

    private Passenger getPassenger_1() {
        String nationalCode = "1234567890";
        String firstName = "Farnam";
        String lastName = "Hasanzadeh";
        LocalDate birthday = of(2004, 1, 17);
        String phoneNumber = "09123456789";
        String address = "Rasht, Guilan, Iran";
        return new Passenger(nationalCode, firstName, lastName, MALE, birthday, phoneNumber, address);
    }

    private Passenger getPassenger_2() {
        String nationalCode = "0987654321";
        String firstName = "Ali";
        String lastName = "Daei";
        LocalDate birthday = of(1969, 3, 21);
        String phoneNumber = "09101001010";
        String address = "Iran";
        return new Passenger(nationalCode, firstName, lastName, MALE, birthday, phoneNumber, address);
    }
}
