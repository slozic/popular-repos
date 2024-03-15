package com.slozic.popularrepos.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.slozic.popularrepos.clients.dtos.GithubRepositoriesListDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class GithubClient {
    private RestClient restClient;
    private ObjectMapper objectMapper;
    @Value("${github.repositories.api}")//?q=created:2024-03-01&per_page=1&sort=stars&order=desc
    private String GITHUB_API;

    public GithubClient(final RestClient.Builder builder) {
        restClient = builder.build();
        objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    /**
     * By default, we fetch the most popular repos on today's date.
     * Use the additional method 'getPopularReposFromDateOnwards' for data from particular date onwards
     *
     * @return
     * @throws IOException
     */
    public GithubRepositoriesListDto getLatestMostPopularRepos() throws IOException {
        String githubResponse = restClient.get()
                .uri(GITHUB_API, "q", "created:" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        "sort", "stars",
                        "order", "desc")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    log.error("Error occurred while calling the Github Api! {}, {} ", response.getStatusCode(), response.getStatusCode());
                    throw new HttpClientErrorException(response.getStatusCode(), response.getStatusText());
                })
                .body(String.class);

        return objectMapper.readValue(githubResponse, GithubRepositoriesListDto.class);
    }

    /**
     * Expected valid 'Date' format is 'yyyy-MM-dd'
     *
     * @param date
     * @return
     * @throws IOException
     */
    public GithubRepositoriesListDto getPopularReposFromDateOnwards(String date) throws IOException {
        String githubResponse = restClient.get()
                .uri(GITHUB_API, "q", "created:" + date,
                        "sort", "stars",
                        "order", "desc")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    log.error("Error occurred while calling the Github Api! {}, {} ", response.getStatusCode(), response.getStatusCode());
                    throw new HttpClientErrorException(response.getStatusCode(), response.getStatusText());
                })
                .body(String.class);

        return objectMapper.readValue(githubResponse, GithubRepositoriesListDto.class);
    }

    public GithubRepositoriesListDto getPopularReposPaginated(Integer pageSize) throws IOException {
        String githubResponse = restClient.get()
                .uri(GITHUB_API, "q", "created:" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        "per_page", pageSize,
                        "sort", "stars",
                        "order", "desc")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    log.error("Error occurred while calling the Github Api! {}, {} ", response.getStatusCode(), response.getStatusCode());
                    throw new HttpClientErrorException(response.getStatusCode(), response.getStatusText());
                })
                .body(String.class);

        return objectMapper.readValue(githubResponse, GithubRepositoriesListDto.class);
    }

    public GithubRepositoriesListDto getPopularReposFilteredByLanguage(String language) throws IOException {
        String githubResponse = restClient.get()
                .uri(GITHUB_API, "q", "created:" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        "language", language,
                        "sort", "stars",
                        "order", "desc")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    log.error("Error occurred while calling the Github Api! {}, {} ", response.getStatusCode(), response.getStatusCode());
                    throw new HttpClientErrorException(response.getStatusCode(), response.getStatusText());
                })
                .body(String.class);

        return objectMapper.readValue(githubResponse, GithubRepositoriesListDto.class);
    }
}
