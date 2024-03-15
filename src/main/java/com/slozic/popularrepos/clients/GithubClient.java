package com.slozic.popularrepos.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.slozic.popularrepos.clients.dtos.GithubRepositoriesListDto;
import com.slozic.popularrepos.controllers.request.QueryParameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class GithubClient {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    @Value("${github.repositories.api}")
    private String GITHUB_API;

    public GithubClient(final RestClient.Builder builder) {
        restClient = builder.build();
        objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    /**
     * By default, we fetch the most popular repos on today's date.
     * Use the additional query parameters for data from particular date onwards and filter by language
     *
     * @return
     * @throws IOException
     */

    public GithubRepositoriesListDto getPopularGithubRepos(QueryParameters queryParameters) throws IOException {
        String query = assembleQuery(queryParameters);
        String githubResponse = restClient.get()
                .uri(query)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);
        return objectMapper.readValue(githubResponse, GithubRepositoriesListDto.class);
    }

    private String assembleQuery(QueryParameters queryParameters) {
        StringBuffer sb = new StringBuffer();
        sb.append(GITHUB_API + "?q=created:");
        sb.append(queryParameters.date().isPresent() ? ">" + queryParameters.date().get() : LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        sb.append(queryParameters.language().isPresent() ? " language:" + queryParameters.language().get() : "");
        sb.append(queryParameters.pageSize().isPresent() ? "&per_page=" + queryParameters.pageSize().get() : "");
        sb.append("&sort=stars&order=desc");
        return sb.toString();
    }
}
