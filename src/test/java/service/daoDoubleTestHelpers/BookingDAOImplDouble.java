package service.daoDoubleTestHelpers;

import dao.database.BookingDAO;
import model.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class BookingDAOImplDouble implements BookingDAO {

    private final List<Reservation> reservations;

    public BookingDAOImplDouble() {
        this.reservations = new ArrayList<>();
    }

    @Override
    public void book(Reservation reservation) {
        reservations.add(reservation);
    }

    @Override
    public void cancel(String nationCode, String outboundFlightNumber) {

    }

    @Override
    public void cancel(String nationCode, String outboundFlightNumber, String returnFlightNumber) {

    }

    @Override
    public void increaseNumberOfTickets(String nationCode, String outboundFlightNumber, int amount) {

    }

    @Override
    public void increaseNumberOfTickets(String nationCode, String outboundFlightNumber, String returnFlightNumber, int amount) {

    }

    @Override
    public Optional<Reservation> get(String nationCode, String outboundFlightNumber) {
        return reservations.stream()
                .filter(reservation -> isQualified(reservation, nationCode, outboundFlightNumber))
                .findFirst();

    }

    @Override
    public Optional<Reservation> get(String nationCode, String outboundFlightNumber, String returnFlightNumber) {
        return reservations.stream()
                .filter(reservation -> isQualified(reservation, nationCode, outboundFlightNumber, returnFlightNumber))
                .findFirst();
    }

    @Override
    public List<Reservation> searchByNationCode(String nationCodeKey) {
        return reservations.stream()
                .filter(reservation -> reservation.getPassenger().getNationCode().contains(nationCodeKey))
                .toList();
    }

    @Override
    public List<Reservation> searchByName(String firstNameKey, String lastNameKey) {
        return reservations.stream()
                .filter(reservation -> reservation.getPassenger().getFirstName().contains(firstNameKey)
                        && reservation.getPassenger().getLastName().contains(lastNameKey))
                .toList();
    }

    @Override
    public List<Reservation> searchByFlight(String flightNumber) {
        return reservations.stream()
                .filter(reservation -> reservation.getOutboundFlight().getFlightNumber().contains(flightNumber)
                        || reservation.getReturnFlight().getFlightNumber().contains(flightNumber))
                .toList();
    }

    @Override
    public boolean isExists(String nationCode, String outboundFlightNumber) {
        return reservations.stream()
                .filter(Predicate.not(Reservation::isRoundTrip))
                .anyMatch(reservation -> isQualified(reservation, nationCode, outboundFlightNumber));
    }

    @Override
    public boolean isExists(String nationCode, String outboundFlightNumber, String returnFlightNumber) {
        return reservations.stream()
                .filter(Reservation::isRoundTrip)
                .anyMatch(reservation -> isQualified(reservation, nationCode, outboundFlightNumber, returnFlightNumber));
    }

    private boolean isQualified(Reservation reservation, String nationCode, String outboundFlightNumber) {
        return !reservation.isRoundTrip()
                && reservation.getPassenger().getNationCode().equals(nationCode)
                && reservation.getOutboundFlight().getFlightNumber().equals(outboundFlightNumber);
    }

    private boolean isQualified(Reservation reservation, String nationCode, String outboundFlightNumber, String returnFlightNumber) {
        return reservation.isRoundTrip()
                && reservation.getPassenger().getNationCode().equals(nationCode)
                && reservation.getOutboundFlight().getFlightNumber().equals(outboundFlightNumber)
                && reservation.getReturnFlight().getFlightNumber().equals(returnFlightNumber);
    }
}
