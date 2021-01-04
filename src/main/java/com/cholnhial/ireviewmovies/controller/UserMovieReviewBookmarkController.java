package com.cholnhial.ireviewmovies.controller;

import com.cholnhial.ireviewmovies.model.UserMovieReviewBookmark;
import com.cholnhial.ireviewmovies.service.UserMovieReviewBookmarkService;
import com.cholnhial.ireviewmovies.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/bookmarks")
@RequiredArgsConstructor
public class UserMovieReviewBookmarkController {

    private final UserMovieReviewBookmarkService userMovieReviewBookmarkService;
    private final UserService userService;


    @GetMapping("/")
    String  index(Model model, Pageable pageable) {
        Page<UserMovieReviewBookmark> bookmarks = userMovieReviewBookmarkService
                .findMovieReviewBookmarksByUserId(
                        userService.getCurrentLoggedInUser().getId(), pageable);
        model.addAttribute("bookmarks", bookmarks);
        return "bookmarks";
    }
}
