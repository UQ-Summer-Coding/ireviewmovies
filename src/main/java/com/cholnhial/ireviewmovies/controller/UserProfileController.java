package com.cholnhial.ireviewmovies.controller;

import com.cholnhial.ireviewmovies.model.User;
import com.cholnhial.ireviewmovies.service.UserService;
import com.cholnhial.ireviewmovies.service.dto.UserProfileDTO;
import com.cholnhial.ireviewmovies.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserProfileController {
    private final UserService userService;

    @ModelAttribute("user")
    public UserProfileDTO userProfileDto() {
        Authentication authentication = SecurityUtil.getAuthentication();
       Optional<User> user =  userService.findByEmail(authentication.getName());
        return new UserProfileDTO(
                user.get().getFullName(),
                user.get().getEmail(),
                "",
                "",
                Optional.ofNullable(user.get().getProfileImage()).orElse("/img/default-profile-pic.png"));
    }

    @GetMapping("/profile")
    public String index(Model model) {
        return "profile";
    }

    @PostMapping("/profile")
    public ModelAndView updateProfile(@Valid @ModelAttribute("user")  UserProfileDTO profileDto,
                                      BindingResult result,
                                      Model model) {
        Optional<User> user = this.userService.findByEmail(profileDto.getEmail());
        ModelAndView modelAndView = new ModelAndView("");

        if (result.hasErrors()) {
            modelAndView.setViewName("profile :: form");
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            return modelAndView;
        }

        user.ifPresent(u -> {
            u.setFullName(profileDto.getFullName());
            if (!profileDto.getConfirmPassword().isEmpty()) {
                u.setPassword(profileDto.getPassword());
            }
            u.setProfileImage(profileDto.getProfileImageBase64());
            this.userService.save(u);
        });

        modelAndView.setViewName("profile");
        modelAndView.setStatus(HttpStatus.OK);

        return modelAndView;
    }


}
