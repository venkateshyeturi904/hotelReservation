package ui;

import api.AdminResource;
import api.HotelResource;
import model.*;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.*;

public class AdminMenu {
    HotelResource hotelResource = HotelResource.getInstance();
    AdminResource adminResource = AdminResource.getInstance();

    Scanner adminChoiceScanner = new Scanner(System.in);
    public void seeCustomers()
    {
        Collection<Customer> customers = hotelResource.getAllCustomers();
        if(!customers.isEmpty()) {
            System.out.println("All Customers");
            for (Customer customer : customers) {
                System.out.println("First Name: " + customer.firstName + " " + "Last Name: " + customer.lastName + " " + "Email: " + customer.email);
            }
        }
        else
        {
            System.out.println("No customers found");
        }

    }
    public void seeRooms()
    {
        Collection<IRoom> allRooms = adminResource.getAllRooms();
        if(!allRooms.isEmpty()) {
            System.out.println("All Rooms");
            for (IRoom room : allRooms) {
                System.out.println(room.toString());
            }

        }
        else
        {
            System.out.println("No Rooms Found");
        }
    }
    public void addRoom()
    {
        List<IRoom> rooms = new ArrayList<IRoom>();

        Map<String,IRoom> roomRecords = adminResource.getIndex();
        Set<String> localRoomNumbers = new HashSet<>();
        String roomNumber;
        Double price;
        int roomType;
        boolean stopFlag = false;
        try {
            while (!stopFlag) {

                System.out.println("Enter room Number");
                roomNumber = adminChoiceScanner.next();
                if(roomRecords.size()!=0) {
//                    System.out.println("Entering iffff condition.....");
                    while (roomRecords.get(roomNumber) != null && localRoomNumbers.contains(roomNumber) ) {
                        System.out.println("room Number already exists. Please enter another room number");
                        roomNumber = adminChoiceScanner.next();

                    }
                    localRoomNumbers.add(roomNumber);
                }
                else
                {

                    while (localRoomNumbers.contains(roomNumber) ) {
//                        System.out.println("duplicate rooom number");
                        System.out.println("room Number already exists. Please enter another room number");
                        roomNumber = adminChoiceScanner.next();

                    }
                    localRoomNumbers.add(roomNumber);
                }
                System.out.println("Enter price per night");
                price = adminChoiceScanner.nextDouble();

                try
                {

                    while(price<0) {
                        System.out.println("Price cant be negative");
                        price = adminChoiceScanner.nextDouble();
                    }

                }
                catch(Exception e)
                {
                    System.out.println("Enter a valid price");
                    price = adminChoiceScanner.nextDouble();
                }

                System.out.println("Enter room type: 1 for single bed, 2 for double bed");
                roomType = adminChoiceScanner.nextInt();

                while (!(roomType == 1 || roomType == 2)) {
                    try {
                        System.out.println("Enter 1 or 2");
                        roomType = adminChoiceScanner.nextInt();
                    }
                    catch(Exception e)
                    {
                        System.out.println("Enter a valid room type");
                    }
                }
                RoomType type;
                if (roomType == 1) {
                    type = RoomType.Single;
                } else {
                    type = RoomType.Double;
                }
                if(price == 0)
                {
                    FreeRoom fr = new FreeRoom(roomNumber,type);
                    rooms.add(fr);
                }
                else {
                    Room r = new Room(roomNumber, price, type);
                    rooms.add(r);
                }
                System.out.println("Would you like to add another room Y or N");
                String decision = adminChoiceScanner.next();
                while (!(decision.equals("Y") || decision.equals("y") || decision.equals("n") || decision.equals("N"))) {
                    System.out.println("Please enter y or n ");
                    decision = adminChoiceScanner.next();
                }
                if (decision.equals("N") || decision.equals("n")) {
                    stopFlag = true;
                }
            }
            adminResource.addRoom(rooms);

        }
        catch (InputMismatchException e)
        {
            System.out.println("Please enter valid input and try again");
            adminChoiceScanner.nextLine();
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }
    public void display() {

        while (true) {
            System.out.println("-----------------------------------------------");
            System.out.println("                     Admin Menu                ");
            System.out.println("-----------------------------------------------");
            System.out.println("1.See All Customers");
            System.out.println("2.See all Rooms");
            System.out.println("3.See all Reservations");
            System.out.println("4.Add a Room");
            System.out.println("5.Back to Main Menu");
            System.out.println("Please choose an action");
            int adminChoice =0;
            try {
                adminChoice = adminChoiceScanner.nextInt();
            }
            catch (InputMismatchException e)
            {
                adminChoiceScanner.nextLine();
            }

                switch (adminChoice) {
                    case 1:
                        seeCustomers();
                        break;
                    case 2:
                        seeRooms();
                        break;
                    case 3:
                        adminResource.displayAllReservations();
                        break;
                    case 4:
                        addRoom();
                        break;
                    case 5:
                        new MainMenu().display();
                        break;
                    default:
                        System.out.println("Please select a service from the below");
                        break;
                }



        }
    }
}
