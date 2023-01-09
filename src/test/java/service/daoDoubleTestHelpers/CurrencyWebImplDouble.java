package service.daoDoubleTestHelpers;

import dao.web.CurrencyWeb;

// Based on USD
public class CurrencyWebImplDouble implements CurrencyWeb {

    @Override
    public double getUSD() {
        return 1;
    }

    @Override
    public double getEUR() {
        return 0.939456;
    }

    @Override
    public double getIRR() {
        return 41978.1;
    }
}
