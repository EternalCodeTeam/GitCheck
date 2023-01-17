package com.eternalcode.gitcheck.git;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GitTagTest {

    @Test
    void testSuccessfulTag() {
        GitTag tag = GitTag.of("v1.0.0");
        assertEquals("v1.0.0", tag.getTag());
    }

    @Test
    void testHashcodeAndEquals() {
        EqualsVerifier.forClass(GitTag.class)
                .withNonnullFields("tag")
                .verify();
    }

    @Test
    void testNullTag() {
        assertThrows(IllegalArgumentException.class, () -> GitTag.of(null));
    }

}