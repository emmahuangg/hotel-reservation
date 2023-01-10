import java.util.*;
import api.*;
import model.*;

public class AdminMenu {
  private final static Scanner sc = new Scanner(System.in);
  private static boolean dataPopulated = false;
  public static void open() {
    while (true) {
      if (!dataPopulated) {
        System.out.println("Admin menu" +
          "\n——————————\n" +
          "1. See all customers\n" +
          "2. See all rooms\n" +
          "3. See all reservations\n" +
          "4. Add a room\n" +
          "5. Back to Main Menu\n" +
          "6. Populate application with test data");
        int choice = 5;
        boolean validInput = false;
        while (!validInput) {
          try {
            choice = sc.nextInt();
            if (1 <= choice && choice <= 6) {
              validInput = true;
            } else {
              System.out.println("Please enter a number between 1 and 6.");
            }
          } catch (Exception e) {
            System.out.println("Please enter a number between 1 and 6.");
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
          case 6:
            option6();
            dataPopulated = true;
            break;
          default:
            return;
        }
      } else {
        System.out.println("Admin menu" +
          "\n——————————\n" +
          "1. See all customers\n" +
          "2. See all rooms\n" +
          "3. See all reservations\n" +
          "4. Add a room\n" +
          "5. Back to Main Menu");
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
            return;
        }
      }
    }
  }

  /**
   * Prints all customers.
   */
  public static void option1() {
    System.out.println("Customer List\n—————————");
    AdminResource.getInstance().getAllCustomers().forEach(customer -> System.out.println(customer + "\n—————————"));
  }

  /**
   * Prints all rooms.
   */
  public static void option2() {
    System.out.println("Room List\n—————————");
    AdminResource.getInstance().getAllRooms().forEach(room -> System.out.println(room + "\n—————————"));
  }

  /**
   * Prints all reservations.
   */
  public static void option3() {
    System.out.println("Reservations List\n—————————");
    AdminResource.getInstance().displayAllReservations();
  }

  /**
   * Accepts user input to add rooms.
   * Rooms are continuously added until terminated by the user.
   * New rooms must have unique room numbers; otherwise, they will not be added.
   */
  public static void option4() {
    List<IRoom> rooms = new ArrayList<IRoom>();
    boolean terminate = false;
    while (!terminate) {
      System.out.println("Please enter a new room number");
      String roomNumberInput = sc.next();
      System.out.println("Please enter the price for this room.");
      boolean validPriceInput = false;
      Double priceInput = null;
      while (!validPriceInput) {
        try {
          priceInput = sc.nextDouble();
          if (priceInput >= 0) {
            validPriceInput = true;
          }
        } catch (Exception e) {
          System.out.println("Please enter a valid price for this room.");
          sc.nextLine();
        }
      }

      System.out.println("Please select the room type from the options below.");
      RoomType roomType = RoomType.DOUBLE;
      int counter = 1;
      for (RoomType roomtype : RoomType.values()) {
        System.out.println(String.valueOf(counter) +". " + roomtype.toString());
        counter++;
      }
      boolean validTypeInput = false;
      while (!validTypeInput) {
        try {
          int typeInput = sc.nextInt();
          switch (typeInput) {
            case 1:
              roomType = RoomType.SINGLE;
              validTypeInput = true;
              break;
            case 2:
              roomType = RoomType.DOUBLE;
              validTypeInput = true;
              break;
            default:
              System.out.println("Please enter a number between 1 and 2.");
          }
        } catch (Exception e) {
          System.out.println("Please enter a number between 1 and 2.");
        }
      }

      // Adding this room to the list
      IRoom room;
      if (priceInput == 0.0) {
        room = new FreeRoom(roomNumberInput, roomType);
      } else {
        room = new Room(roomNumberInput, priceInput, roomType);
      }
      rooms.add(room);

      // Loop termination
      System.out.println("Would you like to add more rooms? Please enter Y or N.");
      boolean validInput = false;
      while (!validInput) {
        String input = sc.next().toLowerCase();
        if (input.equals("y") || input.equals("yes")) {
          terminate = false;
          validInput = true;
        } else if (input.equals("n") || input.equals("no")) {
          terminate = true;
          validInput = true;
        } else {
          System.out.println("Please enter Y or N.");
        }
      }
    }
    try {
      AdminResource.getInstance().addRoom(rooms);
    } catch (Exception e) {
      System.out.println("One or more rooms could not be added, as a conflicting room number already exists.\n" +
        "Please select option 2 to view all added and existing rooms.");
    }
  }

  /**
   * Populates the program with a series of test data
   */
  public static void option6() {
    try {
      AdminResource.getInstance().populateProgram();
      System.out.println("Test data has been added to the program.");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
