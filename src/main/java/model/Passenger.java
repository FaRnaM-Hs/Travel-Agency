package model;

import java.time.LocalDate;
import java.util.Objects;

public class Passenger {

    private static final String NATION_CODE_PATTERN = "[0-9]{10}";
    private static final String NAME_PATTERN = "^[\\p{L} ']+$";
    private static final String PHONE_NUMBER_PATTERN = "09\\d{9}$";

    private final String nationalCode;
    private final String firstName;
    private final String lastName;
    private final Gender gender;
    private final LocalDate birthday;
    private final String phoneNumber;
    private final String address;

    public Passenger(String nationalCode, String firstName, String lastName, Gender gender, LocalDate birthday, String phoneNumber, String address) {
        this.nationalCode = nationalCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.address = address;

        check();
    }

    public String getNationalCode() {
        return nationalCode;
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
                + " | " + "National Code: " + nationalCode
                + " | " + "Gender: " + gender.name
                + " | " + "Birthday: " + birthday.toString()
                + " | " + "Phone Number: " + phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(nationalCode, passenger.nationalCode)
                && Objects.equals(firstName, passenger.firstName)
                && Objects.equals(lastName, passenger.lastName)
                && gender == passenger.gender
                && Objects.equals(birthday, passenger.birthday)
                && Objects.equals(phoneNumber, passenger.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nationalCode, firstName, lastName, gender, birthday, phoneNumber);
    }

    private void check() {
        checkNationalCode();
        checkName(this.firstName);
        checkName(this.lastName);
        checkBirthday();
        checkPhoneNumber();
    }

    private void checkNationalCode() {
        if (!this.nationalCode.matches(NATION_CODE_PATTERN))
            throw new IllegalArgumentException("National code must be a 10-digit number.");

    }

    private void checkName(String name) {
        if (!name.matches(NAME_PATTERN))
            throw new IllegalArgumentException("Names have to contain only the letters.");
    }

    private void checkBirthday() {
        if (this.birthday.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Birthday cannot be in the future.");
    }

    private void checkPhoneNumber() {
        if (!this.phoneNumber.matches(PHONE_NUMBER_PATTERN))
            throw new IllegalArgumentException("Phone number must be a 11-digit number and start with '09'.");
    }
}