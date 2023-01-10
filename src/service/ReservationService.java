package service;
import java.util.*;

import api.AdminResource;
import api.HotelResource;
import model.*;

public class ReservationService {
  private static ReservationService instance = null;
  /**
   * a static collection (set) containing all reservations.
   */
  private static Set<Reservation> reservations;
  /**
   * a static collection (map) containing all reservations.
   * The room's number is the map's key, and the room object is the map's value.
   */
  private static Map<String, IRoom> rooms;

  private ReservationService() {
    reservations = new HashSet<Reservation>();
    rooms = new TreeMap<String, IRoom>();
  }

  public static ReservationService getInstance() {
    if (instance == null)
      instance = new ReservationService();
    return instance;
  }

  /**
   * Method for adding a room to the rooms collection.
   * @param room the room to be added
   * @throws Exception if the room number conflicts with an existing room number
   */
  public static void addRoom(IRoom room) throws Exception {
    if (rooms.containsKey(room.getRoomNumber())) {
      throw new Exception("There exists a room with this room number already.");
    } else {
      if (room.getRoomPrice() == 0.0) {
        rooms.put(room.getRoomNumber(), new FreeRoom(room.getRoomNumber(), room.getRoomType()));
      } else {
        rooms.put(room.getRoomNumber(), (Room) room);
      }
    }
  }

  /**
   * Method for retrieving a room object based on its number.
   * @param roomId the room number
   * @return the room object that corresponds to the given room number, if available
   * @throws Exception if the room number provided does not correspond to an existing room
   */
  public static IRoom getARoom(String roomId) throws Exception {
    if (!rooms.containsKey(roomId)) {
      throw new Exception("There does not exist a room with this room number.");
    } else {
      return rooms.get(roomId);
    }
  }

  /**
   * Method for reserving a room.
   * @param customer the customer reserving the room
   * @param room the room being reserved
   * @param checkInDate the date on which the customer checks in
   * @param checkOutDate the date on which the customer checks out
   * @return the new reservation, if successfully created
   * @throws Exception if the reservation conflicts with an existing reservation.
   */
  public static Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) throws Exception {
    try {
      Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
      for (Reservation r : reservations) {
        if (reservation.isConflicting(r)) {
          throw new Exception("This reservation conflicts with an existing reservation.");
        }
      }
      reservations.add(reservation);
      return reservation;
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * Prints all existing reservations.
   */
  public static void printAllReservations() {
    for (Reservation reservation : reservations) {
      System.out.println(reservation);
      System.out.println("——————————");
    }
  }

  /**
   * A default-access method that returns the set of all reservations.
   * @return a set of all reservations
   */
  Set<Reservation> getReservations() {
    return reservations;
  }

  public static Collection<IRoom> getAllRooms() {
    return rooms.values();
  }

  /**
   * Method for finding available rooms during the specified date range.
   * @param checkInDate the date on which the customer checks in
   * @param checkOutDate the date on which the customer checks out
   * @return a collection of available rooms
   */
  public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
    Map<String, IRoom> availableRooms = new TreeMap<String, IRoom>(rooms);
    for (Reservation reservation : reservations) {
      if (reservation.isConflicting(checkInDate, checkOutDate)) {
        availableRooms.remove(reservation.getRoom().getRoomNumber());
      }
    }
    return availableRooms.values();
  }

  /**
   * Method for retrieving a customer's reservations based on their email address.
   * @param customerEmail the customer's email address
   * @return a collection of reservations that match the email address
   * @throws Exception if the searched email does not exist in the customers collection
   */
  public static Collection<Reservation> getCustomersReservations(String customerEmail) {
    List<Reservation> customersReservations = new ArrayList<Reservation>();
    for (Reservation reservation : reservations) {
      if (reservation.getCustomer().getEmail().equals(customerEmail.toLowerCase())) {
        customersReservations.add(reservation);
      }
    }
    return customersReservations;
  }

  public static void addSampleRooms() {
    rooms.put("111", new FreeRoom("111", RoomType.SINGLE));
    rooms.put("222", new Room("222", 100d, RoomType.DOUBLE));
    rooms.put("333", new Room("333", 500d, RoomType.DOUBLE));
  }

}
