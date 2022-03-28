package com.ead.authuser.domain.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserUpdateImageForm {

    @NotBlank
    private String imageUrl;

}
