package com.cholnhial.ireviewmovies.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class UserLoginController {

    @GetMapping
    public String login(Model model) {
        return "login";
    }
}
