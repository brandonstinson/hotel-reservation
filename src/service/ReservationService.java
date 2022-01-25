package service;

import model.*;

import java.util.*;

public class ReservationService {

    private static ReservationService reservationService = null;

    private ReservationService() {}

    public static ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    Map<String, IRoom> rooms = new HashMap<>();

    Collection<Reservation> reservations = new ArrayList<>();

    public void addRoom(String roomNumber, Double price, RoomType roomType) {
        IRoom room;
        if (price == 0.0) {
            room = new FreeRoom(roomNumber, roomType);
        } else {
            room = new Room(roomNumber, price, roomType);
        }
        rooms.put(roomNumber, room);
    }

    public IRoom getRoom(String roomNumber) {
        Optional<IRoom> room = Optional.ofNullable(rooms.get(roomNumber));
        return room.orElseThrow(() -> new NoSuchElementException("Room not found"));
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    public void printAllRooms() {
        rooms.values().forEach(System.out::println);
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        // TODO implement this
        return rooms.values();
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        // TODO check room is available during the date range
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }

    public Collection<Reservation> getCustomerReservations(Customer customer) {
        return reservations.stream().filter(r -> r.getCustomer() == customer).toList();
    }

    public void printAllReservations() {
        reservations.forEach(System.out::println);
    }
}
