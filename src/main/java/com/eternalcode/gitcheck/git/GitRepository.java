package com.eternalcode.gitcheck.git;

import com.eternalcode.gitcheck.shared.Preconditions;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a git repository.
 */
public final class GitRepository {

    private static final String FULL_NAME_FORMAT = "%s/%s";

    private final String owner;
    private final String name;

    /**
     * Creates a new instance of {@link GitRepository} with the given owner and name.
     *
     * @param owner the owner of the repository
     * @param name  the name of the repository
     * @see #of(String, String)
     */
    private GitRepository(@NotNull String owner, @NotNull String name) {
        Preconditions.notNull(owner, "owner");
        Preconditions.notNull(name, "name");
        Preconditions.notEmpty(owner, "owner");
        Preconditions.notEmpty(name, "name");

        this.owner = owner;
        this.name = name;
    }

    /**
     * Creates a new instance of {@link GitRepository} with the given owner and name.
     *
     * @param owner the owner of the repository
     * @param name  the name of the repository
     * @return repository of the given owner and name
     */
    public static GitRepository of(@NotNull String owner, @NotNull String name) {
        return new GitRepository(owner, name);
    }

    @NotNull
    public String getOwner() {
        return this.owner;
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    @NotNull
    public String getFullName() {
        return String.format(FULL_NAME_FORMAT, this.owner, this.name);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        return object instanceof GitRepository that &&
                this.owner.equals(that.owner) &&
                this.name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.owner, this.name);
    }
}
