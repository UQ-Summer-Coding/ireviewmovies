package com.cholnhial.ireviewmovies.service;

import com.cholnhial.ireviewmovies.model.User;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
class MovieReviewServiceTest {

    @Autowired
    private MovieReviewService movieReviewService;

    @Autowired
    private UserService userService;


    @Autowired
    private FlywayMigrationStrategy flywayMigrationStrategy;

    @Autowired
    private Flyway flyway;

    @AfterEach
    public void setup() {
        flywayMigrationStrategy.migrate(flyway);
    }

    @Test
    @Sql("createUserWithReviews.sql")
    void findAllByUserId() {
        Optional<User> user = userService.findByEmail("chol2@example.com");
        user.ifPresentOrElse((u) -> {
            var reviews  = movieReviewService
                    .findAllByUserId(u.getId(), PageRequest.of(0, 10));
            assertThat(reviews.getContent(), hasSize(2));
        }, Assertions::fail);
    }

    @Test
    @Sql("createUserWithReviews.sql")
    void deleteMovieReviewById() {
        Optional<User> user = userService.findByEmail("chol2@example.com");
        user.ifPresentOrElse((u) -> {
            var reviews  = movieReviewService
                    .findAllByUserId(u.getId(), PageRequest.of(0, 10));
            reviews.getContent().stream()
                    .findFirst()
                    .ifPresent(r -> movieReviewService.deleteMovieReviewById(r.getId()));
        }, Assertions::fail);

        user.ifPresentOrElse((u) -> {
            var reviews  = movieReviewService
                    .findAllByUserId(u.getId(), PageRequest.of(0, 10));
            assertThat(reviews.getContent(), hasSize(1));
        }, Assertions::fail);

    }
}