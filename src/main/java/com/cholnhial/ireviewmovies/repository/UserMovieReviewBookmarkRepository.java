package com.cholnhial.ireviewmovies.repository;


import com.cholnhial.ireviewmovies.model.UserMovieReviewBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserMovieReviewBookmarkRepository extends JpaRepository<UserMovieReviewBookmark, Long> {
    Set<UserMovieReviewBookmark> findAllByUserId(Long userId);
}
