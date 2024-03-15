package com.slozic.popularrepos.services;

import com.slozic.popularrepos.dtos.PopularReposListDto;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PopularRepoService {

    private GitHubRepoService gitHubRepoService;

    public PopularReposListDto getMostPopularRepos() throws IOException {
        // Update: Here call the persisted data from the db first, before going to the external source
        return gitHubRepoService.getLatestPopularRepos();
    }

    public PopularReposListDto getPopularReposFromDateOnwards(String date) throws IOException {
        // Update: Here call the persisted data from the db first, before going to the external source
        return gitHubRepoService.getPopularReposFromDateOnwards(date);
    }

    public PopularReposListDto getPopularReposPaginated(Integer pageSize) throws IOException {
        // Update: Here call the persisted data from the db first, before going to the external source
        return gitHubRepoService.getPopularReposPaginated(pageSize);
    }

    public PopularReposListDto getPopularReposFilteredByLanguage(String language) throws IOException {
        // Update: Here call the persisted data from the db first, before going to the external source
        return gitHubRepoService.getPopularReposFilteredByLanguage(language);
    }
}
