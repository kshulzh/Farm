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
