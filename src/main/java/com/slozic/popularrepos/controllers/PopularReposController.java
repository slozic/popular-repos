package com.slozic.popularrepos.controllers;

import com.slozic.popularrepos.controllers.request.QueryParameters;
import com.slozic.popularrepos.dtos.PopularReposListDto;
import com.slozic.popularrepos.services.GitHubRepoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/popular-repositories")
@RequiredArgsConstructor
@Slf4j
public class PopularReposController {
    private final GitHubRepoService gitHubRepoService;

    @GetMapping
    public ResponseEntity<PopularReposListDto> getPopularGithubRepositories(final QueryParameters queryParameters) {
        PopularReposListDto mostPopularRepos;
        try {
            mostPopularRepos = gitHubRepoService.getPopularGithubRepos(queryParameters);
            return ResponseEntity.ok(mostPopularRepos);
        } catch (IOException e) {
            log.error("Error occurred while retrieving the github response", e);
            return ResponseEntity.unprocessableEntity().build();
        }
    }

}
