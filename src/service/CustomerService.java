package service;
import java.util.*;

import api.HotelResource;
import model.*;

/**
 * Provides services for the customer.
 */
public class CustomerService {
  private static CustomerService instance = null;
  /**
   * a static collection (map) containing all customers.
   * The customer's email is the map's key, and the customer object is the map's value.
   */
  private static Map<String, Customer> customers = null;

  private CustomerService() {
    customers = new HashMap<String, Customer>();
  }

  public static CustomerService getInstance() {
    if (instance == null)
      instance = new CustomerService();
    return instance;
  }

  /**
   * Method for adding a customer to the customers collection.
   * @param firstName the customer's first name
   * @param lastName the customer's last name
   * @param email the customer's unique email address in the format "(...)@(...).com"
   * @throws Exception if the email provided is not unique
   */
  public static void addCustomer(String firstName, String lastName, String email) throws Exception {
    if (customers.containsKey(email.toLowerCase())) {
      throw new Exception("There exists a customer with this email address already.");
    } else {
      Customer customer = new Customer(firstName, lastName, email);
      customers.put(email.toLowerCase(), customer);
    }
  }

  /**
   * Method for retrieving a customer object based on their email address.
   * @param customerEmail the customer's email address
   * @return the customer object that matches the email address, if available
   * @throws Exception if the searched email does not exist in the customers collection
   */
  public static Customer getCustomer(String customerEmail) throws Exception {
    if (customers.get(customerEmail.toLowerCase()) == null) {
      throw new Exception("There is no existing customer with this email address.");
    } else {
      return customers.get(customerEmail.toLowerCase());
    }
  }

  /**
   * Produces a list of all existing customers.
   * @return a list of all existing customers
   */
  public static Collection<Customer> getAllCustomers() {
    return customers.values();
  }

  /**
   * Adds three sample customers to the program.
   */
  public static void addSampleCustomers() {
     customers.put("jj@gmail.com", new Customer("Jane", "Johnson", "jj@gmail.com"));
     customers.put("ss@gmail.com", new Customer("Steven", "Smith", "ss@gmail.com"));
     customers.put("rr@gmail.com", new Customer("Richard", "Roe", "rr@gmail.com"));
  }
}
