package com.eternalcode.gitcheck;

import com.eternalcode.gitcheck.git.GitRelease;
import com.eternalcode.gitcheck.git.GitTag;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class GitCheckResultTest {

    private final GitRelease release = GitRelease.builder()
            .name("test")
            .branch("master")
            .tag(GitTag.of("v2.0.0"))
            .pageUrl("https://repo.eternalcode.pl/releases")
            .publishedAt(Instant.parse("2020-01-01T00:00:00Z"))
            .build();

    private final GitCheckResult successResult = GitCheckResult.success(this.release, GitTag.of("v1.0.0"));
    private final GitCheckResult upToDateResult = GitCheckResult.success(this.release, GitTag.of("v2.0.0"));
    private final GitCheckError error = new GitCheckError(GitCheckErrorType.REPOSITORY_NOT_FOUND, "Test error", 404, null);
    private final GitCheckResult failedResult = GitCheckResult.failure(error);

    @Test
    void testSuccessfulResult() {
        assertTrue(successResult.isSuccessful());
        assertTrue(upToDateResult.isSuccessful());
        assertFalse(failedResult.isSuccessful());
    }

    @Test
    void testIsUpToDate() {
        assertFalse(successResult.isUpToDate());
        assertTrue(upToDateResult.isUpToDate());
    }

    @Test
    void testGetLatestReleaseSuccess() {
        assertEquals(release, successResult.getLatestRelease());
        assertEquals(release, upToDateResult.getLatestRelease());
    }

    @Test
    void testGetLatestReleaseFailure() {
        assertThrows(IllegalStateException.class, () -> failedResult.getLatestRelease());
    }

    @Test
    void testGetCurrentTagSuccess() {
        assertEquals(GitTag.of("v1.0.0"), successResult.getCurrentTag());
        assertEquals(GitTag.of("v2.0.0"), upToDateResult.getCurrentTag());
    }

    @Test
    void testGetCurrentTagFailure() {
        assertThrows(IllegalStateException.class, () -> failedResult.getCurrentTag());
    }

    @Test
    void testIsUpToDateFailure() {
        assertThrows(IllegalStateException.class, () -> failedResult.isUpToDate());
    }

    @Test
    void testGetError() {
        assertTrue(successResult.getError().isEmpty());
        assertTrue(upToDateResult.getError().isEmpty());

        assertTrue(failedResult.getError().isPresent());
        assertEquals(error, failedResult.getError().get());
    }

    @Test
    void testStaticFactoryMethods() {
        GitCheckResult success = GitCheckResult.success(release, GitTag.of("v1.0.0"));
        GitCheckResult failure = GitCheckResult.failure(error);

        assertTrue(success.isSuccessful());
        assertFalse(failure.isSuccessful());
    }
}