package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.fakeDAOs.FakeCurrencyDAOImpl;

import static org.junit.jupiter.api.Assertions.*;

public class ConverterServiceImplShould {

    private ConverterService converterService;

    @BeforeEach
    void setUp() {
        converterService = new ConverterServiceImpl(new FakeCurrencyDAOImpl());
    }

    @Test
    void update_currencies_without_any_error() {
        assertDoesNotThrow(() -> converterService.update());
    }

    @Test
    void convert_from_irr_to_usd() {
        converterService.update();
        double usd = converterService.IRRToUSD(750_000);

        assertEquals(17.87, usd);
    }

    @Test
    void convert_from_irr_to_eur() {
        converterService.update();
        double eur = converterService.IRRToEUR(800_000);

        assertEquals(17.90, eur);
    }
}
