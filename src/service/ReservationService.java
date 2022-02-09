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

    public String addRoom(String roomNumber, Double price, RoomType roomType) {
        if (Objects.equals(roomNumber, "")) {
            return "Room number cannot be blank";
        }
        if (rooms.get(roomNumber) != null) {
            return "Room number already exists";
        }
        IRoom room = price == 0.0 ? new FreeRoom(roomNumber, roomType) : new Room(roomNumber, price, roomType);
        rooms.put(roomNumber, room);
        return "Room created";
    }

    public IRoom getRoom(String roomNumber) {
        Optional<IRoom> room = Optional.ofNullable(rooms.get(roomNumber));
        return room.orElseThrow(() -> new NoSuchElementException("Room not found"));
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    public void printAllRooms() {
        if (rooms.isEmpty()) {
            System.out.println("No Rooms yet");
        } else {
            System.out.println("Rooms");
            rooms.values().forEach(System.out::println);
        }
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
        if (customer == null) return Collections.emptyList();
        return reservations.stream().filter(r -> r.getCustomer() == customer).toList();
    }

    public void printAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations yet");
        } else {
            reservations.forEach(System.out::println);
        }
    }
}
