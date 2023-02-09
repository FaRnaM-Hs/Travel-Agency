package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.daoDoubleTestHelpers.CurrencyDAOImplDouble;

import static org.assertj.core.api.Assertions.*;

public class ConverterServiceImplShould {

    private ConverterService converterService;

    @BeforeEach
    void setUp() {
        converterService = new ConverterServiceImpl(new CurrencyDAOImplDouble());
    }

    @Test
    void convert_from_irr_to_usd() {
        double usd = converterService.IRRToUSD(750_000);

        assertThat(usd).isEqualTo(17.87);
    }

    @Test
    void convert_from_irr_to_eur() {
        double eur = converterService.IRRToEUR(800_000);

        assertThat(eur).isEqualTo(17.90);
    }
}
