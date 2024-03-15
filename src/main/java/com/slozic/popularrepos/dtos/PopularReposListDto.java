package com.slozic.popularrepos.dtos;

import java.util.List;

public record PopularReposListDto(
        List<PopularReposDto> data
) {
}
