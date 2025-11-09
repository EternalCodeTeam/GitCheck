plugins {
    `java-library`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.eternalcode"
version = "1.0.0"
val artifactId = "gitcheck"

java {
    withJavadocJar()
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_1_9
    targetCompatibility = JavaVersion.VERSION_1_9
}

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple
    api("com.googlecode.json-simple:json-simple:1.1.1") {
        exclude(group = "junit")
    }

    api("org.jetbrains:annotations:26.0.2-1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:6.0.1")
    testImplementation("nl.jqno.equalsverifier:equalsverifier:4.2.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:6.0.1")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "$group"
            artifactId = artifactId
            version = "${project.version}"

            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "eternalcode-repository"
            url = uri("https://repo.eternalcode.pl/releases")

            credentials {
                username = System.getenv("ETERNALCODE_REPO_USERNAME")
                password = System.getenv("ETERNALCODE_REPO_PASSWORD")
            }
        }
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
