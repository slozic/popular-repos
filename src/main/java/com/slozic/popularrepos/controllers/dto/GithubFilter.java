package com.slozic.popularrepos.controllers.dto;

import java.util.Optional;

public record GithubFilter(Optional<Integer> pageSize,
                           Optional<String> date,
                           Optional<String> language
) {
}
