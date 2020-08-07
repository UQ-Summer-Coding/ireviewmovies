package com.cholnhial.ireviewmovies.controller;

import com.cholnhial.ireviewmovies.model.Movie;
import com.cholnhial.ireviewmovies.model.MovieReview;
import com.cholnhial.ireviewmovies.service.MovieReviewService;
import com.cholnhial.ireviewmovies.service.MovieService;
import com.cholnhial.ireviewmovies.service.TMDbMovieService;
import com.cholnhial.ireviewmovies.service.dto.MovieReviewDTO;
import com.cholnhial.ireviewmovies.service.dto.TMDbMovieDTO;
import com.cholnhial.ireviewmovies.service.mapper.MovieReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Optional;

@Controller
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class MovieReviewController {

    private final TMDbMovieService tmDbMovieService;
    private final MovieReviewMapper movieReviewMapper;
    private final MovieReviewService movieReviewService;
    private final MovieService movieService;


    @ModelAttribute("movieReview")
    public MovieReviewDTO movieReviewDto() { return new MovieReviewDTO(); }

    @GetMapping("/{tmdbMovieId}/new")
    public String newReview(Model model, @PathVariable(required = true) Long tmdbMovieId) {
        TMDbMovieDTO movie = tmDbMovieService.getMovieById(tmdbMovieId);
        model.addAttribute("movie", movie);
        return "reviews/new";
    }

    @GetMapping("{movieReviewId}/edit")
    public String editReview(Model model, @PathVariable Long movieReviewId) {
        Optional<MovieReview> movieReviewOptional = movieReviewService.findOneById(movieReviewId);
        model.addAttribute("movieReview", this.movieReviewMapper.movieReviewToMovieReviewDto(movieReviewOptional.get()));
        TMDbMovieDTO movie = tmDbMovieService.getMovieById(movieReviewOptional.get().getTMDBMovieId());
        model.addAttribute("movie", movie);
        return "movie-review-edit-modal :: modalContents";
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


        /*
         * Locate an instance of a movie already saved or create new.
         * This is used to display the reviewed movies on the home page
         */
        movieService.findByTMDbMovieId(movieReviewToSave.getTMDBMovieId()).ifPresentOrElse(m -> {
            m.setTotalReviews(m.getTotalReviews() + 1);
            m.setAverageRating(movieReviewService.getAverageRatingByTMDBMovieId(movieReviewDto.getTMDBMovieId()));
            movieService.save(m);
        }, () -> {
            Movie newMovie = new Movie();
            newMovie.setTotalReviews(1L);
            newMovie.setYear(movie.getReleaseDate().getLong(ChronoField.YEAR));
            newMovie.setName(movie.getTitle());
            newMovie.setDescription(movie.getOverview());
            newMovie.setAverageRating(movieReviewService.getAverageRatingByTMDBMovieId(movieReviewDto.getTMDBMovieId()));
            newMovie.setTMDBMovieId(movieReviewDto.getTMDBMovieId());
            newMovie.setTMDBMoviePosterPath(movieReviewDto.getTMDBMoviePosterPath());
            newMovie.setCreatedDateTime(ZonedDateTime.now());
            movieService.save(newMovie);
        } );

        return "redirect:/user/my-reviews";
    }

    @PostMapping("/save-edit")
    public ModelAndView saveEditedMovieReview(@Valid @ModelAttribute("movieReview") MovieReviewDTO movieReviewDto,
                                              BindingResult result) {

        ModelAndView modelAndView = new ModelAndView();

        if(result.hasErrors()) {
            // ID should been set in form
            TMDbMovieDTO movie = tmDbMovieService.getMovieById(movieReviewDto.getTMDBMovieId());
            modelAndView.addObject("movie", movie);
            modelAndView.setViewName("movie-review-edit-modal :: modalContents");
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            return modelAndView;
        }

        Optional<MovieReview> existingMovieReview = this.movieReviewService.findOneById(movieReviewDto.getId());
        existingMovieReview.ifPresent(review -> {
            review.setReviewText(movieReviewDto.getReviewText());
            review.setRating(movieReviewDto.getRating());
            this.movieReviewService.save(review);
        });

        modelAndView.setStatus(HttpStatus.OK);
        modelAndView.setViewName("redirect:/user/my-reviews");
        return modelAndView;
    }

    @GetMapping("/{movieId}")
    public String getMovieReviews(@PathVariable Long movieId,
                                  @RequestParam("page") Optional<Integer> requestedPage,
                                  @RequestParam("size") Optional<Integer> requestedPageSize,
                                  Model model) {

        int currentPage = requestedPage.orElse(1);
        int pageSize = requestedPageSize.orElse(3);

        Page<MovieReview> movieReviewPage = this.movieReviewService.findAllByTMDBMovieId(movieId, PageRequest.of(currentPage - 1, pageSize));
        Page<MovieReviewDTO> reviews = movieReviewPage.map(movieReviewMapper::movieReviewToMovieReviewDto);
        TMDbMovieDTO movie = this.tmDbMovieService.getMovieById(movieId);
        model.addAttribute("movie", movie);
        model.addAttribute("reviews", reviews);

        return "reviews";
    }
}
