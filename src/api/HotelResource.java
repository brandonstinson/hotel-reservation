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

    final CustomerService customerService = CustomerService.getInstance();
    final ReservationService reservationService = ReservationService.getInstance();

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public String createCustomer(String firstName, String lastName, String email) {
        return customerService.addCustomer(firstName, lastName, email);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = getCustomer(customerEmail);
        return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }

    public void getCustomerReservations(String customerEmail) {
        Customer customer = getCustomer(customerEmail);
        Collection<Reservation> reservations = reservationService.getCustomerReservations(customer);
        if (reservations.isEmpty()) {
            System.out.println("\nNo reservations yet\n");
        } else {
            reservations.forEach(System.out::println);
        }
    }

    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate) {
        return reservationService.findRooms(checkInDate, checkOutDate);
    }

    public Date addDays(Date date, Long days) {
        return reservationService.addDays(date, days);
    }
}
