package com.slozic.popularrepos.dtos;

public record PopularReposDto (
        String name,
        String description,
        String url,
        String user,
        String language,
        Integer stars
) {
}
