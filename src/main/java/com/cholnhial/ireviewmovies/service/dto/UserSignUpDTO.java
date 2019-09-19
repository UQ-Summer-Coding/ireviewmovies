package com.cholnhial.ireviewmovies.service.dto;

import com.cholnhial.ireviewmovies.validator.FieldMatch;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
        @FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")
})
public class UserSignUpDTO {

    @NotEmpty
    private String fullName;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Email
    private String confirmEmail;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;
}
