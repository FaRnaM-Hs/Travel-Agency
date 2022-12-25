package dao.web;

import dao.ConfigReaderException;
import dao.web.classes.Currency;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class CurrencyWebImpl implements CurrencyWeb {

    private final String uri;
    private final Currency currency;

    public CurrencyWebImpl() {
        try {
            FileReader reader = new FileReader("api.properties");
            Properties properties = new Properties();
            properties.load(reader);
            this.uri = properties.getProperty("uri");
        }  catch (IOException e) {
            throw new ConfigReaderException(e);
        }

        this.currency = new Client().get(uri, Currency.class);
    }

    @Override
    public double getUSD() {
        return currency.getRates().getUSD();
    }

    @Override
    public double getEUR() {
        return currency.getRates().getEUR();
    }

    @Override
    public double getIRR() {
        return currency.getRates().getIRR();
    }
}
