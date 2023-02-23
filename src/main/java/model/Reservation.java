package model;

import exceptions.NotEnoughSeatsException;
import exceptions.ReservationNotFoundException;
import exceptions.TimeException;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reservation {

    private final Flight outboundFlight;
    private final Flight returnFlight;
    private final Passenger passenger;
    private final int ticket;

    public Reservation(Flight outboundFlight, Flight returnFlight, Passenger passenger, int ticket) {
        this.outboundFlight = outboundFlight;
        this.returnFlight = returnFlight;
        this.passenger = passenger;
        this.ticket = ticket;

        check();
    }

    public boolean isRoundTrip() {
        return !returnFlight.isEmpty();
    }

    public void checkBeforeBooking(boolean isOutboundFlightExists, boolean isReturnFlightExists) {
        this.outboundFlight.checkExistence(isOutboundFlightExists);
        this.returnFlight.checkExistence(isReturnFlightExists);
        checkTime();
        checkSeatsLeft();
    }

    public void checkBeforeCanceling(boolean isReservationExists) {
        checkExistence(isReservationExists);
        checkTime();
    }

    public Flight getOutboundFlight() {
        return outboundFlight;
    }

    public Flight getReturnFlight() {
        return returnFlight;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public int getTicket() {
        return ticket;
    }

    @Override
    public String toString() {
        return "Reservation: ["
                + "Passenger: " + passenger.getNationalCode()
                + " | " + "Outbound Flight: " + outboundFlight.getFlightNumber()
                + " | " + "Return Flight: " + returnFlight.getFlightNumber()
                + " | " + "Ticket: " + ticket
                + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return ticket == that.ticket
                && Objects.equals(outboundFlight, that.outboundFlight)
                && Objects.equals(returnFlight, that.returnFlight)
                && Objects.equals(passenger, that.passenger);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outboundFlight, returnFlight, passenger, ticket);
    }

    private void checkSeatsLeft() {
        if (this.outboundFlight.getSeatsLeft() < this.ticket)
            throw new NotEnoughSeatsException(this.outboundFlight.toString());
        if (this.isRoundTrip() && this.returnFlight.getSeatsLeft() < this.ticket)
            throw new NotEnoughSeatsException(this.returnFlight.toString());
    }

    private void checkTime() {
        if (LocalDateTime.now().isAfter(this.getOutboundFlight().getDeparture()))
            throw new TimeException();
    }

    private void checkExistence(boolean isExists) {
        if (!isExists)
            throw new ReservationNotFoundException(this.toString());
    }

    private void check() {
        checkOutboundFlight();
        checkFlightsTime();
        checkTicketAmount();
    }

    private void checkOutboundFlight() {
        if (this.outboundFlight.isEmpty())
            throw new IllegalArgumentException("Outbound Flight cannot be empty.");
    }

    private void checkFlightsTime() {
        if (this.isRoundTrip() && this.outboundFlight.getDeparture().isAfter(this.returnFlight.getDeparture()))
            throw new TimeException();
    }

    private void checkTicketAmount() {
        if (this.ticket <= 0)
            throw new IllegalArgumentException("Tickets cannot be zero or negative.");
    }
}