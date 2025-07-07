package com.eternalcode.gitcheck.mock;

import com.eternalcode.gitcheck.git.GitException;
import com.eternalcode.gitcheck.git.GitRelease;
import com.eternalcode.gitcheck.git.GitReleaseProvider;
import com.eternalcode.gitcheck.git.GitRepository;

public class MockErrorGitReleaseProvider implements GitReleaseProvider {

    @Override
    public GitRelease getLatestRelease(GitRepository repository) {
        throw new GitException("Repository or release not found: " + repository.getFullName() + " (404)");
    }
}