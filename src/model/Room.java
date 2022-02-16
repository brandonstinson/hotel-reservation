package model;

public class Room implements IRoom {

    protected final String roomNumber;
    protected final Double price;
    protected final RoomType roomType;

    public Room(String roomNumber, Double price, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public Boolean isFree() {
        return false;
    }

    @Override
    public String toString() {
        return String.format("Room number %s - %s - $%.2f", roomNumber, roomType, price);
    }
}
