package com.cholnhial.ireviewmovies.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserDTO {

    @Email
    private String email;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

}
