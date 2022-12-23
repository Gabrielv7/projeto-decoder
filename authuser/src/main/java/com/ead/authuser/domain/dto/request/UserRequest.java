package com.ead.authuser.domain.dto.request;

import com.ead.authuser.validator.UsernameConstraint;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserRequest {

    public interface UserView {
        public static interface RegistrationPost {}
        public static interface UserPut {}
        public static interface PasswordPut {}
        public static interface ImagePut {}
    }

    @UsernameConstraint(groups = UserView.RegistrationPost.class)
    @JsonView({UserView.RegistrationPost.class})
    private String username;

    @NotBlank(groups = UserView.RegistrationPost.class, message = "{email-not-blank}")
    @Email(groups = UserView.RegistrationPost.class, message = "{email-invalid}")
    @JsonView({UserView.RegistrationPost.class})
    private String email;

    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class}, message = "{password-not-blank}")
    @Size(groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class}, min = 6, max = 20, message = "{password-size}")
    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    private String password;

    @NotBlank(groups = {UserView.PasswordPut.class}, message = "{old-password-not-blank}")
    @Size(groups = {UserView.PasswordPut.class}, min = 6, max = 20, message = "{old-password-size}")
    @JsonView({UserView.PasswordPut.class})
    private String oldPassword;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.UserPut.class}, message = "{full-name-not-blank}")
    private String fullName;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.UserPut.class}, message = "{phone-number-not-blank}")
    private String phoneNumber;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.UserPut.class}, message = "{cpf-not-blank}")
    private String cpf;

    @NotBlank(groups = UserView.ImagePut.class, message = "{image-not-blank}")
    @JsonView(UserView.ImagePut.class)
    private String imageUrl;

}
