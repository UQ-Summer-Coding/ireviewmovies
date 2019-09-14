package com.cholnhial.ireviewmovies.service;

import com.cholnhial.ireviewmovies.model.User;
import com.cholnhial.ireviewmovies.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

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

}
