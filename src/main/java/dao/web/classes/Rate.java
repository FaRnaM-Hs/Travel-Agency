package dao.web.classes;

public class Rate {

    private final double USD;
    private final double EUR;
    private final double IRR;

    public Rate(double USD, double EUR, double IRR) {
        this.USD = USD;
        this.EUR = EUR;
        this.IRR = IRR;
    }

    public double getUSD() {
        return USD;
    }

    public double getEUR() {
        return EUR;
    }

    public double getIRR() {
        return IRR * 10;
    }
}
