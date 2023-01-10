import java.util.*;
import api.*;
import model.*;
import service.*;
import static java.lang.System.exit;

import java.text.SimpleDateFormat;

public class MainMenu {
  private final static Scanner sc = new Scanner(System.in);
  public static void open() {
    while (true) {
      System.out.println("Main menu" +
        "\n——————————\n" +
        "1. Find and reserve a room\n" +
        "2. See my reservations\n" +
        "3. Create an account\n" +
        "4. Admin\n" +
        "5. Exit");
      int choice = 5;
      boolean validInput = false;
      while (!validInput) {
        try {
          choice = sc.nextInt();
          if (1 <= choice && choice <= 5) {
            validInput = true;
          } else {
            System.out.println("Please enter a number between 1 and 5.");
          }
        } catch (Exception e) {
          System.out.println("Please enter a number between 1 and 5.");
        }
      }
      switch (choice) {
        case 1:
          option1();
          break;
        case 2:
          option2();
          break;
        case 3:
          option3();
          break;
        case 4:
          option4();
          break;
        default:
          exit(0);
      }
    }
  }

  /**
   * Allows the user to find and book a room based on their check-in & check-out dates.
   */
  public static void option1() {
    // Getting user input for check-in & check-out dates
    Calendar calendar = Calendar.getInstance();
    calendar.set(0000,00,01);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date checkInDate = calendar.getTime();
    Date checkOutDate = calendar.getTime();

      boolean validInput = false;
      while (!validInput) {
        System.out.println("Please enter your check-in date in the format yyyy-MM-dd:");
        boolean validCheckIn = false;
        while (!validCheckIn) {
          String checkInDateInput = sc.next();
          try {
            checkInDate = simpleDateFormat.parse(checkInDateInput);
            Date todayDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            if (checkInDate.after(todayDate) || checkInDate.equals(todayDate))
              validCheckIn = true;
            else
              System.out.println("The check-in date must be in the future. Please try again.");
          } catch (Exception e) {
            System.out.println("Please enter your check-in date in the format yyyy-MM-dd:");
          }
        }

        System.out.println("Please enter your check-out date in the format yyyy-MM-dd:");
        boolean validCheckOut = false;
        while (!validCheckOut) {
          String checkOutDateInput = sc.next();
          try {
            checkOutDate = simpleDateFormat.parse(checkOutDateInput);
            validCheckOut = true;
          } catch (Exception e) {
            System.out.println("Please enter your check-out date in the format yyyy-MM-dd:");
          }
        }

        if (checkInDate.before(checkOutDate)) {
          validInput = true;
        } else {
          System.out.println("The check-in date must be before the check-out date.");
        }
      }

    // Listing available rooms
    System.out.println("Available rooms from " + simpleDateFormat.format(checkInDate) + " to " + simpleDateFormat.format(checkOutDate));
    System.out.println("——————————");
    Collection<IRoom> availableRooms = HotelResource.getInstance().findARoom(checkInDate, checkOutDate);
    if (availableRooms.isEmpty()) {
        System.out.println("There are no available rooms during this date range.\n" +
          "How many days would you like to postpone your stay?");
        int days = 7;
        boolean validDayInput = false;
        while (!validDayInput) {
          try {
            days = sc.nextInt();
            if (days >= 1) {
              validDayInput = true;
            } else {
              System.out.println("Please enter a valid number of days.");
            }
          } catch (Exception e) {
            System.out.println("Please enter a valid number of days.");
            sc.nextLine();
          }
        }
        checkInDate = HotelResource.getInstance().incrementDate(checkInDate, days);
        checkOutDate = HotelResource.getInstance().incrementDate(checkOutDate, days);
        availableRooms = HotelResource.getInstance().findARoom(checkInDate, checkOutDate);
        if (availableRooms.isEmpty()) {
          System.out.println("There are no available rooms from "  + simpleDateFormat.format(checkInDate) + " to " + simpleDateFormat.format(checkOutDate));
          return;
        } else {
          System.out.println("Recommended rooms from " + simpleDateFormat.format(checkInDate) + " to " + simpleDateFormat.format(checkOutDate));
          System.out.println("—————————");
          availableRooms.forEach(room -> System.out.println(room + "\n—————————"));
        }
    } else {
      availableRooms.forEach(room -> System.out.println(room + "\n—————————"));
    }

    // Asking for room number
    System.out.println("If you would like to make a reservation, please enter a room number from the options above.\n" +
      "Otherwise, please enter any other key to exit back to the Main Menu,");

    IRoom room;
      String roomNumberInput = sc.next();
      try {
        room = HotelResource.getInstance().getRoom(roomNumberInput);
        if (availableRooms.contains(room)) {
          System.out.println("Room information:");
          System.out.println(room);
        } else {
          System.out.println("Transaction aborted");
          return;
        }
      } catch (Exception e) {
        System.out.println("Transaction aborted");
        return;
    }

    // Booking confirmation
    System.out.println("Would you like to book this room? Please enter Y or N.");
    boolean bookingBool = false;
      validInput = false;
      while (!validInput) {
        String bookingInput = sc.next().toLowerCase();
        if (bookingInput.equals("y") || bookingInput.equals("yes")) {
          bookingBool = true;
          validInput = true;
        } else if (bookingInput.equals("n") || bookingInput.equals("no")) {
          bookingBool = false;
          validInput = true;
        } else {
          System.out.println("Please enter Y or N.");
        }
      }

    if (!bookingBool) {
      System.out.println("Transaction aborted");
      return;
    }

    // Asking for account information
    System.out.println("Please enter your email address. \nPlease ensure that you have created an account with this email address.");
    String emailInput;
      emailInput = sc.next();
      try {
        AdminResource.getCustomer(emailInput);
      } catch (Exception e) {
        System.out.println("Invalid email. Transaction aborted.");
        return;
      }

    // Booking confirmation
    System.out.println("Reservation booked!\n—————————");
    try {
      System.out.println(HotelResource.getInstance().bookARoom(emailInput, room, checkInDate, checkOutDate));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Prints all reservations relevant to a user, based on their email address.
   */
  public static void option2() {
    System.out.println("Please enter your email address. \nPlease ensure that you have created an account with this email address.");
    String emailInput;
      emailInput = sc.next();
      try {
        AdminResource.getCustomer(emailInput);
      } catch (Exception e) {
        System.out.println("Invalid email. Process aborted.");
        return;
      }
    HotelResource.getInstance().getCustomersReservations(emailInput).forEach(reservation -> System.out.println(reservation + "\n——————————"));
  }

  /**
   * Creates a new account.
   */
  public static void option3() {
    System.out.println("Please enter your first name.");
    String firstName = sc.next();
    System.out.println("Please enter your last nane.");
    String lastName = sc.next();
    boolean validInput = false;
    while (!validInput) {
      try {
        System.out.println("Please enter your email address in the format (...)@(...).com.");
        String email = sc.next();
        HotelResource.getInstance().createACustomer(firstName, lastName, email);
        validInput = true;
        System.out.println("Your account has been created!");
        System.out.println("Account Summary\n—————————");
        System.out.println(HotelResource.getInstance().getCustomer(email));
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /**
   * Opens the Admin Menu.
   */
  public static void option4() {
    AdminMenu.open();
  }
}
