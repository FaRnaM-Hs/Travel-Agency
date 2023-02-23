package model;

import exceptions.FlightNotFoundException;

import java.time.LocalDateTime;
import java.util.Objects;

public class Flight {

    public static final Flight EMPTY = new Flight(null, null, null, null, -1, -1);

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

        check();
    }

    public boolean isEmpty() {
        return this.flightNumber == null
                && this.origin == null
                && this.destination == null
                && this.departure == null
                && this.price == -1
                && this.seatsLeft == -1;
    }

    public void checkExistence(boolean isExists) {
        if (!this.isEmpty() && !isExists)
            throw new FlightNotFoundException(this.toString());
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
        return Double.compare(flight.price, price) == 0
                && seatsLeft == flight.seatsLeft
                && Objects.equals(flightNumber, flight.flightNumber)
                && origin == flight.origin
                && destination == flight.destination
                && Objects.equals(departure, flight.departure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNumber, origin, destination, departure, price, seatsLeft);
    }

    private void check() {
        if (!this.isEmpty()) {
            checkNumber();
            checkCities();
            checkPrice();
            checkSeatsLeft();
        }
    }

    private void checkNumber() {
        if (!this.flightNumber.matches("[0-9]+"))
            throw new IllegalArgumentException("Flight Number must have only digits.");
    }

    private void checkCities() {
        if (this.origin.equals(this.destination))
            throw new IllegalArgumentException("Origin and Destination cannot be the same.");
    }

    private void checkPrice() {
        if (this.price < 0)
            throw new IllegalArgumentException("Price cannot be negative.");
    }

    private void checkSeatsLeft() {
        if (this.seatsLeft < 0)
            throw new IllegalArgumentException("Seats Left cannot be negative.");
    }
}