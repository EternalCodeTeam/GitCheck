package com.eternalcode.gitcheck;

import com.eternalcode.gitcheck.git.GitRelease;
import com.eternalcode.gitcheck.git.GitRepository;
import com.eternalcode.gitcheck.git.GitTag;
import com.eternalcode.gitcheck.mock.MockGitReleaseProvider;
import com.eternalcode.gitcheck.mock.MockErrorGitReleaseProvider;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class GitCheckTest {

    @Test
    void testDefaultConstructor() {
        assertDoesNotThrow(() -> new GitCheck());
    }

    @Test
    void testGetLatestReleaseSuccess() {
        GitCheck gitCheck = new GitCheck(new MockGitReleaseProvider());
        GitRepository repository = GitRepository.of("EternalCodeTeam", "ChatFormatter");

        GitCheckResult result = gitCheck.getLatestRelease(repository);

        assertTrue(result.isSuccessful());
        assertNotNull(result.getLatestRelease());
        assertTrue(result.getError().isEmpty());
    }

    @Test
    void testGetLatestReleaseWithNullRepository() {
        GitCheck gitCheck = new GitCheck(new MockGitReleaseProvider());

        assertThrows(IllegalArgumentException.class, () -> gitCheck.getLatestRelease(null));
    }

    @Test
    void testCheckReleaseSuccess() {
        GitCheck gitCheck = new GitCheck(new MockGitReleaseProvider());
        GitRepository repository = GitRepository.of("EternalCodeTeam", "ChatFormatter");
        GitTag tag = GitTag.of("v1.0.0");

        GitCheckResult result = gitCheck.checkRelease(repository, tag);

        assertTrue(result.isSuccessful());
        assertNotNull(result.getLatestRelease());
        assertEquals(tag, result.getCurrentTag());
        assertTrue(result.getError().isEmpty());
    }

    @Test
    void testStatusCodeErrorHandler() {
        AtomicReference<GitCheckError> capturedError = new AtomicReference<>();

        GitCheck gitCheck = new GitCheck(new MockErrorGitReleaseProvider())
                .onStatusCode(404, capturedError::set);

        GitRepository repository = GitRepository.of("EternalCodeTeam", "ChatFormatter");
        GitCheckResult result = gitCheck.checkRelease(repository, GitTag.of("v1.0.0"));

        assertFalse(result.isSuccessful());
        assertNotNull(capturedError.get());
        assertEquals(404, capturedError.get().getStatusCode());
    }


    @Test
    void testBuiltInErrorHandlers() {
        assertDoesNotThrow(() -> {
            GitCheck gitCheck = new GitCheck(new MockGitReleaseProvider())
                    .onError(GitCheckErrorHandler.noOp())
                    .onError(GitCheckErrorHandler.console());
        });
    }

    @Test
    void testThrowingErrorHandler() {
        GitCheck gitCheck = new GitCheck(new MockErrorGitReleaseProvider())
                .onError(GitCheckErrorHandler.throwing());

        GitRepository repository = GitRepository.of("EternalCodeTeam", "ChatFormatter");

        assertThrows(GitCheckException.class, () ->
                gitCheck.checkRelease(repository, GitTag.of("v1.0.0"))
        );
    }

    @Test
    void testNullErrorHandler() {
        GitCheck gitCheck = new GitCheck(new MockGitReleaseProvider());

        assertThrows(IllegalArgumentException.class, () ->
                gitCheck.onError(null)
        );
    }

    @Test
    void testNullErrorTypeHandler() {
        GitCheck gitCheck = new GitCheck(new MockGitReleaseProvider());

        assertThrows(IllegalArgumentException.class, () ->
                gitCheck.onErrorType(null, GitCheckErrorHandler.noOp())
        );

        assertThrows(IllegalArgumentException.class, () ->
                gitCheck.onErrorType(GitCheckErrorType.UNKNOWN, null)
        );
    }

    @Test
    void testNullStatusCodeHandler() {
        GitCheck gitCheck = new GitCheck(new MockGitReleaseProvider());

        assertThrows(IllegalArgumentException.class, () ->
                gitCheck.onStatusCode(404, null)
        );
    }
}