plugins {
    `java-library`
    `maven-publish`
}

group = "com.eternalcode"
version = "1.0.0"
val artifactId = "gitcheck"

java {
    withJavadocJar()
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    api("com.googlecode.json-simple:json-simple:1.1.1") {
        exclude(group = "junit")
    }
    api("org.jetbrains:annotations:26.0.2")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testImplementation("nl.jqno.equalsverifier:equalsverifier:3.14.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

publishing {
    repositories {
        mavenLocal()

        maven {
            url = uri("https://repo.eternalcode.pl/releases")

            if (version.toString().endsWith("-SNAPSHOT")) {
                url = uri("https://repo.eternalcode.pl/snapshots")
            }

            credentials {
                username = System.getenv("ETERNAL_CODE_MAVEN_USERNAME")
                password = System.getenv("ETERNAL_CODE_MAVEN_PASSWORD")
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
