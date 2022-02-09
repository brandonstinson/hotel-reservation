package ui;

import api.HotelResource;

import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class MainMenu {

    private final HotelResource hotelResource = HotelResource.getInstance();

    private String customerEmail = null;

    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.mainMenu();
    }

    private void mainMenu() {
        boolean keepRunning = true;
        String errorMessage = "Please select a number between 1 and 5";

        try (Scanner scanner = new Scanner(System.in)) {
            while (keepRunning) {
                try {
                    System.out.println("--- Main Menu ---\n");
                    System.out.println("1. Find and reserve a room");
                    System.out.println("2. See my reservation");
                    System.out.println("3. Create an account");
                    System.out.println("4. Admin");
                    System.out.println("5. Exit");
                    System.out.print("Please make a selection:  ");

                    int selection = Integer.parseInt(scanner.nextLine());

                    switch (selection) {
                        case 1 -> reserveRoom(scanner);
                        case 2 -> seeReservations();
                        case 3 -> createAnAccount(scanner);
                        case 4 -> {
                            System.out.println();
                            AdminMenu adminMenu = new AdminMenu(scanner);
                            adminMenu.adminMenu();
                        }
                        case 5 -> keepRunning = false;
                        default -> System.out.printf("\n%s\n\n", errorMessage);
                    }
                } catch (Exception ex) {
                    System.out.printf("\n%s\n\n", errorMessage);
                }
            }
        }
    }

    private void reserveRoom(Scanner scanner) {
        Date checkIn = null;
        Date checkOut = null;

        if (customerEmail == null) {
            System.out.println("\nPlease create an account first\n");
        } else {
            // TODO ask for dates
            // TODO find and display rooms
            // TODO reserve selected room
            System.out.println("Booked");
        }
    }

    private void seeReservations() {
        if (customerEmail == null) {
            System.out.println("\nPlease create an account first\n");
        } else {
            hotelResource.getCustomerReservations(customerEmail);
        }
    }

    private void createAnAccount(Scanner scanner) {
        boolean keepRunning = true;

        System.out.println("\nCreate an account\n");

        while(keepRunning) {
            System.out.println("Please enter first name:");
            String firstName = scanner.nextLine();
            System.out.println("Please enter last name:");
            String lastName = scanner.nextLine();
            System.out.println("Please enter email:");
            String email = scanner.nextLine();
            String message = hotelResource.createCustomer(firstName, lastName, email);

            System.out.printf("\n%s\n\n", message);
            if (Objects.equals(message, "Customer created")) {
                customerEmail = email;
                keepRunning = false;
            }
        }
    }
}
