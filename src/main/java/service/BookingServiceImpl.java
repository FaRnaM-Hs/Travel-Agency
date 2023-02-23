package service;

import dao.database.BookingDAO;
import dao.database.FlightDAO;
import model.Flight;
import model.Reservation;

import java.util.List;

public class BookingServiceImpl implements BookingService {

    private final BookingDAO bookingDAO;
    private final FlightDAO flightDAO;

    public BookingServiceImpl(BookingDAO bookingDAO, FlightDAO flightDAO) {
        this.bookingDAO = bookingDAO;
        this.flightDAO = flightDAO;
    }

    @Override
    public void book(Reservation reservation) {
        reservation.checkBeforeBooking(isExists(reservation.getOutboundFlight()), isExists(reservation.getReturnFlight()));

        decreaseFlightsSeatsLeft(reservation);

        if (isExists(reservation)) {
            increaseTicket(reservation);
        } else {
            bookingDAO.book(reservation);
        }
    }

    @Override
    public void cancel(Reservation reservation) {
        reservation.checkBeforeCanceling(isExists(reservation));

        bookingDAO.cancel(
                reservation.getPassenger().getNationalCode(),
                reservation.getOutboundFlight().getFlightNumber(),
                reservation.getReturnFlight().getFlightNumber()
        );
        increaseFlightsSeatsLeft(reservation);
    }

    @Override
    public List<Reservation> findByNationalCode(String nationalCodeKey) {
        return bookingDAO.findByNationalCode(nationalCodeKey);
    }

    @Override
    public List<Reservation> findByName(String firstNameKey, String lastNameKey) {
        return bookingDAO.findByName(firstNameKey, lastNameKey);
    }

    @Override
    public List<Reservation> findByFlight(String flightNumber) {
        return bookingDAO.findByFlight(flightNumber);
    }

    private boolean isExists(Reservation reservation) {
        return bookingDAO.isExists(
                reservation.getPassenger().getNationalCode(),
                reservation.getOutboundFlight().getFlightNumber(),
                reservation.getReturnFlight().getFlightNumber()
        );
    }

    private boolean isExists(Flight flight) {
        return flight.isEmpty() || flightDAO.isExists(flight.getFlightNumber());
    }

    private void increaseTicket(Reservation reservation) {
        bookingDAO.increaseTicket(
                reservation.getPassenger().getNationalCode(),
                reservation.getOutboundFlight().getFlightNumber(),
                reservation.getReturnFlight().getFlightNumber(),
                reservation.getTicket()
        );
    }

    private void decreaseFlightsSeatsLeft(Reservation reservation) {
        flightDAO.decreaseSeatsLeft(reservation.getOutboundFlight().getFlightNumber(), reservation.getTicket());
        if (reservation.isRoundTrip())
            flightDAO.decreaseSeatsLeft(reservation.getReturnFlight().getFlightNumber(), reservation.getTicket());
    }

    private void increaseFlightsSeatsLeft(Reservation reservation) {
        flightDAO.increaseSeatsLeft(reservation.getOutboundFlight().getFlightNumber(), reservation.getTicket());
        if (reservation.isRoundTrip())
            flightDAO.increaseSeatsLeft(reservation.getReturnFlight().getFlightNumber(), reservation.getTicket());
    }
}