package com.cholnhial.ireviewmovies.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TMDbMovieSearchResultDTO {

    private Long page;

    private Long totalResults;

    private Long totalPages;

    private List<TMDbMovieDTO> results;
}
