package com.eternalcode.gitcheck.git;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GitExceptionTest {

    @Test
    void testMessage() {
        GitException exception = new GitException("message");

        assertEquals("message", exception.getMessage());
    }

    @Test
    void testMessageAndCause() {
        RuntimeException runtimeException = new RuntimeException();
        GitException exception = new GitException("message", runtimeException);

        assertEquals("message", exception.getMessage());
        assertEquals(runtimeException, exception.getCause());
    }

}