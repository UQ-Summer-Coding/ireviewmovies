package com.cholnhial.ireviewmovies.controller;

import com.cholnhial.ireviewmovies.model.MovieReview;
import com.cholnhial.ireviewmovies.model.User;
import com.cholnhial.ireviewmovies.service.MovieReviewService;
import com.cholnhial.ireviewmovies.service.TMDbMovieService;
import com.cholnhial.ireviewmovies.service.UserService;
import com.cholnhial.ireviewmovies.service.dto.MovieReviewDTO;
import com.cholnhial.ireviewmovies.service.dto.TMDbMovieDTO;
import com.cholnhial.ireviewmovies.service.mapper.MovieReviewMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/user/")
@AllArgsConstructor
public class UserMovieReviewController {

    private final MovieReviewService movieReviewService;
    private final MovieReviewMapper movieReviewMapper;
    private final TMDbMovieService tmDbMovieService;
    private final UserService userService;

    @GetMapping("my-reviews")
    public String index(@RequestParam("page") Optional<Integer> requestedPage,
                        @RequestParam("size") Optional<Integer> requestedPageSize,
                        Model model) {

        int currentPage = requestedPage.orElse(1);
        int pageSize = requestedPageSize.orElse(10);

        User currentUser = userService.getCurrentLoggedInUser();
        Page<MovieReview> movieReviewPage = this.movieReviewService.findAllByUserId(currentUser.getId(), PageRequest.of(currentPage - 1, pageSize));
        Page<MovieReviewDTO> movieReviewDtosPage = movieReviewPage.map(movieReviewMapper::movieReviewToMovieReviewDto);

        model.addAttribute("movieReviewsPage", movieReviewDtosPage);
        return "my-reviews";
    }

    @GetMapping("my-reviews/{id}")
    public String getMovieReviewDetails(@PathVariable Long id, Model model) {

        //TODO: Redirect to 404 if not found
        Optional<MovieReview> movieReviewOptional = this.movieReviewService.findOneById(id);

        TMDbMovieDTO movie = this.tmDbMovieService.getMovieById(movieReviewOptional.get().getTMDBMovieId());
        model.addAttribute("movieReview", movieReviewOptional.get());
        model.addAttribute("movie", movie);
        return "movie-review-details-modal :: modalContents";
    }

    @GetMapping("my-reviews/{id}/delete")
    public String getMovieReviewDeleteModal(@PathVariable Long id, Model model) {
        Optional<MovieReview> movieReviewOptional = this.movieReviewService.findOneById(id);
        model.addAttribute("movieReview", movieReviewOptional.get());
        return "movie-review-delete-modal :: modalContents";
    }

    @PostMapping("my-reviews/{id}/delete")
    public String deleteMovieReview(@PathVariable Long id) {
        this.movieReviewService.deleteMovieReviewById(id);
        return "redirect:/user/my-reviews";
    }
}

