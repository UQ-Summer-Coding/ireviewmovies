package com.cholnhial.ireviewmovies.service;

import com.cholnhial.ireviewmovies.service.dto.TMDbMovieSearchResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TMDbMovieSearchServiceTest {

    @Autowired
    private TMDbMovieSearchService tmDbMovieSearchService;



    @Test
    void searchMovie() {
        TMDbMovieSearchResultDTO result = this.tmDbMovieSearchService.searchMovie("Shrek", 1);
        assertNotNull(result);
        assertThat(result.getResults().size(), greaterThan(0));
    }

    @Test
    void searchMovie_shouldBeAbleToSearchRemainingPages() {
        Integer currentPage = 1;

        // Get first page
        TMDbMovieSearchResultDTO result = this.tmDbMovieSearchService.searchMovie("Shrek", currentPage);
        Long totalPages = result.getTotalPages();
        assertThat(totalPages, greaterThan(1L));

        // get subsequent pages
        for(int i = currentPage+1; i < totalPages; i++) {
            result = this.tmDbMovieSearchService.searchMovie("Shrek", currentPage);
            assertNotNull(result);
            assertEquals(result.getPage(), Long.valueOf(i));
            assertThat(result.getResults().size(), greaterThan(0));
        }
    }
}