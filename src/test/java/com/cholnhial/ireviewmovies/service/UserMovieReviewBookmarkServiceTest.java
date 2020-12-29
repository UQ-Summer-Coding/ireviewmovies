package com.cholnhial.ireviewmovies.service;

import com.cholnhial.ireviewmovies.model.MovieReview;
import com.cholnhial.ireviewmovies.model.User;
import com.cholnhial.ireviewmovies.model.UserMovieReviewBookmark;
import com.cholnhial.ireviewmovies.repository.UserMovieReviewBookmarkRepository;
import com.cholnhial.ireviewmovies.service.exception.AlreadyBookmarkedException;
import com.cholnhial.ireviewmovies.service.exception.MovieReviewNotFoundException;
import com.cholnhial.ireviewmovies.service.exception.UserNotFoundException;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMovieReviewBookmarkServiceTest {

    @Autowired
    private UserMovieReviewBookmarkService userMovieReviewBookmarkService;

    @Autowired
    private FlywayMigrationStrategy flywayMigrationStrategy;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieReviewService movieReviewService;

    @Autowired
    private Flyway flyway;

    @AfterEach
    public void setup() {
        flywayMigrationStrategy.migrate(flyway);
    }

    @Test
    @Sql("createUserWithReviews.sql")
    public void bookmarkReview_whenReviewNotFound_ExpectMovieReviewNotFoundException() {
        Optional<User> user = userService.findByEmail("chol2@example.com");
        if(user.isPresent()) {

            Assertions.assertThrows(MovieReviewNotFoundException.class, () -> {
                userMovieReviewBookmarkService.bookmarkReview(user.get().getId(), 0L);
            });
        } else {
            fail("User was not found");
        }
    }

    @Test
    public void bookmarkReview_WhenUserNotFound_ExpectUserNotFoundException() {
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userMovieReviewBookmarkService.bookmarkReview(0L, 0L);
        });
    }

    @Test
    @Sql("createUserWithReviews.sql")
    public void bookmarkReview_WhenReviewIsBookmarked_ExpectItToBeSaved() {
        Optional<User> user = userService.findByEmail("chol2@example.com");
        if (user.isPresent()) {
            try {
                userMovieReviewBookmarkService.bookmarkReview(user.get().getId(), 1L);
                assertTrue(userMovieReviewBookmarkService.isReviewAlreadyBookmarked(user.get().getId(), 1L));
            } catch (Exception e) {
                fail("Exception caught: " + e.getMessage());
            }
        } else {
            fail("User not found.");
        }
    }

    @Test
    @Sql("createUserWithReviewsAndBookmark.sql")
    public void bookmarkReview_WhenReviewIsRemoved_ExpectToNotExistInDatabase() {
        Optional<User> user = userService.findByEmail("chol2@example.com");
        if (user.isPresent()) {
            try {
                userMovieReviewBookmarkService.removeBookmark(user.get().getId(), 1L);
                assertFalse(userMovieReviewBookmarkService.isReviewAlreadyBookmarked(user.get().getId(), 1L));
            } catch (Exception e) {
                fail("Exception caught: " + e.getMessage());
            }
        } else {
            fail("User not found.");
        }
    }

    @Test
    @Sql("createUserWithReviewsAndBookmark.sql")
    public void findMovieReviewBookmarksByUserId_shouldReturnAListWithBookmarks() {
        Page<UserMovieReviewBookmark> bookmarks = userMovieReviewBookmarkService.findMovieReviewBookmarksByUserId(1L, PageRequest.of(0, 10));
        assertThat(bookmarks.getContent(), hasSize(1));
    }

    @Test
    @Sql("createUserWithReviewsAndBookmark.sql")
    public void bookmarkReview_whenReviewIsAlreadyBookmarked_ShouldThrowAlreadyBookmarkedException() {
        Optional<User> user = userService.findByEmail("chol2@example.com");
        if (user.isPresent()) {
            try {
                userMovieReviewBookmarkService.bookmarkReview(user.get().getId(), 1L);
                fail("Duplicate bookmark inserted");
            } catch (AlreadyBookmarkedException e) {
                Page<UserMovieReviewBookmark> bookmarks = userMovieReviewBookmarkService.findMovieReviewBookmarksByUserId(1L, PageRequest.of(0, 10));
                assertThat(bookmarks.getContent(), hasSize(1));
            } catch (Exception e) {
                fail("Unexpected exception: " + e.getMessage());
            }
        } else {
            fail("User not found.");
        }

    }

}