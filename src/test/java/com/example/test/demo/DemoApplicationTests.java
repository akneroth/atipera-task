package com.example.test.demo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.test.demo.api.model.ApiError;
import com.example.test.demo.api.model.GitUserRepo;
import com.example.test.demo.api.model.GitUserRepo.GitBranch;
import com.example.test.demo.service.GitService;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private GitService gitService;

	@Test
	void testGetNonForkedUserRepos() {
		Object result = gitService.getNonForkedUserRepos("akneroth");
		
		assertTrue(result instanceof List<?>);
		List<?> repos = (List<?>) result;
		assertFalse(repos.isEmpty());
		assertTrue(repos.get(0) instanceof GitUserRepo);
		
		GitUserRepo repo = (GitUserRepo) repos.get(0);
		assertNotNull(repo.getRepoName());
		assertNotNull(repo.getLogin());

		assertTrue(repo.getBranches() instanceof List<?>);
		List<?> branches = repo.getBranches();
		assertFalse(repos.isEmpty());
		assertTrue(branches.get(0) instanceof GitBranch);

		GitBranch branch = (GitBranch) branches.get(0);
		assertNotNull(branch.getName());
		assertNotNull(branch.getLastCommitSha());
	}

}
