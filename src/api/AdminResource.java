package api;

import model.Customer;
import model.IRoom;
import model.RoomType;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;

public class AdminResource {

    private static AdminResource adminResource = null;

    private AdminResource() {}

    public static AdminResource getInstance() {
        if (adminResource == null) {
            adminResource = new AdminResource();
        }
        return adminResource;
    }

    CustomerService customerService = CustomerService.getInstance();
    ReservationService reservationService = ReservationService.getInstance();

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllCustomers() {
        customerService.printAllCustomers();
    }

    public String addRoom(String roomNumber, Double price, RoomType roomType) {
        return reservationService.addRoom(roomNumber, price, roomType);
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public void displayAllRooms() {
        reservationService.printAllRooms();
    }

    public void displayAllReservations() {
        reservationService.printAllReservations();
    }
}
