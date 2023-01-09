package service.impl;

import dao.web.CurrencyWeb;
import service.ConverterService;

public class ConverterServiceImpl implements ConverterService {

    private final CurrencyWeb currencyWeb;

    public ConverterServiceImpl(CurrencyWeb currencyWeb) {
        this.currencyWeb = currencyWeb;
    }

    @Override
    public double IRRToUSD(double amount) {
        return Double.parseDouble(String.format("%.2f", amount / currencyWeb.getIRR()));
    }

    @Override
    public double IRRToEUR(double amount) {
        return Double.parseDouble(String.format("%.2f", amount / (currencyWeb.getIRR() / currencyWeb.getEUR())));
    }
}
