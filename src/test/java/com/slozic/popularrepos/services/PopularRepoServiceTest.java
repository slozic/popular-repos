package com.slozic.popularrepos.services;

import com.slozic.popularrepos.dtos.PopularReposDto;
import com.slozic.popularrepos.dtos.PopularReposListDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PopularRepoServiceTest {

    @InjectMocks
    private PopularRepoService popularRepoService;

    @Mock
    private GitHubRepoService gitHubRepoService;

    @Test
    public void getMostPopularRepos_shouldReturnSuccessfully() throws IOException {
        // given
        List<PopularReposDto> popularReposDtos = List.of(new PopularReposDto("repo name", "description", "https://github/user/repo", "user", "language", 100));
        PopularReposListDto popularReposListDto = new PopularReposListDto(popularReposDtos);
        when(gitHubRepoService.getLatestPopularRepos()).thenReturn(popularReposListDto);

        // when
        PopularReposListDto mostPopularReposList = popularRepoService.getMostPopularRepos();

        // then
        Assertions.assertThat(mostPopularReposList).isNotNull();
        Assertions.assertThat(mostPopularReposList.data()).isEqualTo(List.of(new PopularReposDto("repo name", "description", "https://github/user/repo", "user", "language", 100)));
    }
}
