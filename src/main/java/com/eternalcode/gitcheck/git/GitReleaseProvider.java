package com.eternalcode.gitcheck.git;

public interface GitReleaseProvider {

    GitRelease getLatestRelease(GitRepository repository);

}
