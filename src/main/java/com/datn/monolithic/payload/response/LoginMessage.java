package com.datn.monolithic.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginMessage {
    private String message;
    private Boolean status;

    public LoginMessage(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }
}
