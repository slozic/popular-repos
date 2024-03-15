package com.slozic.popularrepos.controllers.request;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public record QueryParameters(
        @PathVariable("pageSize") Optional<Integer> pageSize,
        @PathVariable("date") Optional<String> date,
        @PathVariable("language") Optional<String> language
) {
}
