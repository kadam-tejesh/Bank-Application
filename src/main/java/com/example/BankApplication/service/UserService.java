package com.example.BankApplication.service;

import com.example.BankApplication.DTO.LoginDetailsDTO;
import com.example.BankApplication.DTO.UserResponseDTO;
import com.example.BankApplication.entity.User;
import com.example.BankApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JWTService service;
    public UserResponseDTO addUser(User u){
        u.setPassword(encoder.encode(u.getPassword()));
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

    public String verify(LoginDetailsDTO login){
        Authentication authentication=manager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(),login.getPassword()));
        if(authentication.isAuthenticated()){
           return service.generateToken(login.getEmail());
        }
        return "fail";
    }


}
