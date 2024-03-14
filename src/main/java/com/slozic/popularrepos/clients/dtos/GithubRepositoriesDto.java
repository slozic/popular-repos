package com.slozic.popularrepos.clients.dtos;

public record GithubRepositoriesDto (
        String name,
        String description,
        String url,
        String user,
        String language,
        Integer stars
) {
}
