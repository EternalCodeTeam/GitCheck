package com.eternalcode.updater;

import org.jetbrains.annotations.Contract;

/**
 * The PluginData class is used to store information about a plugin.
 */
public class PluginData {

    private final String githubRepository;
    private final String pluginVersion;
    private final String pluginName;

    /**
     * Creates a new instance of PluginData with the GitHub repository name, plugin version, and plugin name.
     *
     * @param githubRepository The GitHub repository name
     * @param pluginVersion The version of the plugin
     * @param pluginName The name of the plugin
     */
    public PluginData(String githubRepository, String pluginVersion, String pluginName) {
        this.githubRepository = githubRepository;
        this.pluginVersion = pluginVersion;
        this.pluginName = pluginName;
    }

    /**
     * Returns the GitHub repository name
     *
     * @return The GitHub repository name
     */
    @Contract(pure = true)
    public String getGithubRepository() {
        return this.githubRepository;
    }

    /**
     * Returns the version of the plugin
     *
     * @return The version of the plugin
     */
    @Contract(pure = true)
    public String getPluginVersion() {
        return this.pluginVersion;
    }

    /**
     * Returns the name of the plugin
     *
     * @return The name of the plugin
     */
    @Contract(pure = true)
    public String getPluginName() {
        return this.pluginName;
    }
}
