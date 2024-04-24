package com.slozic.popularrepos.clients;

import com.slozic.popularrepos.clients.dtos.GithubRepositoriesListDto;
import com.slozic.popularrepos.controllers.request.QueryParameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class GithubClient extends ApiClient {
    @Value("${github.repositories.api}")
    private String GITHUB_API;

    public GithubClient(RestClient.Builder builder) {
        super(builder);
    }

    /**
     * By default, we fetch the most popular repos on today's date.
     * Use the additional query parameters for data from particular date onwards and filter by language
     *
     * @return
     * @throws IOException
     */

    public GithubRepositoriesListDto getPopularGithubRepos(QueryParameters queryParameters) throws IOException {
        UriComponents uriComponents = assembleQuery(queryParameters);
        String githubResponse = restClient.get()
                .uri(uriComponents.toUri())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);
        return objectMapper.readValue(githubResponse, GithubRepositoriesListDto.class);
    }

    private UriComponents assembleQuery(QueryParameters queryParameters) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(GITHUB_API.substring(8));

        if (queryParameters.date().isPresent()) {
            uriComponentsBuilder.query("q=created:>" + queryParameters.date().get());
        } else {
            uriComponentsBuilder.query("q=created:" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        if (queryParameters.language().isPresent()) {
            uriComponentsBuilder.query("language:" + queryParameters.language().get());
        }

        if (queryParameters.pageSize().isPresent()) {
            uriComponentsBuilder.query("per_page=" + queryParameters.pageSize().get());
        }

        uriComponentsBuilder.query("sort=stars");
        uriComponentsBuilder.query("order=desc");

        return uriComponentsBuilder.build();
    }
}
