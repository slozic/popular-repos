package com.slozic.popularrepos.clients;

import com.slozic.popularrepos.clients.dtos.GithubRepositoriesListDto;
import com.slozic.popularrepos.controllers.dto.GithubFilter;
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

    public GithubRepositoriesListDto getPopularGithubRepos(GithubFilter githubFilter) throws IOException {
        UriComponents uriComponents = assembleQuery(githubFilter);
        String githubResponse = restClient.get()
                .uri(uriComponents.toUri())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);
        return objectMapper.readValue(githubResponse, GithubRepositoriesListDto.class);
    }

    private UriComponents assembleQuery(GithubFilter githubFilter) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(GITHUB_API.substring(8));

        if (githubFilter.date().isPresent()) {
            uriComponentsBuilder.query("q=created:>" + githubFilter.date().get());
        } else {
            uriComponentsBuilder.query("q=created:" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        if (githubFilter.language().isPresent()) {
            uriComponentsBuilder.query("language:" + githubFilter.language().get());
        }

        if (githubFilter.pageSize().isPresent()) {
            uriComponentsBuilder.query("per_page=" + githubFilter.pageSize().get());
        }

        uriComponentsBuilder.query("sort=stars");
        uriComponentsBuilder.query("order=desc");

        return uriComponentsBuilder.build();
    }
}
