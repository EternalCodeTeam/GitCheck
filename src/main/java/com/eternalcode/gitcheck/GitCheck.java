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
 */
public class GitCheck {

    private final GitReleaseProvider versionProvider;
    private GitCheckErrorHandler errorHandler;

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
        this.errorHandler = GitCheckErrorHandler.noOp();
    }

    /**
     * Sets the error handler for this GitCheck instance.
     *
     * @param errorHandler the error handler
     * @return this instance for method chaining
     */
    @NotNull
    public GitCheck onError(@NotNull GitCheckErrorHandler errorHandler) {
        Preconditions.notNull(errorHandler, "error handler");
        this.errorHandler = errorHandler;
        return this;
    }

    /**
     * Sets error handler for specific status codes.
     *
     * @param statusCode the status code to handle
     * @param handler    the handler for this status code
     * @return this instance for method chaining
     */
    @NotNull
    public GitCheck onStatusCode(int statusCode, @NotNull GitCheckErrorHandler handler) {
        Preconditions.notNull(handler, "handler");

        GitCheckErrorHandler previousHandler = this.errorHandler;
        this.errorHandler = error -> {
            if (error.hasStatusCode() && error.getStatusCode() == statusCode) {
                handler.handle(error);
            }
            else {
                previousHandler.handle(error);
            }
        };
        return this;
    }

    /**
     * Sets error handler for specific error types.
     *
     * @param errorType the error type to handle
     * @param handler   the handler for this error type
     * @return this instance for method chaining
     */
    @NotNull
    public GitCheck onErrorType(@NotNull GitCheckErrorType errorType, @NotNull GitCheckErrorHandler handler) {
        Preconditions.notNull(errorType, "error type");
        Preconditions.notNull(handler, "handler");

        GitCheckErrorHandler previousHandler = this.errorHandler;
        this.errorHandler = error -> {
            if (error.getType() == errorType) {
                handler.handle(error);
            }
            else {
                previousHandler.handle(error);
            }
        };
        return this;
    }

    /**
     * Gets the latest release for the given repository.
     *
     * @param repository the repository
     * @return the latest release, or null if error occurred
     */
    @NotNull
    public GitCheckResult getLatestRelease(@NotNull GitRepository repository) {
        Preconditions.notNull(repository, "repository");

        try {
            GitRelease release = this.versionProvider.getLatestRelease(repository);
            return GitCheckResult.success(release, GitTag.of("unknown"));
        }
        catch (Exception exception) {
            GitCheckError error = mapExceptionToError(exception);
            this.errorHandler.handle(error);
            return GitCheckResult.failure(error);
        }
    }

    /**
     * Creates a new instance of {@link GitCheckResult} for the given repository and tag.
     *
     * @param repository the repository
     * @param currentTag the current tag
     * @return the result
     */
    @NotNull
    public GitCheckResult checkRelease(@NotNull GitRepository repository, @NotNull GitTag currentTag) {
        Preconditions.notNull(repository, "repository");
        Preconditions.notNull(currentTag, "current tag");

        try {
            GitRelease latestRelease = this.versionProvider.getLatestRelease(repository);
            return GitCheckResult.success(latestRelease, currentTag);
        }
        catch (Exception exception) {
            GitCheckError error = mapExceptionToError(exception);
            this.errorHandler.handle(error);
            return GitCheckResult.failure(error);
        }
    }

    private GitCheckError mapExceptionToError(Exception exception) {
        String message = exception.getMessage();

        if (message.contains("404")) {
            if (message.contains("repository")) {
                return new GitCheckError(GitCheckErrorType.REPOSITORY_NOT_FOUND, message, 404, exception);
            }
            else {
                return new GitCheckError(GitCheckErrorType.RELEASE_NOT_FOUND, message, 404, exception);
            }
        }

        if (message.contains("403")) {
            return new GitCheckError(GitCheckErrorType.RATE_LIMIT_EXCEEDED, message, 403, exception);
        }

        if (message.contains("401")) {
            return new GitCheckError(GitCheckErrorType.AUTHENTICATION_REQUIRED, message, 401, exception);
        }

        if (message.contains("Unexpected response code")) {
            try {
                int statusCode = Integer.parseInt(message.replaceAll(".*: (\\d+).*", "$1"));
                return new GitCheckError(GitCheckErrorType.HTTP_ERROR, message, statusCode, exception);
            }
            catch (NumberFormatException e) {
                return new GitCheckError(GitCheckErrorType.HTTP_ERROR, message, exception);
            }
        }

        if (message.contains("Invalid JSON") || message.contains("not a JSON object")) {
            return new GitCheckError(GitCheckErrorType.INVALID_RESPONSE, message, exception);
        }

        if (exception instanceof java.io.IOException) {
            return new GitCheckError(GitCheckErrorType.NETWORK_ERROR, message, exception);
        }

        return new GitCheckError(GitCheckErrorType.UNKNOWN, message, exception);
    }
}