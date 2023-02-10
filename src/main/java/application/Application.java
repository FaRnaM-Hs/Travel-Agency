package application;

import dao.database.BookingDAO;
import dao.database.BookingDAOImpl;
import dao.database.FlightDAO;
import dao.database.FlightDAOImpl;
import dao.web.CurrencyDAO;
import dao.web.CurrencyDAOImpl;
import service.*;
import ui.MainPage;

import java.awt.*;
import java.util.Arrays;

public class Application {

    private static MainPage mainPage;

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        BookingDAO bookingDAO = new BookingDAOImpl();
        FlightDAO flightDAO = new FlightDAOImpl();
        CurrencyDAO currencyDAO = new CurrencyDAOImpl();

        BookingService bookingService = new BookingServiceImpl(bookingDAO, flightDAO);
        FlightService flightService = new FlightServiceImpl(flightDAO);
        ConverterService converterService = new ConverterServiceImpl(currencyDAO);

        mainPage = new MainPage(bookingService, flightService, converterService);
        mainPage.setVisible(true);
    }

    public static void restart() {
        Arrays.stream(Window.getWindows()).skip(1).filter(Component::isDisplayable).forEach(Window::dispose);
        mainPage.setVisible(true);
    }
}
