package com.slozic.popularrepos.clients.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubRepositoriesDto(
        String name,
        String description,
        String url,
        String language,
        Integer stargazersCount
) {
}
