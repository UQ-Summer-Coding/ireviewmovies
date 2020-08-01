package com.cholnhial.ireviewmovies.service;

import com.cholnhial.ireviewmovies.model.Movie;
import com.cholnhial.ireviewmovies.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MovieService {

    private final MovieRepository movieRepository;

    /**
     * Finds a single movie in the database that has been created
     * to keep track of reviews
     *
     * @param tMDbMovieId the TMDb movie ID
     * @return an optional with the movie if found or empty otherwise
     */
    public Optional<Movie> findByTMDbMovieId(Long tMDbMovieId) {
        return movieRepository.findByTMDbMovieId(tMDbMovieId);
    }

    /**
     * Returns a paginated list of all movies reviewed
     *
     * @param pageable pagination information
     * @return list of pageable movies
     */
    public Page<Movie> findAll(String query, Pageable pageable) {
        return movieRepository.findAll(query, pageable);
    }

    /**
     * Returns a paginated list of all movies reviewed
     *
     * @return list of pageable movies
     */
    public Page<Movie> findAll(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    /**
     * Persists a movie entity to the DB
     * @param movie the movie to be saved
     * @return the movie just persisted
     */
    @Transactional
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }
}
