package dao.web;

import dao.PropertiesHelper;
import dao.web.classes.Currency;

// Based on USD
public class CurrencyDAOImpl implements CurrencyDAO {

    private final String uri;
    private final HttpClient httpClient;
    private Currency currency;

    public CurrencyDAOImpl() {
        this.uri = new PropertiesHelper("api.properties").getProperty("uri");
        this.httpClient = new HttpClient();
    }

    @Override
    public void update() {
        this.currency = this.httpClient.get(uri, Currency.class);
    }

    @Override
    public double getEUR() {
        return this.currency.getRates().getEUR();
    }

    @Override
    public double getIRR() {
        return this.currency.getRates().getIRR();
    }
}