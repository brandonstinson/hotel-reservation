package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {

    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

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
        DateFormat format = new SimpleDateFormat("dd MMM yyy");
        return String.format("Room %s reserved from %s to %s", room.getRoomNumber(), format.format(checkInDate), format.format(checkOutDate));
    }
}
