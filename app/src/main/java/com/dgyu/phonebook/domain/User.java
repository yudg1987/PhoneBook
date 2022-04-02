package com.dgyu.phonebook.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    private int age;
    private String sex;
    private int pic;
    private String phone;
}
