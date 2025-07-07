package com.eternalcode.gitcheck;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an error that occurred during git check operation.
 */
public class GitCheckError {

    private final GitCheckErrorType type;
    private final String message;
    private final int statusCode;
    private final Throwable cause;

    public GitCheckError(
            @NotNull GitCheckErrorType type,
            @NotNull String message,
            int statusCode,
            @Nullable Throwable cause
    ) {
        this.type = type;
        this.message = message;
        this.statusCode = statusCode;
        this.cause = cause;
    }

    public GitCheckError(@NotNull GitCheckErrorType type, @NotNull String message, @Nullable Throwable cause) {
        this(type, message, -1, cause);
    }

    public GitCheckError(@NotNull GitCheckErrorType type, @NotNull String message) {
        this(type, message, -1, null);
    }

    @NotNull
    public GitCheckErrorType getType() {
        return type;
    }

    @NotNull
    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Nullable
    public Throwable getCause() {
        return cause;
    }

    public boolean hasStatusCode() {
        return statusCode != -1;
    }

    @Override
    public String toString() {
        return "GitCheckError{" +
                "type=" + type +
                ", message='" + message + '\'' +
                (hasStatusCode() ? ", statusCode=" + statusCode : "") +
                '}';
    }
}