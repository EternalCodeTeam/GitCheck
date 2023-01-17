package com.eternalcode.gitcheck;

import com.eternalcode.gitcheck.git.GitRelease;
import com.eternalcode.gitcheck.git.GitTag;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GitCheckResultTest {

    private final GitRelease release = GitRelease.builder()
            .name("test")
            .branch("master")
            .tag(GitTag.of("v2.0.0"))
            .pageUrl("https://repo.eternalcode.pl/releases")
            .publishedAt(Instant.parse("2020-01-01T00:00:00Z"))
            .build();

    private final GitCheckResult noActualResult = new GitCheckResult(this.release, GitTag.of("v1.0.0"));
    private final GitCheckResult actualResult = new GitCheckResult(this.release, GitTag.of("v2.0.0"));

    @Test
    void testIsUpToDate() {
        assertFalse(this.noActualResult.isUpToDate());
        assertTrue(this.actualResult.isUpToDate());
    }

    @Test
    void testGetRelease() {
        assertEquals(this.release, this.noActualResult.getLatestRelease());
        assertEquals(this.release, this.actualResult.getLatestRelease());
    }

    @Test
    void testGetActualTag() {
        assertEquals(GitTag.of("v1.0.0"), this.noActualResult.getCurrentTag());
        assertEquals(GitTag.of("v2.0.0"), this.actualResult.getCurrentTag());
    }

}