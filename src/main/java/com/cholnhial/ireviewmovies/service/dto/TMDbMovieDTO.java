package com.cholnhial.ireviewmovies.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TMDbMovieDTO {

    private Double popularity;

    private Integer voteCount;

    private Boolean video;

    private String posterPath;

    private Long id;

    private Boolean adult;

    private String backdropPath;

    private String originalLanguage;

    private String originalTitle;

    private List<String> genreIds;

    private String title;

    private Double voteAverage;

    private String overview;

    private LocalDate releaseDate;
}
