![](/assets/readme-banner.png)

# ⚠️ This repository has been deprecated since November 15th, 2025. If you would like to use an updater system, please refer to [EternalCodeCommons](https://github.com/EternalCodeTeam/EternalCodeCommons)]

# ✔ GitCheck
GitCheck is a Java library that makes it easy to check for updates to a GitHub repository. 
It utilizes the GitHub API to retrieve information about the latest release and compares it to the current version of your application. 
With GitCheck, you can ensure that your users are always running the latest version of your software.

## Features
- Simple and easy-to-use API
- Lightweight and efficient
- Supports Java 9 and above
- Utilizes the GitHub API for retrieving release information

## Installation

To use GitCheck in your project, if you are using Gradle, add the following to your `build.gradle` file:

```kotlin
maven { url = uri("https://repo.eternalcode.pl/releases") }
```

```kotlin
implementation("com.eternalcode:gitcheck:1.0.0")
```

Or, if you are using Maven, add the following to your `pom.xml` file:

```xml
<repository>
    <id>eternalcode-releases</id>
    <url>https://repo.eternalcode.pl/releases</url>
</repository>
```

```xml
<dependency>
    <groupId>com.eternalcode</groupId>
    <artifactId>gitcheck</artifactId>
    <version>1.0.0</version>
</dependency>
```

## API Usage

To use GitCheck, you need to create an instance of the `GitCheck` class.
Create `GitRepository` and `GitTag` objects to specify the repository and the current version of your application.
Then, call the `checkRelease` method to check for updates.

```java
public class MyApplication {

    public static void main(String[] args) {
        GitCheck gitCheck = new GitCheck();
        GitRepository repository = GitRepository.of("Owner", "Project");

        GitCheckResult result = gitCheck.checkRelease(repository, GitTag.of("v1.0.0"));

        if (!result.isUpToDate()) {
            GitRelease release = result.getLatestRelease();
            GitTag tag = release.getTag();

            System.out.println("A new version is available: " + tag.getTag());
            System.out.println("See release page: " + release.getPageUrl());
            System.out.println("Release date: " + release.getPublishedAt());
        }
        
        // ...
    }

}
```
In this example, `GitCheck` is used to check for updates to the repository `Owner/Project` with the current version `v1.0.0`.
If a new version is available, the details of the release are printed to the console.

## Contributing
We welcome contributions to GitCheck!
If you have an idea for a new feature or have found a bug that needs to be fixed, you can [open an issue](https://github.com/EternalCodeTeam/GitCheck/issues/new) or [submit a pull request](https://github.com/EternalCodeTeam/GitCheck/compare) with your changes.
See [CONTRIBUTING.md](.github/CONTRIBUTING.md) for more information.

## License
GitCheck is licensed under the MIT License. See the [LICENSE](LICENSE) file for more information.
