package com.eternalcode.eternalupdater.data;

public class RemoteInformation {

    private final boolean isAvailableNewVersion;
    private final String currentVersion;
    private final String downloadUri;

    public RemoteInformation(boolean isAvailableNewVersion, String currentVersion, String downloadUri) {
        this.isAvailableNewVersion = isAvailableNewVersion;
        this.currentVersion = currentVersion;
        this.downloadUri = downloadUri;
    }

    public String getDownloadUri() {
        return this.downloadUri;
    }

    public boolean isAvailableNewVersion() {
        return this.isAvailableNewVersion;
    }

    public String getCurrentVersion() {
        return this.currentVersion;
    }

}
