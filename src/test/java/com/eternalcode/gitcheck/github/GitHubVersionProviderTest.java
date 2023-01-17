package com.eternalcode.gitcheck.github;

import com.eternalcode.gitcheck.git.GitException;
import com.eternalcode.gitcheck.git.GitRelease;
import com.eternalcode.gitcheck.git.GitRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GitHubVersionProviderTest {

    @Test
    void testGetLatestRelease() {
        GitHubReleaseProvider provider = new GitHubReleaseProvider();
        GitRelease release = provider.getLatestRelease(GitRepository.of("EternalCodeTeam", "ChatFormatter"));

        assertNotNull(release);
        assertNotNull(release.getName());
        assertNotNull(release.getBranch());
        assertNotNull(release.getTag());
        assertNotNull(release.getPageUrl());
        assertNotNull(release.getPublishedAt());
    }

    @Test
    void testGetLatestReleaseWithNonExistingRepository() {
        this.assertThrowsReleaseFor(GitRepository.of("EternalCodeTeam", "-"));
    }

    @Test
    void testGetLatestReleaseWithNotReleasedRepository() {
        this.assertThrowsReleaseFor(GitRepository.of("EternalCodeTeam", "EternalCore"));
    }

    @Test
    void testGetLatestReleaseWithInvalidRepository() {
        this.assertThrowsReleaseFor(GitRepository.of(".", "."));
    }

    private void assertThrowsReleaseFor(GitRepository repository) {
        GitHubReleaseProvider provider = new GitHubReleaseProvider();

        assertThrows(GitException.class, () -> provider.getLatestRelease(repository));
    }

}