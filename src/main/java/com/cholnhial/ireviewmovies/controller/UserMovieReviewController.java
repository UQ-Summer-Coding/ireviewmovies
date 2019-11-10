package com.cholnhial.ireviewmovies.controller;

import com.cholnhial.ireviewmovies.model.MovieReview;
import com.cholnhial.ireviewmovies.model.User;
import com.cholnhial.ireviewmovies.service.MovieReviewService;
import com.cholnhial.ireviewmovies.service.UserService;
import com.cholnhial.ireviewmovies.service.dto.MovieReviewDTO;
import com.cholnhial.ireviewmovies.service.mapper.MovieReviewMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/user/")
@AllArgsConstructor
public class UserMovieReviewController {

    private final MovieReviewService movieReviewService;
    private final MovieReviewMapper movieReviewMapper;
    private final UserService userService;

    @GetMapping("my-reviews")
    public String index(@RequestParam("page") Optional<Integer> requestedPage,
                        @RequestParam("size") Optional<Integer> requestedPageSize,
                        Model model) {

        int currentPage = requestedPage.orElse(1);
        int pageSize = requestedPageSize.orElse(3);

        User currentUser = userService.getCurrentLoggedInUser();
        Page<MovieReview> movieReviewPage = this.movieReviewService.findAllByUserId(currentUser.getId(), PageRequest.of(currentPage - 1, pageSize));
        Page<MovieReviewDTO> movieReviewDtosPage = movieReviewPage.map(movieReviewMapper::movieReviewToMovieReviewDto);

        model.addAttribute("movieReviewsPage", movieReviewDtosPage);
        return "my-reviews";
    }
}

