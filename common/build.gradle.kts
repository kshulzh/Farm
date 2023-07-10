plugins {
    kotlin("multiplatform") version "1.8.20"
    application
    jacoco
    `maven-publish`
}

group = extra["farm.group"]!!
version = extra["farm.version"]!!

kotlin {
    jvm {
        jvmToolchain(17)
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
                implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.20")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.2")
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.2")

                implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
                implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
                implementation("io.ktor:ktor-server-netty:2.0.2")
                implementation("io.ktor:ktor-server-html-builder-jvm:2.0.2")
                implementation("io.ktor:ktor-server-cors:2.0.2")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
                implementation("khttp:khttp:0.1.0")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("org.mockito.kotlin:mockito-kotlin:5.0.0")
            }
        }
        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.346")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.346")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.9.3-pre.346")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.2")
            }
        }
    }
}

application {
    mainClass.set("me.kshulzh.farm.ServerKt")
}

tasks.named<Copy>("jvmProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}

jacoco {
    toolVersion = "0.8.8"
}