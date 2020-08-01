package com.cholnhial.ireviewmovies.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "movie")
@Data
@EqualsAndHashCode(of = {"id", "tMDBMovieId"})
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tmdb_movie_id")
    private Long tMDBMovieId;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    @Lob
    private String description;

    @Column(name = "year")
    private Long year;

    @Column(name = "tmdb_movie_poster_path")
    private String tMDBMoviePosterPath;

    @Column(name = "total_reviews")
    private Long totalReviews;

    @Column(name = "average_rating")
    private Float averageRating;

    @Column(name = "created")
    private ZonedDateTime createdDateTime;

    @Column(name = "modified")
    private ZonedDateTime modifiedDateTime;

}
