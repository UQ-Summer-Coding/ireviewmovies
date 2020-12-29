package com.cholnhial.ireviewmovies.service;

import com.cholnhial.ireviewmovies.model.MovieReview;
import com.cholnhial.ireviewmovies.model.User;
import com.cholnhial.ireviewmovies.model.UserMovieReviewBookmark;
import com.cholnhial.ireviewmovies.repository.UserMovieReviewBookmarkRepository;
import com.cholnhial.ireviewmovies.service.exception.AlreadyBookmarkedException;
import com.cholnhial.ireviewmovies.service.exception.BookmarkNotFoundException;
import com.cholnhial.ireviewmovies.service.exception.MovieReviewNotFoundException;
import com.cholnhial.ireviewmovies.service.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserMovieReviewBookmarkService {

    private final UserMovieReviewBookmarkRepository userMovieReviewBookmarkRepository;
    private final MovieReviewService movieReviewService;
    private final UserService userService;

    /**
     *  Returns a list of the user's movie bookmark reviews
     *
     * @return A page containing the user's bookmark reviews
     */
    public Page<UserMovieReviewBookmark> findMovieReviewBookmarksByUserId(Long userId, Pageable pageable) {
        return userMovieReviewBookmarkRepository.findAllByUserId(userId, pageable);
    }

    /**
     *  Checks whether a review has already been bookmarked for the logged in user
     * @param userId the user that owns the bookmark
     * @param reviewId the review to check
     */
    public boolean isReviewAlreadyBookmarked(Long userId, Long reviewId) {
        return  userMovieReviewBookmarkRepository
               .findOneByUserIdAndReviewId(userId, reviewId)
               .isPresent();
    }

    /**
     *
     *  Adds a bookmark to the user's movie review bookmarks
     *
     * @param userId the user who will own the bookmark
     * @param reviewId the review id to bookmark
     */
    @Transactional(rollbackOn = {AlreadyBookmarkedException.class, MovieReviewNotFoundException.class})
    public UserMovieReviewBookmark bookmarkReview(Long userId, Long reviewId)
            throws UserNotFoundException, AlreadyBookmarkedException, MovieReviewNotFoundException {
        UserMovieReviewBookmark bookmark = new UserMovieReviewBookmark();
        User user = userService
                .findOneById(userId)
                .orElseThrow(() -> new UserNotFoundException("Unable to find user."));
        bookmark.setUser(user);
        MovieReview review = movieReviewService
                .findOneById(reviewId)
                .orElseThrow(() -> new MovieReviewNotFoundException("The specified movie review can't be located"));
       bookmark.setMovieReview(review);

       if(isReviewAlreadyBookmarked(userId, reviewId)) {
           throw new AlreadyBookmarkedException("The user has already bookmarked this review");
       }

       bookmark.setCreated(ZonedDateTime.now());
       return userMovieReviewBookmarkRepository.save(bookmark);
    }

    /**
     * Remove a previously bookmarked review.
     * @param userId the user who owns the bookmark
     * @param reviewId the id of the review to remove the bookmark of
     */
    public void removeBookmark(Long userId, Long reviewId) throws BookmarkNotFoundException {
       UserMovieReviewBookmark bookmark =  userMovieReviewBookmarkRepository
                .findOneByUserIdAndReviewId(userId, reviewId)
                .orElseThrow(() -> new BookmarkNotFoundException("Unable to find bookmark."));

       userMovieReviewBookmarkRepository.delete(bookmark);
    }
}
