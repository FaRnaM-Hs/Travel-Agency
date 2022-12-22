package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Flight {

    public static final Flight EMPTY = new Flight("Empty", City.EMPTY, City.EMPTY, LocalDateTime.MIN, 0, 0);

    private final String flightNumber;
    private final City origin;
    private final City destination;
    private final LocalDateTime departure;
    private final double price;
    private final int seatsLeft;

    public Flight(String flightNumber, City origin, City destination, LocalDateTime departure, double price, int seatsLeft) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departure = departure;
        this.price = price;
        this.seatsLeft = seatsLeft;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public City getOrigin() {
        return origin;
    }

    public City getDestination() {
        return destination;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public double getPrice() {
        return price;
    }

    public int getSeatsLeft() {
        return seatsLeft;
    }

    public boolean isEmpty() {
        return this.equals(EMPTY);
    }

    @Override
    public String toString() {
        return "Flight: " + flightNumber
                + " | " + "Origin: " + origin.name
                + " | " + "Destination: " + destination.name
                + " | " + "Departure: " + departure.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(flightNumber, flight.flightNumber)
                && origin == flight.origin
                && destination == flight.destination
                && Objects.equals(departure, flight.departure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNumber, origin, destination, departure);
    }
}
