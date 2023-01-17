package com.eternalcode.gitcheck;

import com.eternalcode.gitcheck.git.GitRelease;
import com.eternalcode.gitcheck.git.GitReleaseProvider;
import com.eternalcode.gitcheck.git.GitRepository;
import com.eternalcode.gitcheck.git.GitTag;
import com.eternalcode.gitcheck.github.GitHubReleaseProvider;
import com.eternalcode.gitcheck.shared.Preconditions;
import org.jetbrains.annotations.NotNull;

/**
 * Service for checking if the latest release is up to date.
 * <p>
 *     This service uses {@link GitReleaseProvider} to get the latest release and compares it with the current tag.
 *     The current tag is provided by {@link GitTag#of(String)}
 * <br>
 */
public class GitCheck {

    private final GitReleaseProvider versionProvider;

    /**
     * Creates a new instance of {@link GitCheck} with the default {@link GitHubReleaseProvider}.
     */
    public GitCheck() {
        this(new GitHubReleaseProvider());
    }

    /**
     * Creates a new instance of {@link GitCheck} with the given {@link GitReleaseProvider}.
     *
     * @param versionProvider the version provider
     */
    public GitCheck(@NotNull GitReleaseProvider versionProvider) {
        Preconditions.notNull(versionProvider, "release provider");
        this.versionProvider = versionProvider;
    }

    /**
     * Gets the latest release for the given repository.
     *
     * @param repository the repository
     * @return the latest release
     */
    @NotNull
    public GitRelease getLatestRelease(@NotNull GitRepository repository) {
        Preconditions.notNull(repository, "repository");

        return this.versionProvider.getLatestRelease(repository);
    }

    /**
     * Creates a new instance of {@link GitCheckResult} for the given repository and tag.
     * Result contains the latest release and the current tag.
     * Use {@link GitCheckResult#isUpToDate()} to check if the latest release is up to date.
     *
     * @param repository the repository
     * @param currentTag the current tag
     * @return the result
     */
    @NotNull
    public GitCheckResult checkRelease(@NotNull GitRepository repository, @NotNull GitTag currentTag) {
        Preconditions.notNull(repository, "repository");
        Preconditions.notNull(currentTag, "current tag");

        GitRelease latestRelease = this.getLatestRelease(repository);
        return new GitCheckResult(latestRelease, currentTag);
    }

}
