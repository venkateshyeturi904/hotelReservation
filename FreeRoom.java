package model;

public class FreeRoom extends  Room{
    public FreeRoom(String roomNumber, RoomType enumeration)
    {
        super(roomNumber,0.0,enumeration);
        this.price = Double.valueOf(0);
    }
    @Override
    public String toString()
    {

        return "Room Number: "+ this.getRoomNumber() + " Room Type: "+ this.getRoomType() + " Price: "+ 0  ;
    }
}
