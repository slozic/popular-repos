package com.slozic.popularrepos.dtos;

import com.slozic.popularrepos.clients.dtos.GithubRepositoriesListDto;

import java.util.List;
import java.util.stream.Collectors;

public record PopularReposListDto(
        List<PopularReposDto> data
) {
    public static PopularReposListDto from(GithubRepositoriesListDto latestMostPopularRepos) {
        List<PopularReposDto> popularReposDtos = latestMostPopularRepos.items().stream()
                .map(dto -> new PopularReposDto(dto.name(), dto.description(), dto.url(), "", dto.language(), dto.stargazersCount()))
                .collect(Collectors.toList());
        return new PopularReposListDto(popularReposDtos);
    }
}
