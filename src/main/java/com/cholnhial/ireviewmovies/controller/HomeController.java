package com.cholnhial.ireviewmovies.controller;

import com.cholnhial.ireviewmovies.model.Movie;
import com.cholnhial.ireviewmovies.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
public class HomeController {


    private final MovieService movieService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> requestedPage,
                        @RequestParam("size") Optional<Integer> requestedPageSize,
                        @RequestParam(value ="query", required = false, defaultValue = "") String query
    ) {


        int currentPage = requestedPage.orElse(1);
        int pageSize = requestedPageSize.orElse(9);

        PageRequest pageable = PageRequest.of(currentPage - 1, pageSize);

        if(!query.isEmpty()) {
            Page<Movie> result = movieService.findAll(query, pageable);
            model.addAttribute("searchResults", result);
            int totalPages =  result.getTotalPages();
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        } else {
            Page<Movie> result = movieService.findAll(pageable);
            model.addAttribute("searchResults", result);
            int totalPages =  result.getTotalPages();
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }


        return "home";
    }

}
