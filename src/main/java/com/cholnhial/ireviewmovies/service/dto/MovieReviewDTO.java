package com.cholnhial.ireviewmovies.service.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MovieReviewDTO {

    private Long id;

    @NotNull
    private Long tMDBMovieId;

    private String tMDBMoviePosterPath;

    @NotBlank(message = "You need to write something")
    private String reviewText;

    private Float rating;

    private Integer likes;

}