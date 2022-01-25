package model;

public class Tester {

    public static void main(String[] args) {

        Customer customer = new Customer("brandon", "stinson", "bs@email.com");

        // Customer invalid = new Customer("brandon", "stinson", "email");

        System.out.println(customer);
    }
}
