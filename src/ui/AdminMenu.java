package ui;

import api.AdminResource;
import model.RoomType;

import java.util.Objects;
import java.util.Scanner;

public class AdminMenu {

    private final AdminResource adminResource = AdminResource.getInstance();

    private final Scanner scanner;

    public AdminMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void adminMenu() {
        boolean keepRunning = true;
        String errorMessage = "Please select a number between 1 and 5";

        while(keepRunning) {
            try {
                System.out.println("--- Admin Menu ---\n");
                System.out.println("1. See all customers");
                System.out.println("2. See all rooms");
                System.out.println("3. See all reservations");
                System.out.println("4. Add a room");
                System.out.println("5. Back to main menu");
                System.out.print("Please make a selection:  ");

                int selection = Integer.parseInt(scanner.nextLine());

                switch (selection) {
                    case 1 -> seeAllCustomers();
                    case 2 -> seeAllRooms();
                    case 3 -> seeAllReservations();
                    case 4 -> addRoom();
                    case 5 -> {
                        System.out.println();
                        keepRunning = false;
                    }
                    default -> System.out.printf("\n%s\n\n", errorMessage);
                }
            } catch (Exception ex) {
                System.out.printf("\n%s\n\n", errorMessage);
            }
        }
    }

    private void seeAllCustomers() {
        System.out.println();
        adminResource.displayAllCustomers();
        System.out.println();
    }

    private void seeAllRooms() {
        System.out.println();
        adminResource.displayAllRooms();
        System.out.println();
    }

    private void seeAllReservations() {
        System.out.println();
        adminResource.displayAllReservations();
        System.out.println();
    }

    private void addRoom() {
        boolean keepRunning = true;

        System.out.println("\nAdd a room\n");

        while(keepRunning) {
            main: try {
                System.out.println("Please enter the room number:");
                String roomNumber = scanner.nextLine();
                System.out.println("Please enter the price:");
                Double price = (double) Math.round(Double.parseDouble(scanner.nextLine()) * 100) / 100;
                System.out.println("Please enter the room type:");
                System.out.println("1. Single Room");
                System.out.println("2. Double Room");
                int roomTypeChoice = Integer.parseInt(scanner.nextLine());
                RoomType roomType;
                switch (roomTypeChoice) {
                    case 1 -> roomType = RoomType.SINGLE;
                    case 2 -> roomType = RoomType.DOUBLE;
                    default -> {
                        System.out.println("\nPlease select 1 or 2\n");
                        break main;
                    }
                }
                String message = adminResource.addRoom(roomNumber, price, roomType);

                System.out.printf("\n%s\n\n", message);
                if (Objects.equals(message, "Room created")) {
                    keepRunning = false;
                }
            } catch (NumberFormatException ex) {
                System.out.println("\nPlease enter a valid number\n");
            }
        }
    }
}
