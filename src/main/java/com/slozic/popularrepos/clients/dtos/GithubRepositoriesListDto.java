package com.slozic.popularrepos.clients.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubRepositoriesListDto(
        List<GithubRepositoriesDto> items
) {
}
