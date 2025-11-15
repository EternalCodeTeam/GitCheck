plugins {
    `java-library`
    `maven-publish`
    id("com.gradleup.shadow") version "9.2.2"
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

    api("org.jetbrains:annotations:24.0.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testImplementation("nl.jqno.equalsverifier:equalsverifier:3.19.4")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
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
