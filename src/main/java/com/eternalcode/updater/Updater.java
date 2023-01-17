package com.eternalcode.updater;

import com.eternalcode.updater.http.RemoteInformation;
import com.eternalcode.updater.http.HttpClient;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

/**
 * The Updater class is used to check for updates for a plugin.
 */
public class Updater {

    private final PluginData pluginData;

    /**
     * Creates a new instance of Updater with the plugin name, plugin version, and GitHub remote repository name.
     *
     * @param pluginName The name of the plugin
     * @param pluginVersion The current version of the plugin
     * @param githubRepository The name of the GitHub remote repository (e.g. eternalcodeteam/eternalcore)
     */
    public Updater(@NotNull String pluginName, @NotNull String pluginVersion, @NotNull String githubRepository) {
        this.pluginData = new PluginData(githubRepository, pluginVersion, pluginName);
    }

    /**
     * Checks for updates for the plugin.
     *
     * @return Remote plugin information
     * @throws RuntimeException If there is a connection problem or the remote repository is not found
     */
    @Contract(pure = true)
    public RemoteInformation checkUpdates() {
        JSONObject response = HttpClient.doRequest("repos/" + this.pluginData.getGithubRepository() + "/releases/latest");
        boolean newVersionAvailable = !this.pluginData.getPluginVersion().equals(response.get("tag_name"));
        String latestTag = (String) response.get("tag_name");
        String newDownloadUri = (String) response.get("zipball_url");

        return new RemoteInformation(
                newVersionAvailable,
                latestTag,
                newDownloadUri
        );
    }

}
