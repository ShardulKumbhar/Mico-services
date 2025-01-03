package com.bridgelabz.user.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;


import com.bridgelabz.user.dto.ForgetPasswordDTO;
import com.bridgelabz.user.dto.LoginDTO;
import com.bridgelabz.user.dto.UserDTO;
import com.bridgelabz.user.exception.BookStoreException;
import com.bridgelabz.user.model.UserModel;
import com.bridgelabz.user.repository.UserRegistrationRepository;
import com.bridgelabz.user.util.OtpGenerator;
import com.bridgelabz.user.util.Response;
import com.bridgelabz.user.util.TokenUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements IUserService {

    @Autowired
    public UserRegistrationRepository userRegistrationRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    private OtpGenerator otpGenerator;
//
//
//    @Autowired
//    private EmailService emailService;

    Long otp;

    Optional<UserModel> user;



    @Override
    public Response loginData(@Valid LoginDTO login) {
        user = userRegistrationRepository.findByemailIdAndPassword(login.getEmailId(), login.getPassword());
        if(user.isPresent()) {
            String token= tokenUtil.createToken(user.get().getId());
            return new Response(200, "Token Created Succefully", token);
        }
//        else if (user.isPresent() && user.get().verify==false) {
//            try {
//                otp = otpGenerator.generateOTP();
//                user.get().setOtp(otp);
//                String requestUrl = "http://localhost:8081/user/verify/email/" + otp;
//                System.out.println("the generated otp is " + otp);
//                emailService.sendEmail(
//                        login.getEmailId(),
//                        "Please verify your account",
//                        requestUrl+"\n Your generated otp is "
//                                +otp+
//                                " click on the link above to verify your account");
//                return new Response("Otp generated successfully", user);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        throw new BookStoreException(400, "No such User Found");
    }

    @Override
    public List<UserModel> getAllUsers(String token)
    {
        long id = tokenUtil.decodeToken(token);
        Optional<UserModel> isContactPresent = userRegistrationRepository.findById(id);
        if(isContactPresent.isPresent()) {
            List<UserModel> getUsers=userRegistrationRepository.findAll();
            return getUsers;
        }
        else {

            throw new BookStoreException(400,"Token not valid");
        }
    }

    @Override
    public Response getUserById(Long id) {
        Optional<UserModel> user = userRegistrationRepository.findById(id);
        if(user.isPresent()){
            return new Response(200, "User found", user);
        }else{
            throw new BookStoreException(404,"user Not found");
        }
    }

    @Override
    public Response updateUser(UserDTO dto, String token) {
        long id = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent = userRegistrationRepository.findById(id);
        if(isUserPresent.isPresent()) {
            isUserPresent.get().setFirstName(dto.getFirstName());
            isUserPresent.get().setLastName(dto.getLastName());
            isUserPresent.get().setDob(dto.getDob());
            isUserPresent.get().setKyc(dto.getKyc());
            isUserPresent.get().setEmailId(dto.getEmailId());
            isUserPresent.get().setPassword(dto.getPassword());
            isUserPresent.get().setUpdatedDate(LocalDate.now());
//			isUserPresent.get().setExpiryDate(dto.getExpiryDate());
            UserModel data = userRegistrationRepository.save(isUserPresent.get());
            return new Response(200, "User updated successfully", data);
        }
        else {

            throw new BookStoreException(404,"user Not found");
        }
    }

//	@Override
//	public Response registerUserData(UserDTO dto)
//
//	{
//		UserModel userModel = modelMapper.map(dto,UserModel.class);
//		userRegistrationRepository.save(userModel);
//		return new Response(200, "Saved Succefully", null);
//	}


    @Override
    public Response registerUserData(UserDTO dto)
    {

        Optional<UserModel> isPresent = userRegistrationRepository.findByEmailId(dto.getEmailId());
        if(isPresent.isPresent())
        {
            throw new BookStoreException(400,"User already exists. Email:"+dto.getEmailId());
        }else
        {
            UserModel userEntity = modelMapper.map(dto,UserModel.class);
            UserModel data = userRegistrationRepository.save(userEntity);
            //EmailService.send( dto.getEmailId(), "subject",  "body");
            return new Response(200, "Saved Succefully", data);
        }
    }


    @Override
    public Response deleteUser(String token)
    {
        long id = tokenUtil.decodeToken(token);
        System.out.println("ide checked"+id);
        Optional<UserModel> isUserPresent = userRegistrationRepository.findById(id);
        System.out.println("checked"+isUserPresent);
        if(isUserPresent.isPresent()) {
            userRegistrationRepository.delete(isUserPresent.get());

            return new Response(200, "User deleted successfully", null);
        }
        else {

            throw new BookStoreException(404,"User not found");
        }
    }

//    @Override
//    public Response verifyUser(Long mailOtp) {
//        if(mailOtp.equals(otp)&&user.get().isVerify()==false) {
//            System.out.println(user);
//            user.get().setVerify(true); //setting verification to true after verification
//            //update the userData with is verified value
//            userRegistrationRepository.save(user.get());
//            return new Response("User verified successfully!", user);
//        } else if (user.get().isVerify()&&mailOtp.equals(otp)) {
//            return new Response("otp already verified no need to verify again", user);
//        }
//        return new Response("Invalid otp", "please enter correct otp");
//    }

    @Override
    public Response forgotPassword(ForgetPasswordDTO forgotPasswordDTO)
    {
        if(forgotPasswordDTO.getNewPassword().equals(forgotPasswordDTO.getConfirmPassword()))
        {
            UserModel user = userRegistrationRepository.findByEmailId(forgotPasswordDTO.getEmailId()).get();
            String updatedPassword = userRegistrationRepository.save(new UserModel(user, forgotPasswordDTO.getConfirmPassword())).getPassword();
            return new Response (200,"Password Change Successfull", updatedPassword);
        }else {
            return new Response(400, "Password and Confirm Password Do Not Match", null);
        }
    }


}



