package com.eternalcode.gitcheck;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GitCheckErrorTest {

    @Test
    void testErrorWithAllParameters() {
        Throwable cause = new RuntimeException("Test cause");
        GitCheckError error = new GitCheckError(
                GitCheckErrorType.REPOSITORY_NOT_FOUND,
                "Test message",
                404,
                cause
        );

        assertEquals(GitCheckErrorType.REPOSITORY_NOT_FOUND, error.getType());
        assertEquals("Test message", error.getMessage());
        assertEquals(404, error.getStatusCode());
        assertEquals(cause, error.getCause());
        assertTrue(error.hasStatusCode());
    }

    @Test
    void testErrorWithoutStatusCode() {
        Throwable cause = new RuntimeException("Test cause");
        GitCheckError error = new GitCheckError(
                GitCheckErrorType.NETWORK_ERROR,
                "Test message",
                cause
        );

        assertEquals(GitCheckErrorType.NETWORK_ERROR, error.getType());
        assertEquals("Test message", error.getMessage());
        assertEquals(-1, error.getStatusCode());
        assertEquals(cause, error.getCause());
        assertFalse(error.hasStatusCode());
    }

    @Test
    void testErrorWithoutCause() {
        GitCheckError error = new GitCheckError(
                GitCheckErrorType.INVALID_RESPONSE,
                "Test message"
        );

        assertEquals(GitCheckErrorType.INVALID_RESPONSE, error.getType());
        assertEquals("Test message", error.getMessage());
        assertEquals(-1, error.getStatusCode());
        assertNull(error.getCause());
        assertFalse(error.hasStatusCode());
    }

    @Test
    void testToString() {
        GitCheckError errorWithStatus = new GitCheckError(
                GitCheckErrorType.REPOSITORY_NOT_FOUND,
                "Test message",
                404,
                null
        );

        GitCheckError errorWithoutStatus = new GitCheckError(
                GitCheckErrorType.NETWORK_ERROR,
                "Test message"
        );

        String toStringWithStatus = errorWithStatus.toString();
        String toStringWithoutStatus = errorWithoutStatus.toString();

        assertTrue(toStringWithStatus.contains("statusCode=404"));
        assertFalse(toStringWithoutStatus.contains("statusCode"));

        assertTrue(toStringWithStatus.contains("REPOSITORY_NOT_FOUND"));
        assertTrue(toStringWithoutStatus.contains("NETWORK_ERROR"));
    }
}