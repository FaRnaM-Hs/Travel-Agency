package model;

public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    public final String name;

    Gender(String name) {
        this.name = name;
    }
}
