package com.cholnhial.ireviewmovies.service.mapper;

import com.cholnhial.ireviewmovies.model.MovieReview;
import com.cholnhial.ireviewmovies.service.dto.MovieReviewDTO;
import org.springframework.stereotype.Service;

@Service
public class MovieReviewMapper {

    public MovieReview movieReviewDTOToMovieReview(MovieReviewDTO dto) {
        MovieReview movieReview = new MovieReview();
        movieReview.setRating(dto.getRating());
        movieReview.setLikes(dto.getLikes());
        movieReview.setTMDBMovieId(dto.getTMDBMovieId());
        movieReview.setTMDBMoviePosterPath(dto.getTMDBMoviePosterPath());
        movieReview.setReviewText(dto.getReviewText());

        return movieReview;
    }
}
