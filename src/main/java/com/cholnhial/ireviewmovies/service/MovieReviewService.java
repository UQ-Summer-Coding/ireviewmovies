package com.cholnhial.ireviewmovies.service;

import com.cholnhial.ireviewmovies.model.MovieReview;
import com.cholnhial.ireviewmovies.repository.MovieReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class MovieReviewService {

    private final UserService userService;
    private final MovieReviewRepository movieReviewRepository;

    public MovieReview save(MovieReview movieReview) {

        // new entity
        if(movieReview.getId() == null) {
            movieReview.setCreatedDateTime(ZonedDateTime.now());
            movieReview.setUser(userService.getCurrentLoggedInUser());
        }

      return  this.movieReviewRepository.save(movieReview);
    }

    public Page<MovieReview> findAllByUserId(Long userId, Pageable pageable) {
        return this.movieReviewRepository.findAllByUserId(userId, pageable);
    }

    public Optional<MovieReview> findOneById(Long id) {
        return this.movieReviewRepository.findById(id);
    }

}
