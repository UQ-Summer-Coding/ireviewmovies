package com.cholnhial.ireviewmovies.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private  String tmdbApiKey;
    private String movieSearchApiUrl;
    private String movieFindApiUrl;

    public String getTmdbApiKey() {
        return tmdbApiKey;
    }

    public String getMovieSearchApiUrl() {
        return movieSearchApiUrl;
    }

    public void setTmdbApiKey(String tmdbApiKey) {
        this.tmdbApiKey = tmdbApiKey;
    }

    public void setMovieSearchApiUrl(String movieSearchApiUrl) {
        this.movieSearchApiUrl = movieSearchApiUrl;
    }

    public String getMovieFindApiUrl() {
        return movieFindApiUrl;
    }

    public void setMovieFindApiUrl(String movieFindApiUrl) {
        this.movieFindApiUrl = movieFindApiUrl;
    }
}

