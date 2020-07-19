package com.cholnhial.ireviewmovies.service.dto;

import com.cholnhial.ireviewmovies.validator.FieldMatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
})
public class UserProfileDTO {

    @NotEmpty
    private String fullName;

    @NotEmpty
    private String email;


    private String password;

    private String confirmPassword;

    private String profileImageBase64;
}
