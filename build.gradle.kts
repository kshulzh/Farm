group = extra["farm.group"]!!
version = extra["farm.version"]!!
allprojects {
    repositories {
        jcenter()
        mavenLocal()
        google()
        mavenCentral()

        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
        maven("https://maven.google.com")
    }
}
plugins {
    kotlin("multiplatform") version "1.8.20" apply false
    kotlin("android") version "1.8.20" apply false
    kotlin("plugin.spring") version "1.8.20" apply false
    kotlin("plugin.jpa") version "1.8.20" apply false
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("org.jetbrains.compose") apply false
    id("org.springframework.boot") version "3.0.1" apply false
    id("io.spring.dependency-management") version "1.1.0" apply false
    `maven-publish`
}
