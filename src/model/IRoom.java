package model;

/**
 * Interface providing methods to retrieve room information.
 */

public interface IRoom {
  public String getRoomNumber();
  public Double getRoomPrice();
  public RoomType getRoomType();
  public boolean isFree();
}
