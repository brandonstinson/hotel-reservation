package api;

import model.RoomType;
import service.CustomerService;
import service.ReservationService;

public class AdminResource {

    private static AdminResource adminResource = null;

    private AdminResource() {}

    public static AdminResource getInstance() {
        if (adminResource == null) {
            adminResource = new AdminResource();
        }
        return adminResource;
    }

    final CustomerService customerService = CustomerService.getInstance();
    final ReservationService reservationService = ReservationService.getInstance();

    public void displayAllCustomers() {
        customerService.printAllCustomers();
    }

    public String addRoom(String roomNumber, Double price, RoomType roomType) {
        return reservationService.addRoom(roomNumber, price, roomType);
    }

    public void displayAllRooms() {
        reservationService.printAllRooms();
    }

    public void displayAllReservations() {
        reservationService.printAllReservations();
    }
}
