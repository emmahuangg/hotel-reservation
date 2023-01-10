package model;

/**
 * Represents a room that is free of charge.
 */

public class FreeRoom extends Room {
  /**
   * Constructor for a free room.
   * @param roomNumber the room number
   * @param roomType the room type
   */
  public FreeRoom(String roomNumber, RoomType roomType) {
    super(roomNumber, 0d, roomType);
  }

  public String toString() {
    return "Room number: " + roomNumber + " | Price: Free" + " | Room Type: " + roomType;
  }
}
