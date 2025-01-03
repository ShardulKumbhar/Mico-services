package com.bridgelabz.user.model;
import java.time.LocalDate;
import com.bridgelabz.user.dto.UserDTO;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "user")
public @Data class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    public long Id;

    @Column(name = "firstname")
    public String firstName;

    @Column(name = "lastname")
    public String lastName;

    public String kyc;

    @Column(name = "dob")
    public String dob;

    @Column(name = "registerDate")
    public LocalDate registerDate = LocalDate.now();

    @Column(name = "updateddate")
    public LocalDate updatedDate;

    @Column(name = "emailid")
    public String emailId;

    @Column(name = "password")
    public String password;

    @Column(name = "verification")
    public boolean verify = false;

    public long otp;

    public LocalDate purchaseDate;

//	public LocalDate expiryDate;

    public UserModel(long id, String firstName, String lastName, String kyc, String dob, LocalDate registerDate,
                     LocalDate updatedDate, String emailId, String password, boolean verify, long otp, LocalDate purchaseDate) {
        super();
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.kyc = kyc;
        this.dob = dob;
        this.registerDate = registerDate;
        this.updatedDate = updatedDate;
        this.emailId = emailId;
        this.password = password;
        this.verify = verify;
        this.otp = otp;
        this.purchaseDate = purchaseDate;
//		this.expiryDate = expiryDate;
    }


    public UserModel(UserDTO dto) {

        this.firstName = dto.firstName;
        this.lastName = dto.lastName;
        this.kyc = dto.kyc;
        this.dob = dto.dob;
        this.emailId = dto.emailId;
        this.password = dto.password;
    }

    public UserModel(UserModel user, String newPassword) {
        this.Id = user.Id;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.kyc = user.kyc;
        this.dob = user.dob;
        this.registerDate = user.registerDate;
        this.updatedDate = user.updatedDate;
        this.emailId = user.emailId;
        this.password = newPassword;
        this.verify = user.verify;
        this.otp = user.otp;
        this.purchaseDate = user.purchaseDate;
//		this.expiryDate = user.expiryDate;
    }

    public UserModel () {}
}
