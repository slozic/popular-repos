package com.slozic.popularrepos.clients;

import com.slozic.popularrepos.clients.GithubClient;
import com.slozic.popularrepos.clients.dtos.GithubRepositoriesDto;
import com.slozic.popularrepos.clients.dtos.GithubRepositoriesListDto;
import com.slozic.popularrepos.controllers.request.QueryParameters;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withException;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(GithubClient.class)
public class GithubClientTest {

    @Value("${github.repositories.api}")
    private String GITHUB_API_URL;
    @Autowired
    private GithubClient githubClient;
    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Test
    public void getPopularGithubRepos_shouldReturnSuccessfully() throws IOException {
        // given
        final String jsonResponse = Files.readString(new ClassPathResource("data/latest.json").getFile().toPath());

        mockRestServiceServer.expect(once(), requestTo(GITHUB_API_URL + "?q=created:" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "&sort=stars&order=desc"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));
        // when
        GithubRepositoriesListDto latestMostPopularRepos = githubClient.getPopularGithubRepos(new QueryParameters(Optional.empty(), Optional.empty(), Optional.empty()));

        // then
        mockRestServiceServer.verify();
        GithubRepositoriesDto githubRepositoriesDto = new GithubRepositoriesDto(
                "sd-forge-layerdiffuse",
                "[WIP] Layer Diffusion for WebUI (via Forge)",
                "https://api.github.com/repos/layerdiffusion/sd-forge-layerdiffuse",
                "Python",
                2918);
        GithubRepositoriesListDto expectedListDto = new GithubRepositoriesListDto(List.of(githubRepositoriesDto));
        assertThat(latestMostPopularRepos).isNotNull();
        assertThat(latestMostPopularRepos).isEqualTo(expectedListDto);
    }
}
