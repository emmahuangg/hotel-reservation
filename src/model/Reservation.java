package model;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Represents a reservation.
 */

public class Reservation {
  /**
   * the customer for the reservation
   */
  private Customer customer;
  /**
   * the reserved room
   */
  private IRoom room;
  /**
   * the check-in date for the reservation
   */
  private Date checkInDate;
  /**
   * the check-out date for the reservation
   */
  private Date checkOutDate;

  /**
   * Constructor for a reservation.
   * @param customer the customer for the reservation
   * @param room the reserved room
   * @param checkInDate the check-in date for the reservation
   * @param checkOutDate the check-out date for the reservation
   * @throws Exception if the check-in date is, erroneously, on or after the check-out date
   */
  public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) throws Exception {
    if (checkInDate.before(checkOutDate)) {
      this.customer = customer;
      this.room = room;
      this.checkInDate = checkInDate;
      this.checkOutDate = checkOutDate;
    } else {
      throw new Exception("The check-in date must be before the check-out date.");
    }
  }

  public Customer getCustomer() {
    return customer;
  }

  public IRoom getRoom() {
    return room;
  }

  public Date getCheckInDate() {
    return checkInDate;
  }

  public Date getCheckOutDate() {
    return checkOutDate;
  }

  @Override
  public String toString() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String checkInDateStr = simpleDateFormat.format(checkInDate);
    String checkOutDateStr = simpleDateFormat.format(checkOutDate);
    return "Customer:\n" + customer + "\nRoom:\n" + room + "\nCheck-in date: " + checkInDateStr + " | Check-out date: " + checkOutDateStr;
  }

  /**
   * Method that determines if a given date range conflicts with the date range of this reservation
   * @param checkInDate the start of the checked date range
   * @param checkOutDate the end of the checked date range
   * @return true if the date range conflicts with this reservation
   */
  public boolean isConflicting(Date checkInDate, Date checkOutDate) {
    boolean dateOverlap =
      this.checkInDate.before(checkOutDate)
        && this.checkOutDate.after(checkInDate);
    return dateOverlap;
  }

  /**
   * Method that determines if two reservations conflict with each other.
   * Reservations conflict if their room numbers overlap and date ranges overlap (i.e. A checks in before B checks out AND A checks out after B checks in)
   * @param reservation2 the compared reservation
   * @return true if the reservations conflict, false otherwise
   */
  public boolean isConflicting(Reservation reservation2) {
    boolean roomOverlap = this.room == reservation2.room;
    boolean dateOverlap =
      this.checkInDate.before(reservation2.getCheckOutDate())
        && this.checkOutDate.after(reservation2.getCheckInDate());
    if (roomOverlap && dateOverlap) {
      return true;
    } else {
      return false;
    }
  }
}
