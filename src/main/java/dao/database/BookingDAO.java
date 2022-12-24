package dao.database;

import model.Reservation;

import java.util.List;
import java.util.Optional;

public interface BookingDAO {

    void book(Reservation reservation);

    void cancel(String nationCode, String outboundFlightNumber);

    void cancel(String nationCode, String outboundFlightNumber, String returnFlightNumber);

    void increaseNumberOfTickets(String nationCode, String outboundFlightNumber, int amount);

    void increaseNumberOfTickets(String nationCode, String outboundFlightNumber, String returnFlightNumber, int amount);

    Optional<Reservation> get(String nationCode, String outboundFlightNumber);

    Optional<Reservation> get(String nationCode, String outboundFlightNumber, String returnFlightNumber);

    List<Reservation> getAll();

    List<Reservation> searchByNationCode(String nationCodeKey);

    List<Reservation> searchByName(String firstNameKey, String lastNameKey);

    List<Reservation> searchByFlight(String flightNumber);

    boolean isExists(String nationCode, String outboundFlightNumber);

    boolean isExists(String nationCode, String outboundFlightNumber, String returnFlightNumber);

}
