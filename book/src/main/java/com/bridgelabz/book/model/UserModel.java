package com.bridgelabz.book.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.stereotype.Component;

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
