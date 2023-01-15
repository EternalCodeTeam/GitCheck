package com.eternalcode.eternalupdater.data;

public class RemoteInformation {
    private boolean isAvailableNewVersion;
    private String currentVersion;
    private String downloadUri;

    public String getDownloadUri() {
        return downloadUri;
    }

    public void setDownloadUri(String downloadUri) {
        this.downloadUri = downloadUri;
    }

    public boolean isAvailableNewVersion() {
        return isAvailableNewVersion;
    }

    public void setAvailableNewVersion(boolean availableNewVersion) {
        isAvailableNewVersion = availableNewVersion;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }
}
