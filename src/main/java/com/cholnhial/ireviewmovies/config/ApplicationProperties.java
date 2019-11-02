package com.cholnhial.ireviewmovies.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private  String tmdbApiKey;
    private String movieApiUrl;

    public String getTmdbApiKey() {
        return tmdbApiKey;
    }

    public String getMovieApiUrl() {
        return movieApiUrl;
    }

    public void setTmdbApiKey(String tmdbApiKey) {
        this.tmdbApiKey = tmdbApiKey;
    }

    public void setMovieApiUrl(String movieApiUrl) {
        this.movieApiUrl = movieApiUrl;
    }
}

