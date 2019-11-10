package com.cholnhial.ireviewmovies.controller;

import com.cholnhial.ireviewmovies.model.MovieReview;
import com.cholnhial.ireviewmovies.service.MovieReviewService;
import com.cholnhial.ireviewmovies.service.TMDbMovieService;
import com.cholnhial.ireviewmovies.service.dto.MovieReviewDTO;
import com.cholnhial.ireviewmovies.service.dto.TMDbMovieDTO;
import com.cholnhial.ireviewmovies.service.mapper.MovieReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class MovieReviewController {

    private final TMDbMovieService tmDbMovieService;
    private final MovieReviewMapper movieReviewMapper;
    private final MovieReviewService movieReviewService;


    @ModelAttribute("movieReview")
    public MovieReviewDTO movieReviewDto() { return new MovieReviewDTO(); }

    @GetMapping("/{tmdbMovieId}/new")
    public String newReview(Model model, @PathVariable(required = true) Long tmdbMovieId) {
        TMDbMovieDTO movie = tmDbMovieService.getMovieById(tmdbMovieId);
        model.addAttribute("movie", movie);
        return "reviews/new";
    }

    @PostMapping("/save-new")
    public String saveNewMovieReview(@Valid @ModelAttribute("movieReview") MovieReviewDTO movieReviewDto,
                                       BindingResult result,
                                       Model model) {

        // ID should been set in form
        TMDbMovieDTO movie = tmDbMovieService.getMovieById(movieReviewDto.getTMDBMovieId());

        if(result.hasErrors()) {
            model.addAttribute("movie", movie);
            return "reviews/new";
        }

        // set the movie poster path
        movieReviewDto.setTMDBMoviePosterPath(movie.getPosterPath());
        MovieReview movieReviewToSave = movieReviewMapper.movieReviewDTOToMovieReview(movieReviewDto);
        movieReviewToSave.setLikes(0); // no likes yet
        movieReviewToSave.setTitle(movie.getTitle());
        this.movieReviewService.save(movieReviewToSave);
        return "redirect:/user/my-reviews";
    }

}
