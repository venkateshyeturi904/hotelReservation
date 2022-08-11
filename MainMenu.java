package ui;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import org.apache.groovy.groovysh.Main;
import service.ReservationService;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu {

    HotelResource hotelResource = HotelResource.getInstance();
    AdminResource adminResource = AdminResource.getInstance();
    Scanner choiceScanner = new Scanner(System.in);
    //default method
    boolean validateEmail(String email)
    {
        final String regex = "[a-z0-9]+@[a-z]+\\.[a-z]{2,3}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches())
        {
            return false;
        }
        return true;
    }
    public void findAndReserve()
    {

        try
        {
            System.out.println("Enter CheckIn Date mm/dd/yyyy example 02/01/2020");
            String checkInDate = choiceScanner.next();
            SimpleDateFormat dateParser = new SimpleDateFormat();
            dateParser.applyPattern("MM/dd/yyyy");
            dateParser.setLenient(false);
            Date inDate,outDate;
            while(true) {
                try {
                    inDate = dateParser.parse(checkInDate);
                    if(inDate.before(new Date())) {
                        System.out.println("Please enter a date which is either current date or later");
                        checkInDate = choiceScanner.next();
                    }
                    break;
                } catch (ParseException e) {
                    System.out.println("Invalid check in date. Please enter a valid date");
                    checkInDate = choiceScanner.next();
                }
            }
            System.out.println("Enter CheckOut Date mm/dd/yyyy example 10/01/2020");
            String checkOutDate = choiceScanner.next();
            while(true) {
                try {
                    outDate = dateParser.parse(checkOutDate);
                    if (inDate.after(outDate)) {
                        System.out.println("Checkout date must be after Checkin date. Please enter again");
                        checkOutDate = choiceScanner.next();
                    } else {
                        break;
                    }
                } catch (ParseException e) {
                    System.out.println("Invalid check out date.Please enter a valid checkout date");
                    checkOutDate = choiceScanner.next();
                }
            }
            System.out.println("Would you like to book a room y or n");
            String decision = choiceScanner.next();
            while(!(decision.equals("Y") || decision.equals("y") || decision.equals("n") || decision.equals("N")))
            {
                System.out.println("Please enter y or n ");
                decision  = choiceScanner.next();
            }
            if(decision.equals("y") || decision.equals("Y")){

                System.out.println("Do you have an account with us y or n");
                decision = choiceScanner.next();
                while(!(decision.equals("Y") || decision.equals("y") || decision.equals("n") || decision.equals("N")))
                {
                    System.out.println("Please enter y or n ");
                    decision  = choiceScanner.next();
                }
                if(decision.equalsIgnoreCase("Y"))
                {
//                                System.out.println("Reached email place");
                    System.out.println("Enter your email");

                    String customerEmail = choiceScanner.next();
                    while(validateEmail(customerEmail)==false)
                    {
                        System.out.println("Please enter a valid email address");
                        customerEmail = choiceScanner.next();
                    }
                    Customer target = adminResource.getCustomer(customerEmail);
                    if(target!=null && target.email.equals(customerEmail))
                    {
//                                    System.out.println("Reached inner loop");
                        Collection<IRoom> availableRoomSearch = hotelResource.findARoom(inDate,outDate);

                        if(availableRoomSearch.size() !=0) {
                            System.out.println("Showing the available rooms for the dates " + inDate.toString() + " to " + outDate.toString());
                            for (IRoom room : availableRoomSearch) {
                                System.out.println(room);

                            }
                        }

                        else
                        {
                            Calendar cal = Calendar.getInstance();
                            System.out.println("Sorry! There are no available rooms on the selected dates");
                            System.out.println("Enter the no days to add to search for alternative dates");
                            int padding = choiceScanner.nextInt();
                            while(padding <=0)
                            {
                                System.out.println("please give a positive alternative day value");
                                padding = choiceScanner.nextInt();

                            }
                            cal.setTime(inDate);
                            cal.add(Calendar.DAY_OF_MONTH, padding);
                            inDate = cal.getTime();
                            Calendar cal1 = Calendar.getInstance();
                            cal1.setTime(outDate);
                            cal1.add(Calendar.DAY_OF_MONTH,padding);
                            outDate = cal1.getTime();
                            System.out.println("Updated Dates " + inDate.toString() +" to " + outDate.toString());
                            availableRoomSearch = hotelResource.findARoom(inDate,outDate);
                            if(!availableRoomSearch.isEmpty()) {
                                System.out.println("Showing the recommended rooms as per alternative dates "+ inDate.toString() + "  " + outDate.toString());
                                for (IRoom room : availableRoomSearch) {
                                    System.out.println(room);

                                }
                            }
                            else {
                                System.out.println("Uh Oh! Sorry , No available rooms on alternative days as well");
                                new MainMenu().display();
                            }
                        }
                        System.out.println("What room number would you like to reserve");
                        String userRoomNumSelection = choiceScanner.next();
                        IRoom userSelectedRoom = hotelResource.getRoom(userRoomNumSelection);

                        while( Objects.isNull(userSelectedRoom) || !availableRoomSearch.contains(userSelectedRoom)) {
                            System.out.println("Above room number is not available. Please choose from the above listed rooms");
                            userRoomNumSelection = choiceScanner.next();
                            userSelectedRoom = hotelResource.getRoom(userRoomNumSelection);
                        }


                            Reservation userReservation = hotelResource.bookARoom(customerEmail, userSelectedRoom, inDate, outDate);
                            if(userReservation!=null ) {
                                System.out.println("Reservation");
                                System.out.println("---------------------------------------------");
                                System.out.println(userReservation.customer.firstName + userReservation.customer.lastName);
                                System.out.println("Room: " + userReservation.room.getRoomNumber() + " - " + userReservation.room.getRoomType() + " Bed");
                                System.out.println("Checkin Date : " + userReservation.checkInDate.toString());
                                System.out.println("Checkout Date :" + userReservation.checkOutDate.toString());
                            }
                            else
                            {
                                System.out.println("Please Enter a room number from the above and try again.");
                            }
                        }
                    else
                    {
                        System.out.println("No account found!!!.Please create account first");
                        createAccount();
                    }
                    }
                else
                {
                    System.out.println("Create an account with us first");
                    createAccount();
                }



                }
                else {
                    new MainMenu().display();
                }

            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }

        }






    public void seeReservations()
    {
        System.out.println("Enter your email");
        String customerEmail = choiceScanner.next();
        while(validateEmail(customerEmail) == false)
        {
            System.out.println("Please enter valid email");
            customerEmail = choiceScanner.next();
        }
        Customer target = adminResource.getCustomer(customerEmail);
        int serialNumber = 1;
        if(target!=null && target.email.equals(customerEmail)) {
            Collection<Reservation> userReservations = hotelResource.getCustomerReservations(customerEmail);

            if (userReservations!=null) {
                System.out.println("Reservations");
                System.out.println("--------------------------------------------");
                for (Reservation reservation : userReservations) {
                    System.out.println("Reservation " + String.valueOf(serialNumber));
                    System.out.println(reservation.customer.firstName + reservation.customer.lastName);
                    System.out.println("Room: " + reservation.room.getRoomNumber() + " - " + reservation.room.getRoomType() + " Bed");
                    System.out.println("Checkin Date : " + reservation.checkInDate.toString());
                    System.out.println("Checkout Date :" + reservation.checkOutDate.toString());
                    serialNumber = serialNumber + 1;
                    System.out.println("--------------------------------------------");
                }
            } else
            {
                System.out.println("No reservations found");
            }

        }
        else
        {
            System.out.println("Customer not found.Please create account to reserve rooms");
        }
    }
    public void createAccount()
    {
        String firstName,lastName,email;
        System.out.println("First Name");
        firstName = choiceScanner.next();
        System.out.println("Last Name");
        lastName = choiceScanner.next();
        System.out.println("Email Address");
        email = choiceScanner.next();
        Collection<String> allCustomerEmails = hotelResource.getAllEmails();


        boolean emailAlreadyExists = allCustomerEmails.contains(email);
        while(validateEmail(email) ==false || emailAlreadyExists)
        {

            if(emailAlreadyExists)
            {
                System.out.println("Email already exists. Please enter another email address");
            }
            else if(!validateEmail(email))
            {
                System.out.println("Enter a valid email address");
            }

            email = choiceScanner.next();
            emailAlreadyExists = allCustomerEmails.contains(email);

        }
        hotelResource.createACustomer(email,firstName,lastName);

    }
    public static void main(String args[])
    {
        MainMenu menu = new MainMenu();
        System.out.println("Welcome to Hotel Reservation System");
        menu.display();
    }

    public void display()
    {

        Scanner choiceScanner = new Scanner(System.in);
        while(true)
        {
            System.out.println("-----------------------------------------------");
            System.out.println("                     Main Menu                 ");
            System.out.println("-----------------------------------------------");
            System.out.println("1.Find and Reserve a Room");
            System.out.println("2.See my reservations");
            System.out.println("3.Create an Account");
            System.out.println("4.Admin");
            System.out.println("5.Exit");
            System.out.println("Please choose a service");
            int userChoice = 0;
            try {

                userChoice = choiceScanner.nextInt();

            }
            catch (InputMismatchException e)
            {
                choiceScanner.nextLine();
            }
            if(userChoice == 5 )
                System.out.println("Thanks for using our reservation system");
            switch(userChoice)
            {
                case 1:

                    findAndReserve();
                    break;
                case 2:
                    seeReservations();
                    break;
                case 3:
                    createAccount();
                    break;
                case 4:
                        new AdminMenu().display();
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Please choose a service from the list below");
                    break;
            }


        }

    }

}
