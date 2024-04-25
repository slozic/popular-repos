package com.slozic.popularrepos.services;

import com.slozic.popularrepos.clients.GithubClient;
import com.slozic.popularrepos.clients.dtos.GithubRepositoriesListDto;
import com.slozic.popularrepos.controllers.dto.GithubFilter;
import com.slozic.popularrepos.dtos.PopularReposListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Service that retrieves github repo data and maps it into the business dto object
 * We always go to the third party service (GITHUB API), and as performance update
 * we should persist it in db storage or cache
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"githubFilter"})
public class GitHubRepoService {
    private final GithubClient githubClient;

    @Cacheable(value = "githubFilter", key = "#githubFilter")
    public PopularReposListDto getPopularGithubRepos(GithubFilter githubFilter) throws IOException {
        GithubRepositoriesListDto latestMostPopularRepos = githubClient.getPopularGithubRepos(githubFilter);
        return PopularReposListDto.from(latestMostPopularRepos);
    }

}
