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

    Map<String, Customer> customers = new HashMap<>();

    public void addCustomer(String firstName, String lastName, String email) {
        if (customers.get(email) != null) {
            System.out.println("Email already in use");
            return;
        }
        try {
            Customer customer = new Customer(firstName, lastName, email);
            customers.put(email, customer);
        } catch (Exception ex) {
            System.out.println("Invalid email");
        }
    }

    public Customer getCustomer(String email) {
        return customers.get(email);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }

    public void printAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers yet.");
        }
        customers.values().forEach(System.out::println);
    }
}
