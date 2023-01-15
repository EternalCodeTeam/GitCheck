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
        this.pluginData = new PluginData(githubRepo, pluginVersion, pluginName);
    }

    public RemoteInformation checkUpdates() {
        JSONObject response = HttpClient.doRequest("repos/" + this.pluginData.getGithubRepository() + "/releases/latest");
        Boolean newVersionAvailable = this.pluginData.getPluginVersion() != (String) response.get("tag_name");
        String latestTag = (String) response.get("tag_name");
        String newDownloadUri = (String) response.get("zipball_url");

        return new RemoteInformation(
                newVersionAvailable,
                latestTag,
                newDownloadUri
        );
    }
}
