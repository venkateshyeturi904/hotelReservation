package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Customer {
    public String firstName;
    public String lastName;
    public String email;
    public Customer(String customerFirstName, String customerLastName, String customerEmail)
    {
        this.firstName = customerFirstName;
        this.lastName = customerLastName;
        this.email = customerEmail;
        final String regex = "[a-z0-9]+@[a-z]+\\.[a-z]{2,3}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches())
        {
            throw new IllegalArgumentException();
        }
    }
    @Override
    public String toString()
    {
        return "First Name: "+ this.firstName + " "+ "Last Name: "+ this.lastName+" "+ "Email: "+ this.email;
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.hashCode() == this.hashCode())
            return true;
        else
            return false;
    }
}
