package model;
import java.util.regex.*;

/**
 * Represents a customer.
 */

public class Customer {
  /**
   * the customer's first name
   */
  private String firstName;
  /**
   * the customer's last name
   */
  private String lastName;
  /**
   * the customer's email address in the format "(...)@(...).com"
   */
  private String email;

  /**
   * Constructor for a customer.
   * @param firstName the customer's first name
   * @param lastName the customer's last name
   * @param email the customer's email address in the format "(...)@(...).com"
   * @throws IllegalArgumentException if the email address does not match the format "(...)@(...).com"
   */
  public Customer(String firstName, String lastName, String email) {
    String validEmailRegEx = "^(.+)@(.+).com";
    Pattern validEmailPattern = Pattern.compile(validEmailRegEx);
    if (!validEmailPattern.matcher(email).matches()) {
      throw new IllegalArgumentException("Invalid email format: the email must be in the format \"(...)@(...).com\".");
    } else {
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email.toLowerCase();
    }
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String toString() {
    return "First name: " + firstName + " | Last name: " + lastName + " | Email: " + email;
  }
}
