package model;

import java.util.Date;

public class Reservation {

    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return String.format("%s is reserving room %s from %s to %s", customer.getName(), room.getRoomNumber(), checkInDate, checkOutDate);
    }
}
