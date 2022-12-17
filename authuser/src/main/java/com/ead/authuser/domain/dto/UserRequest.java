package com.ead.authuser.domain.dto;

import lombok.Data;

@Data
public class UserRequest {

    private String username;

    private String email;

    private String password;

    private String oldPassword;

    private String fullName;

    private String phoneNumber;

    private String cpf;

    private String imageUrl;


}
