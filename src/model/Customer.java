package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {

    private String firstName;
    private String lastName;
    private String email;

    public Customer(String firstName, String lastName, String email) {
        if (!Customer.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getName() {
        return String.format("%s %s", firstName, lastName);
    }

    public String getEmail() { return email; }

    @Override
    public String toString() {
        return String.format("Customer %s %s is reachable at %s", firstName, lastName, email);
    }

    public static Boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^(.+)@(.+).com$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
