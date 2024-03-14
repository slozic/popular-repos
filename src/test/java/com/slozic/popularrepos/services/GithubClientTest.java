package com.slozic.popularrepos.services;

import com.slozic.popularrepos.clients.GithubClient;
import com.slozic.popularrepos.clients.dtos.GithubRepositoriesDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class GithubClientTest {

    @InjectMocks
    private GithubClient githubClient;

    @Test
    public void getLatestMostPopularRepo_shouldReturnSuccessfully(){
        // given

        // when
        List<GithubRepositoriesDto> latestMostPopularRepos = githubClient.getLatestMostPopularRepos();

        // then
        assertThat(latestMostPopularRepos).isNotEmpty();
    }
}
