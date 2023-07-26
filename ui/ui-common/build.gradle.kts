
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
    kotlin("multiplatform") version "1.8.20"
    id("org.jetbrains.compose")
    id("com.android.library")
    `maven-publish`
}
group = extra["farm.ui.group"]!!
version = extra["farm.ui.version"]!!

kotlin {
    android("android")
    jvm("desktop") {
        jvmToolchain(17)
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
                implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
                //implementation("${extra["farm.group"]!!}:common:${extra["farm.version"]!!}")
                implementation(project(":common:main"))
                implementation(project(":common"))
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.appcompat:appcompat:1.6.1")
                implementation("androidx.core:core-ktx:1.10.1")
            }
        }
//        val androidTest by getting {
//            dependencies {
//                implementation("junit:junit:4.13.2")
//            }
//        }
        val desktopMain by getting {
            dependencies {
                api(compose.preview)
            }
        }
        val desktopTest by getting
    }
}

android {
    namespace = "me.kshulzh.farm.ui.common"
    compileSdk = 34
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        targetSdk = 34
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

//publishing {
//    publications {
//        register<MavenPublication>("release") {
//            afterEvaluate {
//                from(components["release"])
//            }
//        }
//    }
//}