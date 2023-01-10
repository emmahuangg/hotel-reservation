package api;
import model.*;
import service.*;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.*;

public class HotelResource {
  private static HotelResource instance = null;
  private HotelResource() {}
  public static HotelResource getInstance() {
    if (instance == null)
      instance = new HotelResource();
    return instance;
  }
  /**
   * Method for retrieving a customer object based on their email address.
   *
   * @param email the customer's email address
   * @return the customer object that matches the email address, if available
   * @throws Exception if the searched email does not exist in the customers collection
   */
  public static Customer getCustomer(String email) throws Exception {
    try {
      return CustomerService.getInstance().getCustomer(email);
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * Method for adding a customer to the customers collection.
   *
   * @param firstName the customer's first name
   * @param lastName  the customer's last name
   * @param email     the customer's unique email address in the format "(...)@(...).com"
   * @throws Exception if the email provided is not unique
   */
  public static void createACustomer(String firstName, String lastName, String email) throws Exception {
    try {
      CustomerService.getInstance().addCustomer(firstName, lastName, email);
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * Method for retrieving a room object based on its number.
   *
   * @param roomNumber the room number
   * @return the room object that corresponds to the given room number, if available
   * @throws Exception if the room number provided does not correspond to an existing room
   */
  public static IRoom getRoom(String roomNumber) throws Exception {
    try {
      return ReservationService.getInstance().getARoom(roomNumber);
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * Makes a reservation for the given room and date range for the customer with the given email address.
   *
   * @param customerEmail the customer's email address
   * @param room          the room to be reserved
   * @param checkInDate   the date on which the customer checks in
   * @param checkOutDate  the date on which the customer checks out
   * @return the new reservation, if successfully created
   * @throws Exception if the customer's email does not exist in the customers collection, or if the reservation conflicts with an existing reservation.
   */
  public static Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) throws Exception {
    try {
      Customer customer = CustomerService.getCustomer(customerEmail);
      return ReservationService.getInstance().reserveARoom(customer, room, checkInDate, checkOutDate);
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * Lists all reservations made by the customer with the given email.
   *
   * @param customerEmail the customer's email
   * @return a collection of reservations relevant to the given email
   */
  public static Collection<Reservation> getCustomersReservations(String customerEmail) {
    return ReservationService.getInstance().getCustomersReservations(customerEmail);
  }

  /**
   * Method for finding available rooms during the specified date range.
   *
   * @param checkIn  the date on which the customer checks in
   * @param checkOut the date on which the customer checks out
   * @return a collection of available rooms
   */
  public static Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
    Collection<IRoom> availableRooms = ReservationService.getInstance().findRooms(checkIn, checkOut);
    return availableRooms;
  }

  /**
   * Increments a date by a week.
   * @param date a date
   * @return the date with seven days added to it
   */
  public static Date incrementDate(Date date, int days) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_YEAR, days);
    date = calendar.getTime();
    return date;
  }
}