package com.slozic.popularrepos.services;

import com.slozic.popularrepos.clients.GithubClient;
import com.slozic.popularrepos.clients.dtos.GithubRepositoriesDto;
import com.slozic.popularrepos.clients.dtos.GithubRepositoriesListDto;
import com.slozic.popularrepos.controllers.request.QueryParameters;
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
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GithubRepoServiceTest {
    @Mock
    private GithubClient githubClient;

    @InjectMocks
    private GitHubRepoService gitHubRepoService;

    @Test
    public void getPopularGithubRepos_shouldReturnSuccessfully() throws IOException {
        // given
        List<GithubRepositoriesDto> githubRepositoriesDtos = List.of(new GithubRepositoriesDto("repo name", "description", "https://github/user/repo", "language", 100));
        GithubRepositoriesListDto githubRepositoriesListDto = new GithubRepositoriesListDto(githubRepositoriesDtos);
        when(githubClient.getPopularGithubRepos(new QueryParameters(Optional.empty(), Optional.empty(), Optional.empty()))).thenReturn(githubRepositoriesListDto);

        // when
        PopularReposListDto mostPopularReposList = gitHubRepoService.getPopularGithubRepos(new QueryParameters(Optional.empty(), Optional.empty(), Optional.empty()));

        // then
        Assertions.assertThat(mostPopularReposList).isNotNull();
        Assertions.assertThat(mostPopularReposList.data()).isEqualTo(List.of(new PopularReposDto("repo name", "description", "https://github/user/repo", "", "language", 100)));
    }
}
