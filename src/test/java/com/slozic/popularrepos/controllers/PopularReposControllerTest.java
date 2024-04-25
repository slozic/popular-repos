package com.slozic.popularrepos.controllers;

import com.slozic.popularrepos.controllers.dto.GithubFilter;
import com.slozic.popularrepos.dtos.PopularReposDto;
import com.slozic.popularrepos.dtos.PopularReposListDto;
import com.slozic.popularrepos.services.GitHubRepoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest
public class PopularReposControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GitHubRepoService gitHubRepoService;

    @Test
    public void getPopularGithubRepositories_worksWithSuccess() throws Exception {
        // given
        PopularReposListDto popularReposListDto = new PopularReposListDto(List.of(
                new PopularReposDto("repo name", "description", "https://github/user/repo", "", "language", 100)));
        when(gitHubRepoService.getPopularGithubRepos(any(GithubFilter.class))).thenReturn(popularReposListDto);

        // when
        var mvcResult = mockMvc.perform(get("/popular-repositories")).andReturn();

        // then
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("{\"data\":[{\"name\":\"repo name\",\"description\":\"description\",\"url\":\"https://github/user/repo\",\"user\":\"\",\"language\":\"language\",\"stars\":100}]}");
    }

    @Test
    public void getPopularGithubRepositoriesWithFilterParams_worksWithSuccess() throws Exception {
        // given

        // when
        var mvcResult = mockMvc.perform(get("/popular-repositories?pageSize=10&language=Java&date=2021-01-01")).andReturn();

        // then
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
    }
}
