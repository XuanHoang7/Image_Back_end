package com.datn.monolithic.service;

import com.datn.monolithic.dto.UserDTO;
import com.datn.monolithic.entity.User;
import com.datn.monolithic.payload.response.LoginMessage;
import com.datn.monolithic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserMPL implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserMPL(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean addUser(UserDTO user) {
        User u = new User(
                user.getId(),
                user.getUsername(),
                this.passwordEncoder.encode(user.getPassword())
        );

        userRepository.save(u);
        return true;
    }

    @Override
    public LoginMessage login(UserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername());
        if (user != null) {
            String password = userDTO.getPassword();
            String encodedPassword = user.getPassword();
            boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<User> employee = userRepository.findOneByUsernameAndPassword(userDTO.getUsername(), encodedPassword);
                if (employee.isPresent()) {
                    return new LoginMessage("Login Success", true);
                } else {
                    return new LoginMessage("Login Failed", false);
                }
            } else {
                return new LoginMessage("Password not match", false);
            }
        } else {
            return new LoginMessage("Username not exits", false);
        }
    }
}
