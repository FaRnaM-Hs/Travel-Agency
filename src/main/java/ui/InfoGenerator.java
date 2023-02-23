package ui;

import model.Flight;
import model.Passenger;
import model.Reservation;

import java.text.NumberFormat;

public class InfoGenerator {

    public String generateFlightInfo(Flight flight) {
        return getFlightInfo(flight);
    }

    public String generateReservationInfo(Reservation reservation) {
        String resType = getReservationTypeInfo(reservation);
        String noTickets = getTicketsInfo(reservation);
        String outInf = "Outbound " + getFlightInfo(reservation.getOutboundFlight());
        String retInf = reservation.isRoundTrip() ? "Return " + getFlightInfo(reservation.getReturnFlight()) : "";
        String passInf = getPassengerInfo(reservation.getPassenger());

        return resType + noTickets + outInf + retInf + passInf;
    }

    private String getReservationTypeInfo(Reservation reservation) {
        return reservation.isRoundTrip() ? "Round Trip Reservation\n" : "One Way Reservation\n";
    }

    private String getTicketsInfo(Reservation reservation) {
        int numberOfTickets = reservation.getTicket();
        return String.format("   %d Ticket%s\n\n", numberOfTickets, numberOfTickets > 1 ? "s" : "");
    }

    private String getFlightInfo(Flight flight) {
        return String.format("""
                        Flight:
                           Flight Number: %s
                           Origin: %s
                           Destination: %s
                           Departure: %s
                           Price: %s Rial

                        """,
                        flight.getFlightNumber(),
                        flight.getOrigin().name,
                        flight.getDestination().name,
                        flight.getDeparture().toString().replace("T", " "),
                        NumberFormat.getInstance().format(flight.getPrice()));
    }

    private String getPassengerInfo(Passenger passenger) {
        return String.format("""
                        Passenger:
                           First Name: %s
                           Last Name: %s
                           National Code: %s
                           Gender: %s
                           Birthday: %s
                           Phone Number: %s
                           Address: %s

                        """,
                passenger.getFirstName(),
                passenger.getLastName(),
                passenger.getNationalCode(),
                passenger.getGender().name,
                passenger.getBirthday().toString(),
                passenger.getPhoneNumber(),
                passenger.getAddress());
    }
}
