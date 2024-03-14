package com.slozic.popularrepos.services;

import com.slozic.popularrepos.dtos.PopularReposDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PopularRepoService {
    public List<PopularReposDto> getMostPopularRepos() {
        // TODO fetch the data from storage, otherwise call the API first
        return List.of(new PopularReposDto("repo name", "description", "https://github/user/repo", "user", "language", 100));
    }
}
