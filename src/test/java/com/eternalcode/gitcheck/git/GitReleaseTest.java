package com.eternalcode.gitcheck.git;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GitReleaseTest {

    private final String name = "GitCheck 1.0.";
    private final String branch = "master";
    private final GitTag tag = GitTag.of("v1.0.0");
    private final String pageUrl = "https://github.com/EternalCodeTeam/EternalCore/releases/tag/v1.0.0";
    private final Instant time = Instant.now();

    @Test
    void testSuccessfulBuilder() {
        GitRelease release = GitRelease.builder()
                .name(this.name)
                .branch(this.branch)
                .tag(this.tag)
                .pageUrl(this.pageUrl)
                .publishedAt(this.time)
                .build();

        assertEquals(this.name, release.getName());
        assertEquals(this.branch, release.getBranch());
        assertEquals(this.tag, release.getTag());
        assertEquals(this.pageUrl, release.getPageUrl());
        assertEquals(this.time, release.getPublishedAt());
    }

    @Test
    void testNotFinishedBuilder() {
        assertThrows(IllegalArgumentException.class, () -> GitRelease.builder().build());

        assertThrows(IllegalArgumentException.class, () -> GitRelease.builder()
                .branch(this.branch)
                .tag(this.tag)
                .pageUrl(this.pageUrl)
                .publishedAt(this.time)
                .build());

        assertThrows(IllegalArgumentException.class, () -> GitRelease.builder()
                .name(this.name)
                .tag(this.tag)
                .pageUrl(this.pageUrl)
                .publishedAt(this.time)
                .build());

        assertThrows(IllegalArgumentException.class, () -> GitRelease.builder()
                .name(this.name)
                .branch(this.branch)
                .pageUrl(this.pageUrl)
                .publishedAt(this.time)
                .build());

        assertThrows(IllegalArgumentException.class, () -> GitRelease.builder()
                .name(this.name)
                .branch(this.branch)
                .tag(this.tag)
                .publishedAt(this.time)
                .build());

        assertThrows(IllegalArgumentException.class, () -> GitRelease.builder()
                .name(this.name)
                .branch(this.branch)
                .tag(this.tag)
                .pageUrl(this.pageUrl)
                .build());
    }

    @Test
    void testNullBuilder() {
        assertThrows(IllegalArgumentException.class, () -> GitRelease.builder()
                .name(null));

        assertThrows(IllegalArgumentException.class, () -> GitRelease.builder()
                .branch(null));

        assertThrows(IllegalArgumentException.class, () -> GitRelease.builder()
                .tag(null));

        assertThrows(IllegalArgumentException.class, () -> GitRelease.builder()
                .pageUrl(null));

        assertThrows(IllegalArgumentException.class, () -> GitRelease.builder()
                .publishedAt(null));
    }

}