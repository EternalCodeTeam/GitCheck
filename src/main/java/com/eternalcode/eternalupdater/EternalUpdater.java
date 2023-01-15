package com.eternalcode.eternalupdater;

import com.eternalcode.eternalupdater.data.PluginData;
import com.eternalcode.eternalupdater.data.RemoteInformation;
import com.eternalcode.eternalupdater.util.HttpClient;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

public class EternalUpdater {
    private PluginData pluginData;

    /**
     * @param pluginName Name of the checked plugin
     * @param pluginVersion Current version the plugin
     * @param githubRepo Github remote repository name (ex. eternalcodeteam/eternalcore)
     */
    public EternalUpdater(@NotNull String pluginName, @NotNull String pluginVersion, @NotNull String githubRepo) {
        this.pluginData = new PluginData();
        this.pluginData.setPluginName(pluginName);
        this.pluginData.setPluginVersion(pluginVersion);
        this.pluginData.setGithubRepository(githubRepo);
    }

    public RemoteInformation checkUpdates() {
        JSONObject response = HttpClient.doRequest("repos/" + this.pluginData.getGithubRepository() + "/releases/latest");
        RemoteInformation remoteInformation = new RemoteInformation();

        // TODO: To refactor
        remoteInformation.setCurrentVersion(response.isEmpty() ? this.pluginData.getPluginVersion() : (String) response.get("tag_name"));
        remoteInformation.setAvailableNewVersion(!response.isEmpty() && this.pluginData.getPluginVersion() != (String) response.get("tag_name"));
        remoteInformation.setDownloadUri(response.isEmpty() ? "https://github.com/" + this.pluginData.getGithubRepository() : (String) response.get("zipball_url"));

        return remoteInformation;
    }
}
