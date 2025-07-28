package com.example.test.demo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.demo.service.GitService;

@RestController
public class ApiController {

    private GitService git;

    @Autowired
    public ApiController(GitService gitService) {
        this.git = gitService;
    }

    @GetMapping("/")
    public String helloWorld() {
        return "Hello world";
    }

    @GetMapping("/gitRepos")
    public Object getGitRepos(@RequestParam("user") String user) {
        return git.getUserRepos(user);
    }

}
