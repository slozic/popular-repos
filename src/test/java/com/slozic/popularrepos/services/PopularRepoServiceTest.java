package com.slozic.popularrepos.services;

import com.slozic.popularrepos.dtos.PopularReposDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PopularRepoServiceTest {

    @InjectMocks
    private PopularRepoService popularRepoService;

    @Test
    public void getMostPopularRepos_shouldReturnSuccessfully() {
        // given

        // when
        List<PopularReposDto> mostPopularReposList = popularRepoService.getMostPopularRepos();

        // then
        Assertions.assertThat(mostPopularReposList).isNotEmpty();
        Assertions.assertThat(mostPopularReposList).isEqualTo(List.of(new PopularReposDto("repo name", "description", "https://github/user/repo", "user", "language", 100)));
    }
}
