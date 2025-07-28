package com.example.test.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.example.test.demo.api.model.ApiError;
import com.example.test.demo.api.model.GitUserRepo;
import com.example.test.demo.api.model.GitUserRepo.GitBranch;

@Service
public class GitService {
    private String githubApiBaseUrl = "https://api.github.com";
    private ResponseErrorHandler noErrorHandler = new ResponseErrorHandler() {
        @Override public boolean hasError(org.springframework.http.client.ClientHttpResponse response) { return false;}
        @Override public void handleError(org.springframework.http.client.ClientHttpResponse response) {}
    };

    public Object getNonForkedUserRepos(String username) {
        String reposUrl = githubApiBaseUrl + "/users/" + username + "/repos";
        ResponseEntity<String> response;
        
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(noErrorHandler);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        response = restTemplate.exchange(reposUrl, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode().value() != 200) {
            return new ApiError(response.getStatusCode().value(), new JSONObject(response.getBody()).getString("message"));
        }

        JSONArray reposArray = new JSONArray(response.getBody());
        List<GitUserRepo> nonForkedRepos = new ArrayList<>();

        for (int i = 0; i < reposArray.length(); i++) {
            JSONObject repo = reposArray.getJSONObject(i);

            if (repo.getBoolean("fork"))
                continue;

            GitUserRepo gitUserRepo = new GitUserRepo();
            gitUserRepo.setRepoName(repo.getString("name"));
            gitUserRepo.setLogin(repo.getJSONObject("owner").getString("login"));

            ArrayList<GitBranch> branches = new ArrayList<>();
            String branchesUrl = repo.getString("branches_url").replaceAll("\\{.*?\\}", "");
            response = restTemplate.exchange(branchesUrl, HttpMethod.GET, entity, String.class);
            JSONArray repoBranches = new JSONArray(response.getBody());
            for (int j = 0; j < repoBranches.length(); j++) {
                JSONObject repoBranch = repoBranches.getJSONObject(j);
                GitBranch branch = gitUserRepo.new GitBranch(
                        repoBranch.getString("name"),
                        repoBranch.getJSONObject("commit").getString("sha"));
                branches.add(branch);
            }

            gitUserRepo.setBranches(branches); // You can populate branches if needed
            nonForkedRepos.add(gitUserRepo);
        }

        return nonForkedRepos;
    }

}
