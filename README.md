# Updater 

#### Updater is a library for checking for updates for your plugin on GitHub. It uses the GitHub API to get information about the latest release of your plugin and compare it to the current version of your plugin.

### Usage
First, you need to create an instance of the `Updater` class by passing the plugin name, current version, and Github repository name as arguments. Then, you can call the `checkUpdates()` method to check for updates and get the `RemoteInformation` object.

The `RemoteInformation` object contains information about the update, such as the availability of a new version, the current version, and the download URI.

```java
if (remoteInformation.isAvailableNewVersion()) {
    System.out.println("A new version is available: " + remoteInformation.getCurrentVersion());
    System.out.println("Download URI: " + remoteInformation.getDownloadUri());
} else {
    System.out.println("You are already running the latest version.");
}
```

### Example
Here's an example of how to can use the Updater in `Spigot` plugin

```java

import com.eternalcode.updater.Updater;
import com.eternalcode.updater.http.RemoteInformation;

public class MyPlugin {

    private Updater updater;

    public void onEnable() {
        updater = new Updater("MyPlugin", "1.0", "MyGithubUsername/MyPlugin");
        checkForUpdates();
    }

    private void checkForUpdates() {
        RemoteInformation remoteInformation = updater.checkUpdates();
        if (remoteInformation.isAvailableNewVersion()) {
            System.out.println("A new version is available: " + remoteInformation.getCurrentVersion());
            System.out.println("Download URI: " + remoteInformation.getDownloadUri());
        } else {
            System.out.println("You are already running the latest version.");
        }
    }
}
```

### Maven/Gradle
Get the latest version from [EternalCode Repository](https://repo.eternalcode.pl/#/releases/com/eternalcode/eternalupdater)

#### gradle groovy
```groovy
maven { url "https://repo.eternalcode.pl/releases" }

implementation "com.eternalcode:updater:{VERSION}"
```

```kotlin
maven { url = uri("https://repo.eternalcode.pl/releases") }

implementation("com.eternalcode:updater:{VERSION}")
```

```xml
<repository>
  <id>eternalcode-reposilite-releases</id>
  <name>EternalCode Repository</name>
  <url>https://repo.eternalcode.pl/releases</url>
</repository>

<dependency>
<groupId>com.eternalcode</groupId>
<artifactId>updater</artifactId>
<version>{VERSION}</version>
</dependency>
```



