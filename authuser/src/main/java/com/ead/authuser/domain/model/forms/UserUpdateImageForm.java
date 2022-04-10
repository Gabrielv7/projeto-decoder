package com.ead.authuser.domain.model.forms;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateImageForm {

    @NotBlank
    private String imageUrl;

}
