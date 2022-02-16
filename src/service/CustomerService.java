package service;

import model.Customer;

import java.util.*;

public class CustomerService {

    private static CustomerService customerService = null;

    private CustomerService() {}

    public static CustomerService getInstance() {
        if (customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }

    final Map<String, Customer> customers = new HashMap<>();

    public String addCustomer(String firstName, String lastName, String email) {
        if (Objects.equals(firstName, "")) {
            return "First name cannot be blank";
        }
        if (Objects.equals(lastName, "")) {
            return "Last name cannot be blank";
        }
        if (Objects.equals(email, "")) {
            return "Email cannot be blank";
        }
        if (customers.get(email) != null) {
            return "Email already in use";
        }
        try {
            Customer customer = new Customer(firstName, lastName, email);
            customers.put(email, customer);
            return "Customer created";
        } catch (Exception ex) {
            return "Invalid email";
        }
    }

    public Customer getCustomer(String email) {
        return customers.get(email);
    }

    public void printAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers yet");
        } else {
            System.out.println("Customers");
            customers.values().forEach(System.out::println);
        }
    }
}
