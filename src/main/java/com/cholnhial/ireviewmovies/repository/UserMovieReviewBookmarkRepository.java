package com.cholnhial.ireviewmovies.repository;


import com.cholnhial.ireviewmovies.model.UserMovieReviewBookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserMovieReviewBookmarkRepository extends JpaRepository<UserMovieReviewBookmark, Long> {
    Page<UserMovieReviewBookmark> findAllByUserId(Long userId, Pageable pageable);

    @Query("select umrb from UserMovieReviewBookmark as umrb WHERE umrb.user.id=:userId and umrb.movieReview.id=:reviewId")
    Optional<UserMovieReviewBookmark> findOneByUserIdAndReviewId(@Param("userId") Long userId,@Param("reviewId") Long reviewId);
}
