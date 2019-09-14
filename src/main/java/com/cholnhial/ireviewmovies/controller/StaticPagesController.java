package com.cholnhial.ireviewmovies.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticPagesController {

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

}
