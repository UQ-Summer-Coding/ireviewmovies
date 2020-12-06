package com.cholnhial.ireviewmovies.service;

import com.cholnhial.ireviewmovies.model.UserMovieReviewBookmark;
import com.cholnhial.ireviewmovies.repository.UserMovieReviewBookmarkRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserMovieReviewBookmarkService {

    private final UserMovieReviewBookmarkRepository userMovieReviewBookmarkRepository;

    /**
     *  Returns a list of the user's movie bookmark reviews
     *
     * @return A set containing the user's bookmark reviews
     */
    public Set<UserMovieReviewBookmark> findMovieReviewBookmarksByUserId(Long userId) {
        return userMovieReviewBookmarkRepository.findAllByUserId(userId);
    }
}
