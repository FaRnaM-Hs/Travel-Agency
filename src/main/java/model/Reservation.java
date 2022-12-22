package model;

public class Reservation {

    private final Flight outboundFlight;
    private final Flight returnFlight;
    private final Passenger passenger;
    private final int numberOfTickets;

    public Reservation(Flight outboundFlight, Flight returnFlight, Passenger passenger, int numberOfTickets) {
        this.outboundFlight = outboundFlight;
        this.returnFlight = returnFlight;
        this.passenger = passenger;
        this.numberOfTickets = numberOfTickets;
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

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    @Override
    public String toString() {
        return "Reservation: ["
                + "Passenger: " + passenger.getNationCode()
                + " | " + "Outbound Flight: " + outboundFlight.getFlightNumber()
                + " | " + "Return Flight: " + returnFlight.getFlightNumber()
                + " | " + "Number of Tickets: " + numberOfTickets
                + "]";
    }
}
