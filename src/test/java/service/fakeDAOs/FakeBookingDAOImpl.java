package service.fakeDAOs;

import dao.database.BookingDAO;
import model.Flight;
import model.Passenger;
import model.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FakeBookingDAOImpl implements BookingDAO {

    private final List<Reservation> reservations;

    public FakeBookingDAOImpl() {
        this.reservations = new ArrayList<>();
    }

    @Override
    public void book(Reservation reservation) {
        reservations.add(reservation);
    }

    @Override
    public void cancel(String nationalCode, String outboundFlightNumber, String returnFlightNumber) {
        Reservation reservation = getReservation(nationalCode, outboundFlightNumber, returnFlightNumber);

        reservations.remove(reservation);
    }

    @Override
    public void increaseTicket(String nationalCode, String outboundFlightNumber, String returnFlightNumber, int amount) {
        Reservation reservation = getReservation(nationalCode, outboundFlightNumber, returnFlightNumber);
        Flight outboundFlight = reservation.getOutboundFlight();
        Flight returnFlight = reservation.getReturnFlight();
        Passenger passenger = reservation.getPassenger();
        int ticket = reservation.getTicket();

        reservations.remove(reservation);
        reservations.add(new Reservation(outboundFlight, returnFlight, passenger, ticket + amount));
    }

    @Override
    public List<Reservation> findByNationalCode(String nationalCodeKey) {
        return reservations.stream()
                .filter(reservation -> reservation.getPassenger().getNationalCode().contains(nationalCodeKey))
                .toList();
    }

    @Override
    public List<Reservation> findByName(String firstNameKey, String lastNameKey) {
        return reservations.stream()
                .filter(reservation -> reservation.getPassenger().getFirstName().contains(firstNameKey))
                .filter(reservation -> reservation.getPassenger().getLastName().contains(lastNameKey))
                .toList();
    }

    @Override
    public List<Reservation> findByFlight(String flightNumber) {
        return reservations.stream()
                .filter(reservation -> reservation.getOutboundFlight().getFlightNumber().contains(flightNumber) ||
                        (reservation.isRoundTrip() && reservation.getReturnFlight().getFlightNumber().contains(flightNumber)))
                .toList();
    }

    @Override
    public boolean isExists(String nationalCode, String outboundFlightNumber, String returnFlightNumber) {
        return reservations.stream()
                .anyMatch(reservation -> isQulified(reservation, nationalCode, outboundFlightNumber, returnFlightNumber));
    }

    private Reservation getReservation(String nationalCode, String outboundFlightNumber, String returnFlightNumber) {
        return reservations.stream()
                .filter(reservation -> isQulified(reservation, nationalCode, outboundFlightNumber, returnFlightNumber))
                .findFirst().get();
    }


    private boolean isQulified(Reservation reservation, String nationalCode, String outboundFlightNumber, String returnFlightNumber) {
        return reservation.getPassenger().getNationalCode().equals(nationalCode)
                && reservation.getOutboundFlight().getFlightNumber().equals(outboundFlightNumber)
                && Objects.equals(reservation.getReturnFlight().getFlightNumber(), returnFlightNumber);
    }
}