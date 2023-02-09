package service;

import model.Reservation;

import java.util.List;

public interface BookingService {

    void book(Reservation reservation);

    void cancel(Reservation reservation);

    void cancel(String nationCode, String outboundFlightNumber);

    void cancel(String nationCode, String outboundFlightNumber, String returnFlightNumber);

    Reservation get(String nationCode, String outboundFlightNumber);

    Reservation get(String nationCode, String outboundFlightNumber, String returnFlightNumber);

    List<Reservation> searchByNationCode(String nationCodeKey);

    List<Reservation> searchByName(String firstNameKey, String lastNameKey);

    List<Reservation> searchByFlight(String flightNumber);

    boolean isExists(Reservation reservation);

    boolean isExists(String nationCode, String outboundFlightNumber);

    boolean isExists(String nationCode, String outboundFlightNumber, String returnFlightNumber);
}
