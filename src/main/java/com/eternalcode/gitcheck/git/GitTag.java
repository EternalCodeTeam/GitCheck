package com.eternalcode.gitcheck.git;

import com.eternalcode.gitcheck.shared.Preconditions;
import java.util.Objects;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a git tag.
 */
public final class GitTag {

    private final String tag;

    /**
     * Creates a new instance of {@link GitTag} with the given tag.
     *
     * @param tag the tag
     * @see #of(String)
     */
    private GitTag(@NotNull String tag) {
        Preconditions.notNull(tag, "tag");
        this.tag = tag;
    }

    /**
     * Creates a new instance of {@link GitTag} with the given tag.
     * <p>
     * Tag should be in the format of {@code v1.0.0}, but it can be anything.
     * <br>
     * This method does not validate the tag.
     * <br>
     * </p>
     *
     * @param tag the tag
     * @return the git tag
     * @throws IllegalArgumentException if the tag is null
     */
    @Contract("_ -> new")
    public static GitTag of(@NotNull String tag) {
        return new GitTag(tag);
    }

    @NotNull
    public String getTag() {
        return this.tag;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof GitTag gitTag)) {
            return false;
        }

        return this.tag.equals(gitTag.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.tag);
    }

    @Override
    public String toString() {
        return this.tag;
    }
}
