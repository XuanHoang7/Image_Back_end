package com.datn.monolithic.controller;

import com.datn.monolithic.dto.UserDTO;
import com.datn.monolithic.payload.response.LoginMessage;
import com.datn.monolithic.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/user")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    @PostMapping(path ="/signup")
    public boolean signup(@RequestBody UserDTO user) {
        return userService.addUser(user);
    }


    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody UserDTO user) {
        LoginMessage loginMessage = userService.login(user);
        return ResponseEntity.ok(loginMessage);
    }
}
