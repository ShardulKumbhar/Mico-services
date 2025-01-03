package com.bridgelabz.user.service;

import com.bridgelabz.user.dto.ForgetPasswordDTO;
import com.bridgelabz.user.dto.LoginDTO;
import com.bridgelabz.user.dto.UserDTO;
import com.bridgelabz.user.model.UserModel;
import com.bridgelabz.user.util.Response;

import java.util.List;

import javax.validation.Valid;

public interface IUserService {


    Response registerUserData(@Valid UserDTO dto);

    Response loginData( @Valid LoginDTO login);

    List<UserModel> getAllUsers(String token);

    Response updateUser(UserDTO dto, String token);

    Response deleteUser(String token);

//    Response verifyUser(Long otp);
//
    Response forgotPassword(ForgetPasswordDTO forgotPasswordDTO);

    Response getUserById(Long id);
}