package com.slozic.popularrepos.controllers.dto;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public record QueryParameters(
        @PathVariable("pageSize") Optional<Integer> pageSize,
        @PathVariable("date") Optional<String> date,
        @PathVariable("language") Optional<String> language
) {
    public GithubFilter toGithubFilter() {
        return new GithubFilter(this.pageSize, this.date, this.language);
    }
}
