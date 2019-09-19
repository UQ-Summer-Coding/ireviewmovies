package com.cholnhial.ireviewmovies.controller;

import com.cholnhial.ireviewmovies.model.User;
import com.cholnhial.ireviewmovies.service.UserService;
import com.cholnhial.ireviewmovies.service.dto.UserSignUpDTO;
import com.cholnhial.ireviewmovies.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/signup")
@AllArgsConstructor
public class UserSignUpController {

    private final UserService userService;
    private final UserMapper userMapper;

    @ModelAttribute("user")
    public UserSignUpDTO userSignUpDto() {
        return new UserSignUpDTO();
    }

    @GetMapping
    public String getSignUpPage(Model model) {
        return "signup";
    }

    @PostMapping
    public String signUpUser(@Valid @ModelAttribute("user")  UserSignUpDTO userSignUpDto,
                             BindingResult result,
                             Model model) {
        Optional<User> user = this.userService.findByEmail(userSignUpDto.getEmail());

        if(user.isPresent()) {
            result.rejectValue("email", null, "That email address is taken!");
        }

        if(result.hasErrors()) {
            return "signup";
        }

        this.userService.save(userMapper.UserSignUpDTOToUser(userSignUpDto));

        return "redirect:/signup?success";
    }
}
