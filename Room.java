package model;

import groovy.transform.ToString;

public class Room  implements IRoom{
    public String roomNumber;
    public Double price;
    public RoomType enumeration;
    public Room(String roomNumber, Double price, RoomType enumeration)
    {
        this.roomNumber = roomNumber;
        this.enumeration = enumeration;
        this.price = price;
    }

    @Override public String toString()
    {
        return "Room Number "+ getRoomNumber() + " "+ getRoomType() + " Bed "+ " Price "+ getRoomPrice()+ "$" ;
    }

    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public double getRoomPrice() {
        return this.price;
    }

    @Override
    public RoomType getRoomType() {

        return this.enumeration;
    }

    @Override
    public boolean isFree() {

        if(this.price == 0)
            return true;
        else
            return false;
    }
}
