package com.ead.authuser.domain.model.forms;

import com.ead.authuser.validation.UsernameConstraint;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserForm {

    @NotBlank
    @Size(min = 4, max = 50)
    @UsernameConstraint
    private String userName;

    @NotBlank
    @Email
    @Size(min = 4, max = 50)
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    private String fullName;

    private String phoneNumber;

    private String cpf;

}
