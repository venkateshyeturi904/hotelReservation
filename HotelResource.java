package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class HotelResource {
    private static HotelResource hotelResource;
    private HotelResource()
    {

    }
    public static HotelResource getInstance()
    {
        if(hotelResource == null)
            hotelResource = new HotelResource();
        return hotelResource;
    }
    ReservationService reserveService = ReservationService.getInstance();
    CustomerService customerService = CustomerService.getInstance();
    public Customer getCustomer(String email)
    {

        return customerService.getCustomer(email);
    }
    public void createACustomer(String email,String firstName, String lastName)
    {
        customerService.addCustomer(email,firstName,lastName);

    }
    public IRoom getRoom(String roomNumber)
    {

        return reserveService.getARoom(roomNumber);
    }
    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate)
    {
        return  reserveService.reserveARoom(getCustomer(customerEmail),room,checkInDate,checkOutDate);
    }
    public Collection<Reservation> getCustomerReservations(String customerEmail)
    {

        return reserveService.getCustomersReservation(getCustomer(customerEmail));
    }
    public Collection<IRoom> findARoom(Date checkIn, Date checkOut)
    {

        return  reserveService.findRooms(checkIn,checkOut);
    }
    public Collection<Customer> getAllCustomers()
    {

        return customerService.getAllCustomers();
    }
    public Collection<String> getAllEmails()
    {
        return customerService.getCustomerEmails();
    }


}
