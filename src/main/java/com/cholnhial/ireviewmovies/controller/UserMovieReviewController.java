package com.cholnhial.ireviewmovies.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
public class UserMovieReviewController {

    @GetMapping("my-reviews")
    public String index(Model model) {
        return "my-reviews";
    }
}

