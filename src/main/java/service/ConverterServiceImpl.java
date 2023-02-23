package service;

import dao.web.CurrencyDAO;

public class ConverterServiceImpl implements ConverterService {

    private final CurrencyDAO currencyDAO;

    public ConverterServiceImpl(CurrencyDAO currencyDAO) {
        this.currencyDAO = currencyDAO;
    }

    @Override
    public void update() {
        currencyDAO.update();
    }

    @Override
    public double IRRToUSD(double amount) {
        return Double.parseDouble(String.format("%.2f", amount / currencyDAO.getIRR()));
    }

    @Override
    public double IRRToEUR(double amount) {
        return Double.parseDouble(String.format("%.2f", amount / (currencyDAO.getIRR() / currencyDAO.getEUR())));
    }
}