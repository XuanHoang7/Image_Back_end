package com.datn.monolithic.payload.response;

import lombok.Data;

@Data
public class Res {
    private int status;
    private String message;
    private String url;
}
