package com.eternalcode.gitcheck;

import com.eternalcode.gitcheck.git.GitRelease;
import com.eternalcode.gitcheck.git.GitRepository;
import com.eternalcode.gitcheck.git.GitTag;
import com.eternalcode.gitcheck.mock.MockGitReleaseProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GitCheckTest {

    @Test
    void testDefaultConstructor() {
        assertDoesNotThrow(() -> new GitCheck());
    }

    @Test
    void testGetLatestRelease() {
        GitCheck gitCheck = new GitCheck(new MockGitReleaseProvider());
        GitRepository repository = GitRepository.of("EternalCodeTeam", "ChatFormatter");

        GitRelease release = gitCheck.getLatestRelease(repository);

        assertNotNull(release);
    }

    @Test
    void testGetLatestReleaseWithNullRepository() {
        GitCheck gitCheck = new GitCheck(new MockGitReleaseProvider());

        assertThrows(IllegalArgumentException.class, () -> gitCheck.getLatestRelease(null));
    }

    @Test
    void testCheckRelease() {
        GitCheck gitCheck = new GitCheck(new MockGitReleaseProvider());
        GitRepository repository = GitRepository.of("EternalCodeTeam", "ChatFormatter");
        GitTag tag = GitTag.of("v1.0.0");

        GitCheckResult result = gitCheck.checkRelease(repository, tag);

        assertNotNull(result);
        assertNotNull(result.getLatestRelease());
        assertEquals(tag, result.getCurrentTag());
    }

}