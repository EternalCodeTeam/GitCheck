plugins {
    `java-library`
    `maven-publish`
}

group = "com.eternalcode"
version = "1.0.0"

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

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}