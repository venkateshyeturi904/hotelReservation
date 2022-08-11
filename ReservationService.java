package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import java.util.*;
import static java.lang.Math.min;

public class ReservationService {
    private static ReservationService ReservationServiceObject;
    public static List<Reservation> Reservations = new ArrayList<Reservation>();
    public static List<IRoom> rooms = new ArrayList<IRoom>();
    public static Map<Customer,List<Reservation>> customerReservations = new HashMap<Customer,List<Reservation>>();


    public static Map<String,IRoom> roomIndex = new HashMap<String,IRoom>();
     private ReservationService() {

    }
    public static ReservationService getInstance()
    {
        if(ReservationServiceObject == null)
             ReservationServiceObject = new ReservationService();
        return ReservationServiceObject;

    }
    public void addRoom(IRoom room)
    {

        rooms.add(room);
        roomIndex.put(room.getRoomNumber(),room);
    }
    public IRoom getARoom(String roomId)
    {
        for(IRoom room : rooms)
        {
            if(room.getRoomNumber().equals(roomId))
                return room;
        }
        return null;
    }
    public List<IRoom> getAllRooms()
    {

        return rooms;
    }
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate)
    {
        Reservation r = new Reservation();
        r.customer = customer;
        r.room = room;
        r.checkInDate = checkInDate;
        r.checkOutDate = checkOutDate;
        Reservations.add(r);

        if(customerReservations.get(customer) == null)
        {
            List<Reservation> reservation = new ArrayList<Reservation>();
            reservation.add(r);
            customerReservations.put(customer,reservation);
        }
        else
        {
            customerReservations.get(customer).add(r);
        }
        return r;
    }
    public List<IRoom>  findRooms(Date checkInDate, Date checkoutDate)
    {
        Map<String,IRoom> unavailableRooms = new HashMap<String,IRoom>();
        List<IRoom> availableRooms = new ArrayList<IRoom>();
        for(Reservation reservation : Reservations)
        {
            Date s1 = reservation.checkInDate;
            Date s2 = reservation.checkOutDate;
            Date e1 = checkInDate;
            Date e2 = checkoutDate;
            if(checkOverlapDates(checkInDate,checkoutDate,reservation.checkInDate,reservation.checkOutDate))
            {
                unavailableRooms.put(reservation.room.getRoomNumber(),reservation.room);

            }


        }
        for(IRoom room: rooms)
        {
            if(!unavailableRooms.containsKey(room.getRoomNumber()))
            {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }
    public Map<String,IRoom> getRoomIndex()
    {
        return roomIndex;
    }
    public List<Reservation> getCustomersReservation(Customer customer)
    {
        if(customerReservations.get(customer) != null)
        {
            return customerReservations.get(customer);
        }

        return null;
    }
    public void printAllReservation()
    {
        int serialNumber = 1;
        if(!Reservations.isEmpty()) {
            System.out.println("All Reservations of the hotel");
            System.out.println("-----------------------------------------");
            for (Reservation reservation : Reservations) {
                System.out.println("Reservation " + String.valueOf(serialNumber));
                System.out.println(reservation.customer.firstName + reservation.customer.lastName);
                System.out.println("Room: " + reservation.room.getRoomNumber() + " - " + reservation.room.getRoomType() + " Bed");
                System.out.println("CheckIn Date : " + reservation.checkInDate.toString());
                System.out.println("CheckOut Date :" + reservation.checkOutDate.toString());
                serialNumber = serialNumber + 1;
            }
        }
        else
            System.out.println("No Reservations Found");
    }
    //default method
    boolean checkOverlapDates(Date startA, Date endA, Date startB, Date endB)
    {
        long firstMin = min(endA.getTime()-startA.getTime(),endA.getTime()-startB.getTime());
        long secondMin = min(endB.getTime()-startB.getTime(), endB.getTime()-startA.getTime());
        long finalMin = min(firstMin,secondMin);
        if(finalMin>0)
            return true;
        else
            return false;

    }

}
