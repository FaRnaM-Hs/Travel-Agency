package model;

import java.time.LocalDate;
import java.util.Objects;

public class Passenger {

    private final String nationCode;
    private final String firstName;
    private final String lastName;
    private final Gender gender;
    private final LocalDate birthday;
    private final String phoneNumber;
    private final String address;

    public Passenger(String nationCode, String firstName, String lastName, Gender gender, LocalDate birthday, String phoneNumber, String address) {
        this.nationCode = nationCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getNationCode() {
        return nationCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Passenger: " + firstName + " " + lastName
                + " | " + "Nation Code: " + nationCode
                + " | " + "Gender: " + gender.name
                + " | " + "Birthday: " + birthday.toString()
                + " | " + "Phone Number: " + phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(nationCode, passenger.nationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nationCode);
    }
}
