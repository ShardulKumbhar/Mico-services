package com.bridgelabz.user.controller;
import java.util.List;

import javax.validation.Valid;

import com.bridgelabz.user.dto.LoginDTO;
import com.bridgelabz.user.dto.UserDTO;
import com.bridgelabz.user.model.UserModel;
import com.bridgelabz.user.service.IUserService;
import com.bridgelabz.user.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    IUserService userService;

//    @Autowired
//    private EmailService senderService;

    @GetMapping("/welcome")
    public String welcomeBook() {
        return "Welcome to book store";
    }

    @PostMapping("/register")
    public ResponseEntity<Response> registerUserData(@Valid @RequestBody UserDTO dto){
        Response userModel = userService.registerUserData(dto);
        return new ResponseEntity<Response>(userModel,HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<Response> login(@Valid @RequestBody LoginDTO login){
        Response response = userService.loginData(login);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/getUser")
    public ResponseEntity<List<?>> getAllUsers(@RequestHeader String token)
    {
        List<UserModel> userList = userService.getAllUsers(token);
        return new ResponseEntity<>(userList,HttpStatus.OK);
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable Long id)
    {
        Response user = userService.getUserById(id);
        return new ResponseEntity<Response>(user,HttpStatus.OK);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<Response> updateUser(@RequestBody UserDTO dto, String token){
        Response userEntity = userService.updateUser(dto, token);
        return new ResponseEntity<Response>(userEntity,HttpStatus.OK);

    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<Response> deleteUser(@RequestHeader String token){
        Response userEntity = userService.deleteUser(token);
        return new ResponseEntity<Response>(userEntity,HttpStatus.OK);
    }


}
