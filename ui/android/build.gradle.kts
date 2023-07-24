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
    implementation("androidx.compose.material3:material3:1.0.0-alpha08")
    //implementation("$group:common:$version")
    implementation(project(":ui:ui-common"))
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")
}

android {
    compileSdkVersion(33)
    defaultConfig {
        multiDexEnabled = true
        applicationId = "$group.android"
        minSdkVersion(24)
        targetSdkVersion(33)
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