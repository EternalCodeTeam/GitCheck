# EternalUpdater
✔ Updater for EternalCode plugins


# Example usage
```java
import com.eternalcode.eternalupdater.EternalUpdater;

EternalUpdater eternalUpdater = new EternalUpdater("name", "currentPluginVer", "githubrepo");
RemoteInformation pluginUpdate = eternalUpdater.checkUpdates();
```