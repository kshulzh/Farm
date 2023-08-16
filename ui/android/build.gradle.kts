buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20")
    }
}
plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
    `maven-publish`
}

group = extra["farm.ui.group"]!!
version = extra["farm.ui.version"]!!

dependencies {
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.compose.material3:material3:1.1.1")
    implementation(project(":common"))
    implementation(project(":ui:ui-common"))
}

android {
    namespace = "me.kshulzh.farm.ui.android"
    compileSdk = 34
    defaultConfig {
        multiDexEnabled = true
        applicationId = "$group.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = version.toString()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}