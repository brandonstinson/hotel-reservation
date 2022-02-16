package ui;

import api.HotelResource;
import model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {

    private final HotelResource hotelResource = HotelResource.getInstance();

    private String customerEmail = null;

    public void mainMenu() {
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

        if (customerEmail == null) {
            System.out.println("\nPlease create an account first\n");
        } else {
            Map<String, Date> dates = getDates(scanner);
            Date checkInDate = dates.get("checkin");
            Date checkoutDate = dates.get("checkout");

            IRoom selectedRoom = selectRoom(scanner, checkInDate, checkoutDate);

            System.out.println();

            if (selectedRoom != null) {
                Reservation reservation = hotelResource.bookARoom(customerEmail, selectedRoom, checkInDate, checkoutDate);
                System.out.println(reservation);
                System.out.println();
            }
        }
    }

    private void seeReservations() {
        if (customerEmail == null) {
            System.out.println("\nPlease create an account first\n");
        } else {
            System.out.println("\nReservations");
            hotelResource.getCustomerReservations(customerEmail);
            System.out.println();
        }
    }

    private void createAnAccount(Scanner scanner) {
        boolean keepRunning = true;

        System.out.println("\nCreate an account\n");

        while (keepRunning) {
            System.out.println("Please enter first name:");
            String firstName = scanner.nextLine();
            if (firstName.isBlank()) {
                System.out.println("First name cannot be blank\n");
                continue;
            }
            System.out.println("Please enter last name:");
            String lastName = scanner.nextLine();
            if (lastName.isBlank()) {
                System.out.println("Last name cannot be blank\n");
                continue;
            }
            System.out.println("Please enter email:");
            String email = scanner.nextLine();
            if (email.isBlank()) {
                System.out.println("Email cannot be blank\n");
                continue;
            }
            String message = hotelResource.createCustomer(firstName, lastName, email);

            System.out.printf("\n%s\n\n", message);
            if (Objects.equals(message, "Customer created")) {
                customerEmail = email;
                keepRunning = false;
            }
        }
    }

    private Map<String, Date> getDates(Scanner scanner) {
        boolean keepRunning = true;
        DateFormat format = new SimpleDateFormat("dd MMM yyy");

        Date checkInDate;
        Date checkoutDate;

        Map<String, Date> dates = new HashMap<>();

        System.out.println();

        while (keepRunning) {
            try {
                System.out.println("Please enter a checkin date (dd mmm yyyy):");
                String checkinDateInput = scanner.nextLine();
                checkInDate = format.parse(checkinDateInput);

                System.out.println("Please enter a checkout date (dd mmm yyyy):");
                String checkoutDateInput = scanner.nextLine();
                checkoutDate = format.parse(checkoutDateInput);

                if (!checkoutDate.after(checkInDate)) {
                    System.out.println("\nCheckout must be after checkin\n");
                } else {
                    keepRunning = false;
                    dates.put("checkin", checkInDate);
                    dates.put("checkout", checkoutDate);
                }
            } catch (ParseException ex) {
                System.out.println("\nIncorrect format - use dd mmm yyyy\n");
            }
        }

        return dates;
    }

    private IRoom selectRoom(Scanner scanner, Date checkin, Date checkout) {
        DateFormat format = new SimpleDateFormat("dd MMM yyy");
        Collection<IRoom> availableRooms = hotelResource.findARoom(checkin, checkout);

        if (availableRooms.isEmpty()) {
            Date newCheckin = hotelResource.addDays(checkin, 7L);
            Date newCheckout = hotelResource.addDays(checkout, 7L);

            availableRooms = hotelResource.findARoom(newCheckin, newCheckout);

            if (availableRooms.isEmpty()) {
                System.out.println("\nNo rooms available on your selected dates.");
                return null;
            } else {
                System.out.println("\nNo rooms available on your selected dates.");
                System.out.printf("We have these rooms available from %s to %s.\n", format.format(newCheckin), format.format(newCheckout));
            }
        }

        Collection<String> availRoomNumbers = availableRooms.stream().map(IRoom::getRoomNumber).toList();
        String availRoomsString = availRoomNumbers.toString();

        boolean keepRunning = true;

        IRoom selectedRoom = null;

        while (keepRunning) {
            System.out.println("\nAvailable Rooms:");
            availableRooms.forEach(System.out::println);
            System.out.printf("\nPlease select a room %s or [x] to exit:   ", availRoomsString);
            String roomSelection = scanner.nextLine();

            if (Objects.equals(roomSelection, "x")) {
                break;
            } else {
                if (!availRoomNumbers.contains(roomSelection)) {
                    System.out.println("\nInvalid selection");
                } else {
                    keepRunning = false;
                    selectedRoom = availableRooms.stream().filter(rm -> Objects.equals(rm.getRoomNumber(), roomSelection)).toList().get(0);
                }
            }
        }

        return selectedRoom;
    }
}
