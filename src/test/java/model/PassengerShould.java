package model;

import org.junit.jupiter.api.Test;

import static java.time.LocalDate.of;
import static model.Gender.MALE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PassengerShould {

    @Test
    void throw_exception_if_national_code_contains_letters() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Passenger(
                        "123456abc7",
                        "Ali",
                        "Javadi",
                        MALE,
                        of(1970, 5, 10),
                        "09123456789",
                        "Iran"
                ),
                "National code must be a 10-digit number."
        );
    }

    @Test
    void throw_exception_if_national_code_contains_non_digit_characters() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Passenger(
                        "123-45678-9",
                        "Ali",
                        "Javadi",
                        MALE,
                        of(1970, 5, 10),
                        "09123456789",
                        "Iran"
                ),
                "National code must be a 10-digit number."
        );
    }

    @Test
    void throw_exception_if_national_code_is_less_than_10_digits() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Passenger(
                        "123456789",
                        "Ali",
                        "Javadi",
                        MALE,
                        of(1970, 5, 10),
                        "09123456789",
                        "Iran"
                ),
                "National code must be a 10-digit number."
        );
    }

    @Test
    void throw_exception_if_national_code_is_more_than_10_digits() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Passenger(
                        "123456789101",
                        "Ali",
                        "Javadi",
                        MALE,
                        of(1970, 5, 10),
                        "09123456789",
                        "Iran"
                ),
                "National code must be a 10-digit number."
        );
    }

    @Test
    void throw_exception_if_national_code_is_empty() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Passenger(
                        "",
                        "Ali",
                        "Javadi",
                        MALE,
                        of(1970, 5, 10),
                        "09123456789",
                        "Iran"
                ),
                "National code must be a 10-digit number."
        );
    }

    @Test
    void throw_exception_if_first_name_contains_non_letter_characters() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Passenger(
                        "1234567890",
                        "@Ali007",
                        "Javadi",
                        MALE,
                        of(1970, 5, 10),
                        "09123456789",
                        "Iran"
                ),
                "Names have to contain only the letters."
        );
    }

    @Test
    void throw_exception_if_last_name_contains_non_letter_characters() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Passenger(
                        "1234567890",
                        "Ali",
                        "(Javad!)",
                        MALE,
                        of(1970, 5, 10),
                        "09123456789",
                        "Iran"
                ),
                "Names have to contain only the letters."
        );
    }

    @Test
    void throw_exception_if_first_name_is_empty() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Passenger(
                        "1234567890",
                        "",
                        "Javad",
                        MALE,
                        of(1970, 5, 10),
                        "09123456789",
                        "Iran"
                ),
                "Names have to contain only the letters."
        );
    }

    @Test
    void throw_exception_if_last_name_is_empty() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Passenger(
                        "1234567890",
                        "Ali",
                        "",
                        MALE,
                        of(1970, 5, 10),
                        "09123456789",
                        "Iran"
                ),
                "Names have to contain only the letters."
        );
    }

    @Test
    void throw_exception_if_birthday_is_in_the_future() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Passenger(
                        "1234567890",
                        "Ali",
                        "Javadi",
                        MALE,
                        of(3000, 1, 1),
                        "09123456789",
                        "Iran"
                ),
                "Birthday cannot be in the future."
        );
    }

    @Test
    void throw_exception_if_phone_number_contains_non_digit_characters() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Passenger(
                        "1234567890",
                        "Ali",
                        "Javadi",
                        MALE,
                        of(1970, 5, 10),
                        "+(912)-345-6789",
                        "Iran"
                ),
                "Names have to contain only the letters."
        );
    }

    @Test
    void throw_exception_if_phone_number_is_less_than_11_digits() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Passenger(
                        "1234567890",
                        "Ali",
                        "Javadi",
                        MALE,
                        of(1970, 5, 10),
                        "0912345678",
                        "Iran"
                ),
                "Names have to contain only the letters."
        );
    }

    @Test
    void throw_exception_if_phone_number_is_more_than_11_digits() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Passenger(
                        "1234567890",
                        "Ali",
                        "Javadi",
                        MALE,
                        of(1970, 5, 10),
                        "0912345678910",
                        "Iran"
                ),
                "Names have to contain only the letters."
        );
    }

    @Test
    void throw_exception_if_phone_number_does_not_start_with_09() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Passenger(
                        "1234567890",
                        "Ali",
                        "Javadi",
                        MALE,
                        of(1970, 5, 10),
                        "00123456789",
                        "Iran"
                ),
                "Names have to contain only the letters."
        );
    }

    @Test
    void be_created() {
        Passenger passenger = new Passenger(
                "1234567890",
                "Farnam",
                "Hasanzadeh",
                MALE,
                of(2004, 1, 17),
                "09123456789",
                "Iran"
        );

        assertEquals("1234567890", passenger.getNationalCode());
        assertEquals("Farnam", passenger.getFirstName());
        assertEquals("Hasanzadeh", passenger.getLastName());
        assertEquals(MALE, passenger.getGender());
        assertEquals(of(2004, 1, 17), passenger.getBirthday());
        assertEquals("09123456789", passenger.getPhoneNumber());
        assertEquals("Iran", passenger.getAddress());
        assertEquals("Passenger: Farnam Hasanzadeh | National Code: 1234567890" +
                " | Gender: Male | Birthday: 2004-01-17 | Phone Number: 09123456789", passenger.toString());
    }

    @Test
    void be_equal_to_another_passenger_with_the_same_information() {
        Passenger passenger_1 = new Passenger(
                "1234567890",
                "Farnam",
                "Hasanzadeh",
                MALE,
                of(2004, 1, 17),
                "09123456789",
                "Iran"
        );

        Passenger passenger_2 = new Passenger(
                "1234567890",
                "Farnam",
                "Hasanzadeh",
                MALE,
                of(2004, 1, 17),
                "09123456789",
                "Iran"
        );

        assertEquals(passenger_1, passenger_2);
    }
}
