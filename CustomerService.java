package service;

import model.Customer;

import java.util.ArrayList;
import java.util.Collection;

public class CustomerService {
//    private static CustomerService customerservice;
    public static Collection<Customer> customers = new ArrayList<Customer>();
    public static Collection<String> customerEmails = new ArrayList<>();
    private static CustomerService customerService;
    private CustomerService()
    {

    }
    public static CustomerService getInstance()
    {
        if(customerService == null)
            customerService = new CustomerService();
        return customerService;
    }
    public  void addCustomer(String email, String firstName, String lastName)
    {
        try {
            customers.add(new Customer(firstName, lastName,email));
            System.out.println("Account created succesfully");
            customerEmails.add(email);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Invalid email address");
        }
    }
    public Customer getCustomer(String customerEmail)
    {
        for(Customer customer: customers)
        {
            if(customer.email.equals(customerEmail))
                return customer;
        }
        return null;
    }
    public static Collection<Customer> getAllCustomers()
    {

        return customers;
    }
    public Collection<String> getCustomerEmails()
    {
        return customerEmails;
    }

}
