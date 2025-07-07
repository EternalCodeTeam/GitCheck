package com.eternalcode.gitcheck;

import org.jetbrains.annotations.NotNull;

/**
 * Interface for handling git check errors.
 */
@FunctionalInterface
public interface GitCheckErrorHandler {

    /**
     * Creates a no-op error handler.
     *
     * @return no-op error handler
     */
    @NotNull
    static GitCheckErrorHandler noOp() {
        return error -> {};
    }
    /**
     * Creates an error handler that prints to console.
     *
     * @return console error handler
     */
    @NotNull
    static GitCheckErrorHandler console() {
        return error -> System.err.println("GitCheck Error: " + error);
    }
    /**
     * Creates an error handler that throws exceptions.
     *
     * @return throwing error handler
     */
    @NotNull
    static GitCheckErrorHandler throwing() {
        return error -> {
            throw new GitCheckException(error.getMessage(), error.getCause());
        };
    }
    /**
     * Handles the error.
     *
     * @param error the error to handle
     */
    void handle(@NotNull GitCheckError error);
}