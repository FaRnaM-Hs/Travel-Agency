package dao.database;

import model.Reservation;

import java.util.List;

public interface BookingDAO {

    void book(Reservation reservation);

    void cancel(String nationalCode, String outboundFlightNumber, String returnFlightNumber);

    void increaseTicket(String nationalCode, String outboundFlightNumber, String returnFlightNumber, int amount);

    List<Reservation> findByNationalCode(String nationalCodeKey);

    List<Reservation> findByName(String firstNameKey, String lastNameKey);

    List<Reservation> findByFlight(String flightNumber);

    boolean isExists(String nationalCode, String outboundFlightNumber, String returnFlightNumber);
}