package com.bridgelabz.order.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class UserModel {

    public long Id;
    public String firstName;


    public String lastName;

    public String kyc;


    public String dob;


    public LocalDate registerDate = LocalDate.now();


    public LocalDate updatedDate;


    public String emailId;


    public String password;


    public boolean verify = false;

    public long otp;

    public LocalDate purchaseDate;

    public UserModel () {}
}
