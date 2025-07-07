package com.eternalcode.gitcheck;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class GitCheckErrorHandlerTest {

    @Test
    void testNoOpHandler() {
        GitCheckErrorHandler handler = GitCheckErrorHandler.noOp();
        GitCheckError error = new GitCheckError(GitCheckErrorType.UNKNOWN, "Test error");

        // Should not throw any exception
        assertDoesNotThrow(() -> handler.handle(error));
    }

    @Test
    void testConsoleHandler() {
        GitCheckErrorHandler handler = GitCheckErrorHandler.console();
        GitCheckError error = new GitCheckError(GitCheckErrorType.UNKNOWN, "Test error");

        // Capture System.err output
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(errContent));

        try {
            handler.handle(error);
            String output = errContent.toString();
            assertTrue(output.contains("GitCheck Error:"));
            assertTrue(output.contains("Test error"));
        } finally {
            System.setErr(originalErr);
        }
    }

    @Test
    void testThrowingHandler() {
        GitCheckErrorHandler handler = GitCheckErrorHandler.throwing();
        GitCheckError error = new GitCheckError(GitCheckErrorType.UNKNOWN, "Test error");

        GitCheckException exception = assertThrows(GitCheckException.class, () ->
                handler.handle(error)
        );

        assertEquals("Test error", exception.getMessage());
    }

    @Test
    void testThrowingHandlerWithCause() {
        GitCheckErrorHandler handler = GitCheckErrorHandler.throwing();
        RuntimeException cause = new RuntimeException("Original cause");
        GitCheckError error = new GitCheckError(GitCheckErrorType.UNKNOWN, "Test error", cause);

        GitCheckException exception = assertThrows(GitCheckException.class, () ->
                handler.handle(error)
        );

        assertEquals("Test error", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}