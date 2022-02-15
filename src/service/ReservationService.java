package service;

import model.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {

    private static ReservationService reservationService = null;

    private ReservationService() {
    }

    public static ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    Map<String, IRoom> rooms = new HashMap<>();

    Map<String, Set<Date>> unavailableDates = new HashMap<>();

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
        unavailableDates.put(roomNumber, new HashSet<>());
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
        ArrayList<String> availableRoomNumbers = new ArrayList<>();
        unavailableDates.forEach((k, v) -> {
            boolean conflictFound = false;
            for (Date date : v) {
                if (dateConflict(checkInDate, checkOutDate, date)) {
                    conflictFound = true;
                    break;
                }
            };
            if (!conflictFound) {
                availableRoomNumbers.add(k);
            }
        });
        return availableRoomNumbers.stream().map(rn -> rooms.get(rn)).collect(Collectors.toList());
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        unavailableDates.put(room.getRoomNumber(), getDatesInRange(checkInDate, checkOutDate));
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
            System.out.println("Reservations");
            reservations.forEach(System.out::println);
        }
    }

    private boolean dateConflict(Date checkin, Date checkout, Date unavailable) {
        return (unavailable.equals(checkin) || unavailable.after(checkin)) && (unavailable.equals(checkout) || unavailable.before(checkout));
    }

    private Set<Date> getDatesInRange(Date checkin, Date checkout) {
        Set<Date> dates = new HashSet<>();
        dates.add(checkin);
        dates.add(checkout);

        Date current = checkin;

        while (current.before(checkout)) {
            Date next = Date.from(current.toInstant().plusSeconds(60*60*24));
            dates.add(next);
            current = next;
        }

        return dates;
    }
}
