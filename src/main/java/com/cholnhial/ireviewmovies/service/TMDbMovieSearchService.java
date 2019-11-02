package com.cholnhial.ireviewmovies.service;

import com.cholnhial.ireviewmovies.config.ApplicationProperties;
import com.cholnhial.ireviewmovies.service.dto.TMDbMovieSearchResultDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Slf4j
public class TMDbMovieSearchService {


    private final RestTemplate restTemplate;

    private final ApplicationProperties applicationProperties;

    public TMDbMovieSearchResultDTO searchMovie(String movieSearchQuery, Integer page) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(applicationProperties.getMovieApiUrl())
                .queryParam("language", "en")
                .queryParam("page", page)
                .queryParam("include_adult", false)
                .queryParam("query", movieSearchQuery)
                .queryParam("api_key", applicationProperties.getTmdbApiKey());

        TMDbMovieSearchResultDTO result = restTemplate.getForObject(builder.build().toUri(), TMDbMovieSearchResultDTO.class);

        return result;
    }
}
