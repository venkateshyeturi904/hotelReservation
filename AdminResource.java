package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.List;
import java.util.Collection;
import java.util.Map;


public class AdminResource {
    private static AdminResource adminResource;
    private AdminResource(){}
    public static AdminResource getInstance()
    {
        if(adminResource == null)
            adminResource = new AdminResource();
        return adminResource;
    }
    ReservationService reserveService = ReservationService.getInstance();
    CustomerService customerService = CustomerService.getInstance();
    public Customer getCustomer(String email)
    {
        return customerService.getCustomer(email);
    }
    public void addRoom(List<IRoom> rooms)
    {
        for(IRoom room:rooms)
        {
            reserveService.addRoom(room);
        }

    }
    public Collection<IRoom> getAllRooms()
    {
        return reserveService.getAllRooms();
    }

    public void displayAllReservations()
    {

        reserveService.printAllReservation();
    }
    public Map<String ,IRoom> getIndex()
    {
        return reserveService.getRoomIndex();
    }

}
