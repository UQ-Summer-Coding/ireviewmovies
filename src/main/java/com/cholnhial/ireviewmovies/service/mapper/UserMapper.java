package com.cholnhial.ireviewmovies.service.mapper;

import com.cholnhial.ireviewmovies.model.Role;
import com.cholnhial.ireviewmovies.model.User;
import com.cholnhial.ireviewmovies.model.enumeration.RoleType;
import com.cholnhial.ireviewmovies.service.dto.UserSignUpDTO;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserMapper {

    public User UserSignUpDTOToUser(UserSignUpDTO userSignUpDTO) {
        User user = new User();
        user.setEmail(userSignUpDTO.getEmail());
        user.setFullName(userSignUpDTO.getFullName());
        user.setPassword(userSignUpDTO.getPassword());
        user.setRoles(Set.of(new Role(RoleType.ROLE_USER)));

        return user;
    }

}
