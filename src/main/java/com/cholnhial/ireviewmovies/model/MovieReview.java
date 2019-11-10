package com.cholnhial.ireviewmovies.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "movie_review")
@Data
@EqualsAndHashCode(of = {"id"})
public class MovieReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tmdb_movie_id")
    private Long tMDBMovieId;

    @Column(name = "tmdb_movie_poster_path")
    private String tMDBMoviePosterPath;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "review_text", columnDefinition = "text")
    @Type(type = "text")
    private String reviewText;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "created")
    private ZonedDateTime createdDateTime;

    @Column(name = "modified")
    private ZonedDateTime modifiedDateTime;


}
