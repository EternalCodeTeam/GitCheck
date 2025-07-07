package com.eternalcode.gitcheck;

import com.eternalcode.gitcheck.git.GitRelease;
import com.eternalcode.gitcheck.git.GitTag;
import com.eternalcode.gitcheck.shared.Preconditions;
import java.util.Optional;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the result of a git check.
 */
public class GitCheckResult {

    private final GitRelease latestRelease;
    private final GitTag currentTag;
    private final GitCheckError error;

    @ApiStatus.Internal
    GitCheckResult(@NotNull GitRelease latestRelease, @NotNull GitTag currentTag) {
        Preconditions.notNull(latestRelease, "latest release");
        Preconditions.notNull(currentTag, "current tag");

        this.latestRelease = latestRelease;
        this.currentTag = currentTag;
        this.error = null;
    }

    @ApiStatus.Internal
    GitCheckResult(@NotNull GitCheckError error) {
        Preconditions.notNull(error, "error");

        this.latestRelease = null;
        this.currentTag = null;
        this.error = error;
    }

    /**
     * Creates a successful result.
     *
     * @param latestRelease the latest release
     * @param currentTag    the current tag
     * @return successful result
     */
    @ApiStatus.Internal
    @NotNull
    public static GitCheckResult success(@NotNull GitRelease latestRelease, @NotNull GitTag currentTag) {
        return new GitCheckResult(latestRelease, currentTag);
    }

    /**
     * Creates a failed result.
     *
     * @param error the error
     * @return failed result
     */
    @ApiStatus.Internal
    @NotNull
    public static GitCheckResult failure(@NotNull GitCheckError error) {
        return new GitCheckResult(error);
    }

    /**
     * Checks if the operation was successful.
     *
     * @return {@code true} if successful, {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean isSuccessful() {
        return this.error == null;
    }

    /**
     * Gets the latest release.
     *
     * @return the latest release
     * @throws IllegalStateException if the operation was not successful
     */
    @NotNull
    public GitRelease getLatestRelease() {
        if (!isSuccessful()) {
            throw new IllegalStateException("Cannot get latest release from failed result. Check isSuccessful() first.");
        }
        return this.latestRelease;
    }

    /**
     * Gets the current tag.
     *
     * @return the current tag
     * @throws IllegalStateException if the operation was not successful
     */
    @NotNull
    public GitTag getCurrentTag() {
        if (!isSuccessful()) {
            throw new IllegalStateException("Cannot get current tag from failed result. Check isSuccessful() first.");
        }
        return this.currentTag;
    }

    /**
     * Gets the error if the operation failed.
     *
     * @return the error or empty if successful
     */
    @NotNull
    public Optional<GitCheckError> getError() {
        return Optional.ofNullable(this.error);
    }

    /**
     * Checks if the latest release is up to date.
     *
     * @return {@code true} if the latest release is up to date, {@code false} otherwise
     * @throws IllegalStateException if the operation was not successful
     */
    @Contract(pure = true)
    public boolean isUpToDate() {
        if (!isSuccessful()) {
            throw new IllegalStateException("Cannot check if up to date from failed result. Check isSuccessful() first.");
        }
        return this.latestRelease.getTag().equals(this.currentTag);
    }
}