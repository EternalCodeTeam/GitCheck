package com.eternalcode.eternalupdater.data;

public class PluginData {
    private String githubRepository;
    private String pluginVersion;
    private String pluginName;

    public PluginData(String githubRepository, String pluginVersion, String pluginName) {
        this.githubRepository = githubRepository;
        this.pluginVersion = pluginVersion;
        this.pluginName = pluginName;
    }

    public String getGithubRepository() {
        return githubRepository;
    }

    public void setGithubRepository(String githubRepository) {
        this.githubRepository = githubRepository;
    }

    public String getPluginVersion() {
        return pluginVersion;
    }

    public void setPluginVersion(String pluginVersion) {
        this.pluginVersion = pluginVersion;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }
}
