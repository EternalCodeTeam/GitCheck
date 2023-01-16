import java.net.URI

plugins {
    `java-library`
    `maven-publish`
}

group = "com.eternalcode"
version = System.getenv("E_VERSION")

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple
    implementation("com.googlecode.json-simple:json-simple:1.1.1")

    // OkHTTP
    implementation("com.squareup.okhttp3:okhttp:4.10.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

publishing {
    publications {

    }
    repositories {
        maven {
            name = "eternalcode-repository"
            url = "https://repo.eternalcode.pl/releases"
            credentials {
                username = System.getenv("E_REPO_USERNAME")
                password = System.getenv("E_REPO_PASS")
            }
        }
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}