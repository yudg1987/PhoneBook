package com.dgyu.phonebook.model;

import lombok.Data;

@Data
public class User {
    private String account;
    private String password;
    private String phone;
}
