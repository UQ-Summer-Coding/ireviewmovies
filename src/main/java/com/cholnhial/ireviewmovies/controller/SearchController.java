package com.cholnhial.ireviewmovies.controller;

import com.cholnhial.ireviewmovies.service.TMDbMovieSearchService;
import com.cholnhial.ireviewmovies.service.dto.TMDbMovieSearchResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final TMDbMovieSearchService tmDbMovieSearchService;

    @GetMapping
    public String index(Model model,
                        @RequestParam(value ="query", required = false, defaultValue = "") String query,
                        @RequestParam(value ="page", required = false, defaultValue = "1") Integer page
    ) {
        if(!query.isEmpty()) {
            TMDbMovieSearchResultDTO result = tmDbMovieSearchService.searchMovie(query, page);
            model.addAttribute("searchResults", result);
            Long totalPages = result.getTotalPages();
            totalPages++;
            List<Integer> pageNumbers = IntStream.range(1, totalPages.intValue()).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }


        return "search";
    }
}
