package com.cholnhial.ireviewmovies.repository;

import com.cholnhial.ireviewmovies.model.MovieReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieReviewRepository extends JpaRepository<MovieReview, Long> {
    Page<MovieReview> findAllByUserId(Long userId, Pageable pageable);

    Page<MovieReview> findAllBytMDBMovieId(Long movieId, Pageable pageable);

    @Query("select avg(m.rating) from MovieReview m " +
            "where m.tMDBMovieId = :movieId group by m.tMDBMovieId")
    Float getAverageRatingByTMDBMovieId(@Param("movieId") Long TMdbMovieId);
}
