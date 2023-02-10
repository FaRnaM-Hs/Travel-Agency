package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.daoDoubleTestHelpers.CurrencyDAOImplDouble;

import static org.junit.jupiter.api.Assertions.*;

public class ConverterServiceImplShould {

    private ConverterService converterService;

    @BeforeEach
    void setUp() {
        converterService = new ConverterServiceImpl(new CurrencyDAOImplDouble());
    }

    @Test
    void convert_from_irr_to_usd() {
        double usd = converterService.IRRToUSD(750_000);

        assertEquals(17.87, usd);
    }

    @Test
    void convert_from_irr_to_eur() {
        double eur = converterService.IRRToEUR(800_000);

        assertEquals(17.90, eur);
    }
}
