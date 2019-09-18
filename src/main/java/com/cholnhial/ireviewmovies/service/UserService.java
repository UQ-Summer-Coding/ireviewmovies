package com.cholnhial.ireviewmovies.service;

import com.cholnhial.ireviewmovies.model.Role;
import com.cholnhial.ireviewmovies.model.User;
import com.cholnhial.ireviewmovies.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Find a user by username
     *
     * @param username the username
     * @return Returns a optional with user inside if any
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     *  Find a user by their email
     *
     * @param email the user's email
     * @return Optional with user inside or empty if none found
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
       User user = userRepository.findByUsername(s)
               .orElseThrow(() -> new UsernameNotFoundException("User Not found"));
        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(user.getUsername(),
                        user.getPassword(), this.mapRolesToAuthorities(user.getRoles()));

        return userDetails;
    }

    private Collection <? extends GrantedAuthority > mapRolesToAuthorities(Collection <Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());
    }
}
