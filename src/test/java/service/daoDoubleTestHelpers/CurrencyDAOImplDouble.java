package service.daoDoubleTestHelpers;

import dao.web.CurrencyDAO;

// Based on USD
public class CurrencyDAOImplDouble implements CurrencyDAO {

    @Override
    public void update() {
        // ignore
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
