package api;
import model.*;
import service.*;
import java.util.*;

public class AdminResource {
  private static AdminResource instance = null;
  private AdminResource() {}
  public static AdminResource getInstance() {
    if (instance == null)
      instance = new AdminResource();
    return instance;
  }
  /**
   * Method for retrieving a customer object based on their email address.
   * @param email the customer's email address
   * @return the customer object that matches the email address, if available
   * @throws Exception if the searched email does not exist in the customers collection
   */
  public static Customer getCustomer(String email) throws Exception {
    return CustomerService.getInstance().getCustomer(email);
  }

  /**
   * Method for adding rooms to the rooms collection
   * @param rooms a list of room objects
   * @throws Exception if the number for a room entered conflicts with an existing room number
   */
  public static void addRoom(List<IRoom> rooms) throws Exception {
    try {
      for (IRoom room : rooms) {
        ReservationService.getInstance().addRoom(room);
      }
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * Method for retrieving a list of all rooms
   * @return a list of all rooms
   */
  public static Collection<IRoom> getAllRooms() {
    return ReservationService.getInstance().getAllRooms();
  }

  /**
   * Produces a list of all existing customers.
   * @return a list of all existing customers
   */
  public static Collection<Customer> getAllCustomers() {
    return CustomerService.getInstance().getAllCustomers();
  }

  /**
   * Prints all existing reservations.
   */
  public static void displayAllReservations() {
    ReservationService.getInstance().printAllReservations();
  }

  /**
   * Populates the program with a series of test data
   */
  public static void populateProgram() {
    try {
      CustomerService.getInstance().addSampleCustomers();
      ReservationService.getInstance().addSampleRooms();
      Calendar calendar = Calendar.getInstance();

      calendar.set(2050, 00, 01);
      Date checkIn = calendar.getTime();
      calendar.set(2050, 00, 02);
      Date checkOut = calendar.getTime();
      HotelResource.bookARoom("jj@gmail.com", HotelResource.getRoom("111"), checkIn, checkOut);

      calendar.set(2051, 04, 19);
      checkIn = calendar.getTime();
      calendar.set(2051, 04, 20);
      checkOut = calendar.getTime();
      HotelResource.bookARoom("jj@gmail.com", HotelResource.getRoom("222"), checkIn, checkOut);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
