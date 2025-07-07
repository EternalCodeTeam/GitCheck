package com.eternalcode.gitcheck;

/**
 * Types of errors that can occur during git check operations.
 */
public enum GitCheckErrorType {
    /**
     * Repository not found (404).
     */
    REPOSITORY_NOT_FOUND,

    /**
     * Release not found (404).
     */
    RELEASE_NOT_FOUND,

    /**
     * Rate limit exceeded (403).
     */
    RATE_LIMIT_EXCEEDED,

    /**
     * Authentication required (401).
     */
    AUTHENTICATION_REQUIRED,

    /**
     * Network connection error.
     */
    NETWORK_ERROR,

    /**
     * Invalid JSON response.
     */
    INVALID_RESPONSE,

    /**
     * HTTP error with specific status code.
     */
    HTTP_ERROR,

    /**
     * Unknown error.
     */
    UNKNOWN
}