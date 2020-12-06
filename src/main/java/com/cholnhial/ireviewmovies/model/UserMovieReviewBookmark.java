package com.cholnhial.ireviewmovies.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "user_movie_review_bookmark")
@Data
@EqualsAndHashCode(of = {"id"})
public class UserMovieReviewBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "movie_review_id", referencedColumnName = "id", nullable = false)
    private MovieReview movieReview;

    @Column(name = "created")
    private ZonedDateTime created;
}
