package com.eternalcode.gitcheck.git;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GitRepositoryTest {

    @Test
    void testSuccessful() {
        GitRepository repository = GitRepository.of("EternalCodeTeam", "GitCheck");

        assertEquals("EternalCodeTeam", repository.getOwner());
        assertEquals("GitCheck", repository.getName());
        assertEquals("EternalCodeTeam/GitCheck", repository.getFullName());
    }

    @Test
    void testNullOwner() {
        assertThrows(IllegalArgumentException.class, () -> GitRepository.of(null, "GitCheck"));
    }

    @Test
    void testNullName() {
        assertThrows(IllegalArgumentException.class, () -> GitRepository.of("EternalCodeTeam", null));
    }

    @Test
    void testHashcodeAndEquals() {
        EqualsVerifier.forClass(GitRepository.class)
                .withNonnullFields("owner", "name")
                .verify();
    }


}