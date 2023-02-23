package service;

import model.Reservation;

import java.util.List;

public interface BookingService {

    void book(Reservation reservation);

    void cancel(Reservation reservation);

    List<Reservation> findByNationalCode(String nationalCodeKey);

    List<Reservation> findByName(String firstNameKey, String lastNameKey);

    List<Reservation> findByFlight(String flightNumber);
}