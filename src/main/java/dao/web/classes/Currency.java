package dao.web.classes;

public class Currency {

    private final String date;
    private final String base;
    private final Rate rates;

    public Currency(String date, String base, Rate rates) {
        this.date = date;
        this.base = base;
        this.rates = rates;
    }

    public String getDate() {
        return date;
    }

    public String getBase() {
        return base;
    }

    public Rate getRates() {
        return rates;
    }
}