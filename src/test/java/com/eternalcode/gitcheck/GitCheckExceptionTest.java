package com.eternalcode.gitcheck;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GitCheckExceptionTest {

    @Test
    void testMessageConstructor() {
        GitCheckException exception = new GitCheckException("Test message");

        assertEquals("Test message", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testMessageAndCauseConstructor() {
        RuntimeException cause = new RuntimeException("Original cause");
        GitCheckException exception = new GitCheckException("Test message", cause);

        assertEquals("Test message", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}