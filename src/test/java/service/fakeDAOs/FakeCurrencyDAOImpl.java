package service.fakeDAOs;

import dao.web.CurrencyDAO;

import java.util.List;

// Based on USD
public class FakeCurrencyDAOImpl implements CurrencyDAO {

    private List<Double> currencies;

    @Override
    public void update() {
        this.currencies = List.of(0.939456, 41978.1);
    }

    @Override
    public double getEUR() {
        return this.currencies.get(0);
    }

    @Override
    public double getIRR() {
        return this.currencies.get(1);
    }
}
