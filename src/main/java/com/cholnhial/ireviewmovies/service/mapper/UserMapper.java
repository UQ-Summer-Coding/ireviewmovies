package com.cholnhial.ireviewmovies.service.mapper;

import com.cholnhial.ireviewmovies.model.Role;
import com.cholnhial.ireviewmovies.model.User;
import com.cholnhial.ireviewmovies.model.enumeration.RoleType;
import com.cholnhial.ireviewmovies.repository.RoleRepository;
import com.cholnhial.ireviewmovies.service.dto.UserSignUpDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserMapper {

    private final RoleRepository roleRepository;

    public User UserSignUpDTOToUser(UserSignUpDTO userSignUpDTO) {
        User user = new User();
        user.setEmail(userSignUpDTO.getEmail());
        user.setFullName(userSignUpDTO.getFullName());
        user.setPassword(userSignUpDTO.getPassword());
        Optional<Role> userRole = roleRepository.findByRole(RoleType.ROLE_USER);
        userRole.ifPresent(r -> user.setRoles(Set.of(r)));

        return user;
    }

}
