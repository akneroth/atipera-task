package com.example.test.demo.api.model;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class GitUserRepo {
    @AllArgsConstructor
    public class GitBranch {
        @Getter @Setter private String name;
        @Getter @Setter private String lastCommitSha;
    }

    @Getter @Setter private String repoName;
    @Getter @Setter private String login;
    @Getter @Setter private ArrayList<GitBranch> branches;
}
