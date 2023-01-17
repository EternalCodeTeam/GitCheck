package com.eternalcode.gitcheck.mock;

import com.eternalcode.gitcheck.git.GitRelease;
import com.eternalcode.gitcheck.git.GitReleaseProvider;
import com.eternalcode.gitcheck.git.GitRepository;
import com.eternalcode.gitcheck.git.GitTag;

import java.time.Instant;

public class MockGitReleaseProvider implements GitReleaseProvider {

    @Override
    public GitRelease getLatestRelease(GitRepository repository) {
        return GitRelease.builder()
                .name("GitCheck 1.0.0")
                .branch("master")
                .tag(GitTag.of("v1.0.0"))
                .pageUrl("https://repo.eternalcode.pl/releases")
                .publishedAt(Instant.parse("2020-01-01T00:00:00Z"))
                .build();
    }

}
