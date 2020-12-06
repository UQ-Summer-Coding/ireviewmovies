package com.cholnhial.ireviewmovies.repository;


import com.cholnhial.ireviewmovies.model.UserMovieReviewBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMovieReviewBookmarkRepository extends JpaRepository<UserMovieReviewBookmark, Long> {

}
