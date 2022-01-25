package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {

    private static HotelResource hotelResource = null;

    private HotelResource() {}

    public static HotelResource getInstance() {
        if (hotelResource == null) {
            hotelResource = new HotelResource();
        }
        return hotelResource;
    }

    CustomerService customerService = CustomerService.getInstance();
    ReservationService reservationService = ReservationService.getInstance();

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createCustomer(String firstName, String lastName, String email) {
        customerService.addCustomer(firstName, lastName, email);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getRoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = getCustomer(customerEmail);
        return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomerReservations(String customerEmail) {
        Customer customer = getCustomer(customerEmail);
        return reservationService.getCustomerReservations(customer);
    }

    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate) {
        return reservationService.findRooms(checkInDate, checkOutDate);
    }
}
