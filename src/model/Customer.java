package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {

    private final String firstName;
    private final String lastName;
    private final String email;

    public Customer(String firstName, String lastName, String email) {
        if (!Customer.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%s)", firstName, lastName, email);
    }

    public static Boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^(.+)@(.+).com$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
