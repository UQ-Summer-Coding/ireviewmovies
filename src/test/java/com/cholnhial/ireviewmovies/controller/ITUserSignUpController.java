package com.cholnhial.ireviewmovies.controller;

import com.cholnhial.ireviewmovies.model.User;
import com.cholnhial.ireviewmovies.service.UserService;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class ITUserSignUpController {

    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FlywayMigrationStrategy flywayMigrationStrategy;

    @Autowired
    private Flyway flyway;

    @BeforeEach
    public void setup() {
            mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
            flywayMigrationStrategy.migrate(flyway);
    }

    @Test
    public void whenSignUpPageRequested_shouldReturn200OkWithCorrectBody() throws Exception{
        this.mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Sign up")));
    }

    @Test
    public void whenSignUpFormIsCorrectlyFilledAndPosted_userShouldBeRegistered() throws Exception {

        // Register the user
        this.mockMvc.perform(post("/signup").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("fullName", "Chol Nhial")
                .param("email", "chol@official.com.au")
                .param("confirmEmail", "chol@official.com.au")
                .param("password", "1234")
                .param("confirmPassword", "1234"))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection());

        // Check user saved
        Optional<User> user = userService.findByEmail("chol@official.com.au");
        assertTrue(user.isPresent());
    }

    @Test
    public void whenSignUpFormPasswordsDoNotMatch_shouldReturnErrors() throws Exception {
        this.mockMvc.perform(post("/signup").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("fullName", "Chol Nhial")
                .param("email", "chol@official.com.au")
                .param("confirmEmail", "chol@official.com.au")
                .param("password", "123")
                .param("confirmPassword", "1234"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(content().string(containsString("The password fields must match")));
    }

    @Test
    void whenSignUpFormEmailsDoNotMatch_shouldReturnErrors() throws Exception {
        this.mockMvc.perform(post("/signup").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("fullName", "Chol Nhial")
                .param("email", "chol@official.com")
                .param("confirmEmail", "chol@official.com.au")
                .param("password", "123")
                .param("confirmPassword", "123"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(content().string(containsString("The email fields must match")));
    }

    @Test
    void whenSignUpFormEmailIsTaken_shouldReturnErrors() throws Exception {

     // Save an user first
        User user = new User();
        user.setEmail("chol@official.com.au");
        user.setFullName("Chol Nhial");
        user.setPassword("abcd123");
        userService.save(user);

        // Attempt to use old email
        this.mockMvc.perform(post("/signup").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("fullName", "Chol Nhial")
                .param("email", "chol@official.com.au")
                .param("confirmEmail", "chol@official.com.au")
                .param("password", "1234")
                .param("confirmPassword", "1234"))
                .andExpect(status().isOk())
        .andExpect(content().string(containsString("That email address is taken!")));

    }

}
