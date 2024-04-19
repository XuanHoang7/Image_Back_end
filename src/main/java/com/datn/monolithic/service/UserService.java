package com.datn.monolithic.service;

import com.datn.monolithic.dto.UserDTO;
import com.datn.monolithic.payload.response.LoginMessage;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    boolean addUser(UserDTO user);
    LoginMessage login(UserDTO userDTO);
}
