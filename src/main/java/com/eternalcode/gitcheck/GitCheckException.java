package com.eternalcode.gitcheck;

import com.eternalcode.gitcheck.git.GitException;

/**
 * Exception thrown when git check operation fails.
 */
public class GitCheckException extends GitException {

    public GitCheckException(String message) {
        super(message);
    }

    public GitCheckException(String message, Throwable cause) {
        super(message, cause);
    }
}