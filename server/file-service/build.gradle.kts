import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    `maven-publish`
}

group = extra["farm.group"]!!
version = extra["farm.version"]!!

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-mustache")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.apache.tika:tika-parsers:1.28.3")
    implementation(project(":common"))
    implementation(project(":common:file-service"))
    runtimeOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
    }
}
