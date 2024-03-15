package com.slozic.popularrepos.services;

import com.slozic.popularrepos.clients.GithubClient;
import com.slozic.popularrepos.clients.dtos.GithubRepositoriesListDto;
import com.slozic.popularrepos.dtos.PopularReposDto;
import com.slozic.popularrepos.dtos.PopularReposListDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service that retrieves github repo data and maps it into the business dto object
 * For the first iteration we always go to the third party service (GITHUB API)
 * In second iteration we should persist the data in our db storage
 */
@Service
public class GitHubRepoService {
    private GithubClient githubClient;

    public PopularReposListDto getLatestPopularRepos() throws IOException {
        GithubRepositoriesListDto latestMostPopularRepos = githubClient.getLatestMostPopularRepos();

        List<PopularReposDto> popularReposDtos = latestMostPopularRepos.items().stream()
                .map(dto -> new PopularReposDto(dto.name(), dto.description(), dto.url(), "", dto.language(), dto.stargazersCount()))
                .collect(Collectors.toList());
        PopularReposListDto popularReposListDto = new PopularReposListDto(popularReposDtos);
        return popularReposListDto;
    }

    public PopularReposListDto getPopularReposFromDateOnwards(String date) throws IOException {
        GithubRepositoriesListDto latestMostPopularRepos = githubClient.getPopularReposFromDateOnwards(date);

        List<PopularReposDto> popularReposDtos = latestMostPopularRepos.items().stream()
                .map(dto -> new PopularReposDto(dto.name(), dto.description(), dto.url(), "", dto.language(), dto.stargazersCount()))
                .collect(Collectors.toList());
        PopularReposListDto popularReposListDto = new PopularReposListDto(popularReposDtos);
        return popularReposListDto;
    }

    public PopularReposListDto getPopularReposPaginated(Integer pageSize) throws IOException {
        GithubRepositoriesListDto latestMostPopularRepos = githubClient.getPopularReposPaginated(pageSize);

        List<PopularReposDto> popularReposDtos = latestMostPopularRepos.items().stream()
                .map(dto -> new PopularReposDto(dto.name(), dto.description(), dto.url(), "", dto.language(), dto.stargazersCount()))
                .collect(Collectors.toList());
        PopularReposListDto popularReposListDto = new PopularReposListDto(popularReposDtos);
        return popularReposListDto;
    }

    public PopularReposListDto getPopularReposFilteredByLanguage(String language) throws IOException {
        GithubRepositoriesListDto latestMostPopularRepos = githubClient.getPopularReposFilteredByLanguage(language);

        List<PopularReposDto> popularReposDtos = latestMostPopularRepos.items().stream()
                .map(dto -> new PopularReposDto(dto.name(), dto.description(), dto.url(), "", dto.language(), dto.stargazersCount()))
                .collect(Collectors.toList());
        PopularReposListDto popularReposListDto = new PopularReposListDto(popularReposDtos);
        return popularReposListDto;
    }
}
