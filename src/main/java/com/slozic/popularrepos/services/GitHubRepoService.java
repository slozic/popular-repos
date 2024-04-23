package com.slozic.popularrepos.services;

import com.slozic.popularrepos.clients.GithubClient;
import com.slozic.popularrepos.clients.dtos.GithubRepositoriesListDto;
import com.slozic.popularrepos.controllers.request.QueryParameters;
import com.slozic.popularrepos.dtos.PopularReposDto;
import com.slozic.popularrepos.dtos.PopularReposListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service that retrieves github repo data and maps it into the business dto object
 * We always go to the third party service (GITHUB API), and as performance update
 * we should persist it in db storage or cache
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"queryParameters"})
public class GitHubRepoService {
    private final GithubClient githubClient;

    @Cacheable(value = "queryParameters", key = "#queryParameters")
    public PopularReposListDto getPopularGithubRepos(QueryParameters queryParameters) throws IOException {
        GithubRepositoriesListDto latestMostPopularRepos = githubClient.getPopularGithubRepos(queryParameters);
        PopularReposListDto popularReposListDto = mapToBusinessDto(latestMostPopularRepos);
        return popularReposListDto;
    }

    private PopularReposListDto mapToBusinessDto(GithubRepositoriesListDto latestMostPopularRepos) {
        List<PopularReposDto> popularReposDtos = latestMostPopularRepos.items().stream()
                .map(dto -> new PopularReposDto(dto.name(), dto.description(), dto.url(), "", dto.language(), dto.stargazersCount()))
                .collect(Collectors.toList());
        return new PopularReposListDto(popularReposDtos);
    }
}
