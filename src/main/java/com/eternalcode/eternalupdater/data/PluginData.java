package com.eternalcode.eternalupdater.data;

public class PluginData {
    private final String githubRepository;
    private final String pluginVersion;
    private final String pluginName;

    public PluginData(String githubRepository, String pluginVersion, String pluginName) {
        this.githubRepository = githubRepository;
        this.pluginVersion = pluginVersion;
        this.pluginName = pluginName;
    }

    public String getGithubRepository() {
        return this.githubRepository;
    }

    public String getPluginVersion() {
        return this.pluginVersion;
    }

    public String getPluginName() {
        return this.pluginName;
    }

}
