package com.slozic.popularrepos.clients;

import com.slozic.popularrepos.clients.dtos.GithubRepositoriesDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GithubClient {
    public List<GithubRepositoriesDto> getLatestMostPopularRepos() {
        //TODO call the api and map the response to dto
        return List.of(new GithubRepositoriesDto("repo name", "description", "https://github/user/repo", "user", "language", 100));
    }
}
