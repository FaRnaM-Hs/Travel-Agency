package service;

import dao.database.BookingDAO;
import dao.database.FlightDAO;
import exceptions.ReservationNotFoundException;
import exceptions.NotEnoughSeatsException;
import exceptions.TimeException;
import model.Reservation;

import java.time.LocalDateTime;
import java.util.List;

public class BookingServiceImpl implements BookingService {

    private final BookingDAO bookingDAO;
    private final FlightService flightService;

    public BookingServiceImpl(BookingDAO bookingDAO, FlightDAO flightDAO) {
        this.bookingDAO = bookingDAO;
        this.flightService = new FlightServiceImpl(flightDAO);
    }

    @Override
    public void book(Reservation reservation) {
        check(reservation);
        decreaseSeatsLeft(reservation);
        if (isExists(reservation)) {
            increaseNumberOfTickets(reservation);
        } else {
            bookingDAO.book(reservation);
        }
    }

    @Override
    public void cancel(Reservation reservation) {
        String nationCode = reservation.getPassenger().getNationCode();
        String outboundFlightNumber = reservation.getOutboundFlight().getFlightNumber();

        if (reservation.isRoundTrip()) {
            String returnFlightNumber = reservation.getReturnFlight().getFlightNumber();
            cancel(nationCode, outboundFlightNumber, returnFlightNumber);
        }
        else {
            cancel(nationCode, outboundFlightNumber);
        }
    }

    @Override
    public void cancel(String nationCode, String outboundFlightNumber) {
        Reservation reservation = get(nationCode, outboundFlightNumber);
        checkTime(reservation);

        bookingDAO.cancel(nationCode, outboundFlightNumber);
        flightService.increaseSeatsLeft(outboundFlightNumber, reservation.getNumberOfTickets());
    }

    @Override
    public void cancel(String nationCode, String outboundFlightNumber, String returnFlightNumber) {
        Reservation reservation = get(nationCode, outboundFlightNumber, returnFlightNumber);
        checkTime(reservation);

        bookingDAO.cancel(nationCode, outboundFlightNumber, returnFlightNumber);
        flightService.increaseSeatsLeft(outboundFlightNumber, reservation.getNumberOfTickets());
        flightService.increaseSeatsLeft(returnFlightNumber, reservation.getNumberOfTickets());
    }

    @Override
    public Reservation get(String nationCode, String outboundFlightNumber) {
        return bookingDAO.get(nationCode, outboundFlightNumber)
                .orElseThrow(() -> new ReservationNotFoundException(
                        "NC: " + nationCode + ", OF: " + outboundFlightNumber
                ));
    }

    @Override
    public Reservation get(String nationCode, String outboundFlightNumber, String returnFlightNumber) {
        return bookingDAO.get(nationCode, outboundFlightNumber, returnFlightNumber)
                .orElseThrow(() -> new ReservationNotFoundException(
                        "NC: " + nationCode + ", OF: " + outboundFlightNumber + ", RF" + returnFlightNumber
                ));
    }

    @Override
    public List<Reservation> searchByNationCode(String nationCodeKey) {
        return bookingDAO.searchByNationCode(nationCodeKey);
    }

    @Override
    public List<Reservation> searchByName(String firstNameKey, String lastNameKey) {
        return bookingDAO.searchByName(firstNameKey, lastNameKey);
    }

    @Override
    public List<Reservation> searchByFlight(String flightNumber) {
        return bookingDAO.searchByFlight(flightNumber);
    }

    @Override
    public boolean isExists(Reservation reservation) {
        String nationCode = reservation.getPassenger().getNationCode();
        String outboundFlightNumber = reservation.getOutboundFlight().getFlightNumber();
        String returnFlightNumber = reservation.getReturnFlight().getFlightNumber();

        return (reservation.isRoundTrip())
                ? isExists(nationCode, outboundFlightNumber, returnFlightNumber)
                : isExists(nationCode, outboundFlightNumber);
    }

    @Override
    public boolean isExists(String nationCode, String outboundFlightNumber) {
        return bookingDAO.isExists(nationCode, outboundFlightNumber);
    }

    @Override
    public boolean isExists(String nationCode, String outboundFlightNumber, String returnFlightNumber) {
        return bookingDAO.isExists(nationCode, outboundFlightNumber, returnFlightNumber);
    }

    private void increaseNumberOfTickets(Reservation reservation) {
        String nationCode = reservation.getPassenger().getNationCode();
        String outboundNumber = reservation.getOutboundFlight().getFlightNumber();
        int numberOfTickets = reservation.getNumberOfTickets();

        if (reservation.isRoundTrip()) {
            String returnNumber = reservation.getReturnFlight().getFlightNumber();
            increaseNumberOfTickets(nationCode, outboundNumber, returnNumber, numberOfTickets);
        }
        else {
            increaseNumberOfTickets(nationCode, outboundNumber, numberOfTickets);
        }
    }

    private void increaseNumberOfTickets(String nationCode, String outboundFlightNumber, int amount) {
        bookingDAO.increaseNumberOfTickets(nationCode, outboundFlightNumber, amount);
    }


    private void increaseNumberOfTickets(String nationCode, String outboundFlightNumber, String returnFlightNumber, int amount) {
        bookingDAO.increaseNumberOfTickets(nationCode, outboundFlightNumber, returnFlightNumber, amount);
    }

    private void decreaseSeatsLeft(Reservation reservation) {
        String outboundFlightNumber = reservation.getOutboundFlight().getFlightNumber();
        int numberOfTickets = reservation.getNumberOfTickets();

        flightService.decreaseSeatsLeft(outboundFlightNumber, numberOfTickets);
        if (reservation.isRoundTrip()) {
            String returnFlightNumber = reservation.getReturnFlight().getFlightNumber();
            flightService.decreaseSeatsLeft(returnFlightNumber, numberOfTickets);
        }
    }

    private void check(Reservation reservation) {
        checkTime(reservation);
        checkFlightsTime(reservation);
        checkNumberOfTickets(reservation);
        checkSeatsLeft(reservation);
    }

    private void checkTime(Reservation reservation) {
        if (LocalDateTime.now().plusHours(2).isAfter(reservation.getOutboundFlight().getDeparture()))
            throw new TimeException();
    }

    private void checkFlightsTime(Reservation reservation) {
        if (reservation.isRoundTrip() &&
                reservation.getOutboundFlight().getDeparture().isAfter(reservation.getReturnFlight().getDeparture()))
            throw new TimeException();
    }

    private void checkNumberOfTickets(Reservation reservation) {
        if (reservation.getNumberOfTickets() <= 0)
            throw new IllegalArgumentException("Tickets cannot be zero or negative");
    }

    private void checkSeatsLeft(Reservation reservation) {
        if (reservation.getOutboundFlight().getSeatsLeft() < reservation.getNumberOfTickets())
            throw new NotEnoughSeatsException(reservation.getOutboundFlight().getFlightNumber());
        if (reservation.isRoundTrip() && reservation.getReturnFlight().getSeatsLeft() < reservation.getNumberOfTickets())
            throw new NotEnoughSeatsException(reservation.getReturnFlight().getFlightNumber());
    }
}