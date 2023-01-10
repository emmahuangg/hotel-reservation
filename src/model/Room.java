package model;

/**
 * Represents a room.
 */

public class Room implements IRoom {

  /**
   * the room number
   */
  final protected String roomNumber;

  /**
   * the room price
   */
  protected Double price;
  /**
   * the room type
   */
  protected RoomType roomType;

  /**
   * Constructor for a room.
   * @param roomNumber the room number
   * @param price the price
   * @param roomType the room type
   */
  public Room(String roomNumber, Double price, RoomType roomType) {
    this.roomNumber = roomNumber;
    this.price = price;
    this.roomType = roomType;
  }

  public String getRoomNumber() {
    return roomNumber;
  }
  public Double getRoomPrice() {
    return price;
  }
  public RoomType getRoomType() {
    return roomType;
  }
  public boolean isFree() {
    return price == 0;
  }

  @Override
  public String toString() {
    return "Room number: " + roomNumber + " | Price: " + price + " | Room Type: " + roomType;
  }

  @Override
  public boolean equals(Object obj) {
    final Room room2 = (Room) obj;
    if(this.roomNumber.equals(room2.roomNumber)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return roomNumber.hashCode();
  }
}
