package model;

public enum City {

    TEHRAN("Tehran"),
    MASHHAD("Mashhad"),
    KISH("Kish"),
    SHIRAZ("Shiraz"),
    TABRIZ("Tabriz"),
    AHVAZ("Ahvaz"),
    BANDARABBAS("Bandarabbas"),
    BUSHEHR("Bushehr"),
    ZAHEDAN("Zahedan"),
    ARDABIL("Ardabil"),
    ABADAN("Abadan"),
    GHESHM("Gheshm"),
    ESFAHAN("Esfahan"),
    GORGAN("Gorgan"),
    RASHT("Rasht");

    public final String name;

    City(String name) {
        this.name = name;
    }
}