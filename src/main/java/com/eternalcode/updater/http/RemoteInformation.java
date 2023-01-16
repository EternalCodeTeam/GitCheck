package com.eternalcode.updater.http;

import org.jetbrains.annotations.Contract;

/**
 * The RemoteInformation class is used to store information about a plugin update.
 */
public class RemoteInformation {
    private final boolean isAvailableNewVersion;
    private final String currentVersion;
    private final String downloadUri;

    /**
     * Creates a new instance of RemoteInformation with the update availability, current version, and download URI.
     *
     * @param isAvailableNewVersion True if a new version is available, false otherwise
     * @param currentVersion The current version of the plugin
     * @param downloadUri The URI to download the update
     */
    public RemoteInformation(boolean isAvailableNewVersion, String currentVersion, String downloadUri) {
        this.isAvailableNewVersion = isAvailableNewVersion;
        this.currentVersion = currentVersion;
        this.downloadUri = downloadUri;
    }

    /**
     * Returns the URI to download the update
     *
     * @return The URI to download the update
     */
    @Contract(pure = true)
    public String getDownloadUri() {
        return this.downloadUri;
    }

    /**
     * Indicates if a new version is available
     *
     * @return True if a new version is available, false otherwise
     */
    @Contract(pure = true)
    public boolean isAvailableNewVersion() {
        return this.isAvailableNewVersion;
    }

    /**
     * Returns the current version of the plugin
     *
     * @return The current version of the plugin
     */
    @Contract(pure = true)
    public String getCurrentVersion() {
        return this.currentVersion;
    }
}
