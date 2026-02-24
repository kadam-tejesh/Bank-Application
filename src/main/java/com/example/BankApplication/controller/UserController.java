package com.example.BankApplication.controller;

import com.example.BankApplication.DTO.UserResponseDTO;
import com.example.BankApplication.entity.User;
import com.example.BankApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(path="/User",consumes={"application/json"})
    public UserResponseDTO addUser(@RequestBody User u){
        return userService.addUser(u);
    }
    @GetMapping("/Users")
    public List<UserResponseDTO> getUsers(){
        return userService.getUsers();
    }

}
