package com.cholnhial.ireviewmovies.service.mapper;

import com.cholnhial.ireviewmovies.model.MovieReview;
import com.cholnhial.ireviewmovies.service.dto.MovieReviewDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieReviewMapper {

    public MovieReview movieReviewDTOToMovieReview(MovieReviewDTO dto) {
        MovieReview movieReview = new MovieReview();
        movieReview.setId(dto.getId());
        movieReview.setRating(dto.getRating());
        movieReview.setLikes(dto.getLikes());
        movieReview.setTMDBMovieId(dto.getTMDBMovieId());
        movieReview.setTitle(dto.getTitle());
        movieReview.setTMDBMoviePosterPath(dto.getTMDBMoviePosterPath());
        movieReview.setReviewText(dto.getReviewText());
        movieReview.setCreatedDateTime(dto.getCreated());

        return movieReview;
    }

    public MovieReviewDTO movieReviewToMovieReviewDto(MovieReview movieReview) {
        MovieReviewDTO movieReviewDto = new MovieReviewDTO();
        movieReviewDto.setId(movieReview.getId());
        movieReviewDto.setTMDBMoviePosterPath(movieReview.getTMDBMoviePosterPath());
        movieReviewDto.setTMDBMovieId(movieReview.getTMDBMovieId());
        movieReviewDto.setReviewText(movieReview.getReviewText());
        movieReviewDto.setTitle(movieReview.getTitle());
        movieReviewDto.setLikes(movieReview.getLikes());
        movieReviewDto.setRating(movieReview.getRating());
        movieReviewDto.setCreated(movieReview.getCreatedDateTime());
        movieReviewDto.setUserFullName(movieReview.getUser().getFullName());
        movieReviewDto.setUserProfileImage(movieReview.getUser().getProfileImage());

        return movieReviewDto;
    }

    public List<MovieReviewDTO> movieReviewsToMovieReviewDtos(List<MovieReview> movieReviews) {
        return movieReviews.stream()
                .map(this::movieReviewToMovieReviewDto)
                .collect(Collectors.toList());
    }
}
