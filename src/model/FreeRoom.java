package model;

public class FreeRoom extends Room {

    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber, 0.0, roomType);
    }

    @Override
    public Boolean isFree() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("Room number %s is a %s type free room.", roomNumber, roomType);
    }
}
