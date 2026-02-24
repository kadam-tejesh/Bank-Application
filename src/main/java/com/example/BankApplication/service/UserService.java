package com.example.BankApplication.service;

import com.example.BankApplication.DTO.UserResponseDTO;
import com.example.BankApplication.entity.User;
import com.example.BankApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO addUser(User u){
        User saved=userRepository.save(u);
        UserResponseDTO userDetails= new UserResponseDTO();
        userDetails.setId(saved.getId());
        userDetails.setName(saved.getName());
        userDetails.setEmail(saved.getEmail());
        userDetails.setRole(saved.getRole());
        return userDetails;
    }
    public List<UserResponseDTO> getUsers(){
        List<User> users=userRepository.findAll();
        List<UserResponseDTO> responses=new ArrayList<>();
        for(User u:users){
            UserResponseDTO userDetails= new UserResponseDTO();
            userDetails.setId(u.getId());
            userDetails.setName(u.getName());
            userDetails.setEmail(u.getEmail());
            userDetails.setRole(u.getRole());
            responses.add(userDetails);
        }
        return responses;
    }


}
