package com.eternalcode.gitcheck.git;

import com.eternalcode.gitcheck.shared.Preconditions;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

public final class GitRelease {

    private final String name;
    private final String branch;
    private final GitTag tag;
    private final String pageUrl;
    private final Instant publishedAt;

    private GitRelease(@NotNull String name, @NotNull String branch, @NotNull GitTag tag, @NotNull String pageUrl, @NotNull Instant publishedAt) {
        this.name = name;
        this.branch = branch;
        this.tag = tag;
        this.pageUrl = pageUrl;
        this.publishedAt = publishedAt;
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    @NotNull
    public String getBranch() {
        return this.branch;
    }

    @NotNull
    public GitTag getTag() {
        return this.tag;
    }

    @NotNull
    public String getPageUrl() {
        return this.pageUrl;
    }

    @NotNull
    public Instant getPublishedAt() {
        return this.publishedAt;
    }

    @NotNull
    @Contract("-> new")
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private String branch;
        private GitTag tag;
        private String pageUrl;
        private Instant publishedAt;

        public Builder name(@NotNull String name) {
            Preconditions.notNull(name, "name");
            this.name = name;
            return this;
        }

        public Builder branch(@NotNull String branch) {
            Preconditions.notNull(branch, "branch");
            this.branch = branch;
            return this;
        }

        public Builder tag(@NotNull GitTag tag) {
            Preconditions.notNull(tag, "tag");
            this.tag = tag;
            return this;
        }

        public Builder pageUrl(@NotNull String pageUrl) {
            Preconditions.notNull(pageUrl, "page url");
            this.pageUrl = pageUrl;
            return this;
        }

        public Builder publishedAt(@NotNull Instant publishedAt) {
            Preconditions.notNull(publishedAt, "published at");
            this.publishedAt = publishedAt;
            return this;
        }

        public GitRelease build() {
            Preconditions.notNull(this.name, "name");
            Preconditions.notNull(this.branch, "branch");
            Preconditions.notNull(this.tag, "tag");
            Preconditions.notNull(this.pageUrl, "page url");
            Preconditions.notNull(this.publishedAt, "publishedAt");

            return new GitRelease(this.name, this.branch, this.tag, this.pageUrl, this.publishedAt);
        }

    }

}
