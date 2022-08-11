package model;
import java.util.Date;

public class Reservation {
    public  Customer customer;
    public IRoom room;
    public Date checkInDate;
    public Date checkOutDate;
    @Override
    public String toString()
    {

        return customer.firstName+ " " +room.toString()+" "+ "CheckIn Date "+ this.checkInDate.toString() + "Checkout Date "+ this.checkOutDate.toString();
    }

}
