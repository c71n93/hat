// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.spotless)
}
val targetCompatibility by extra(JavaVersion.VERSION_21)
spotless {
    java {
        target("**/*.java")
        targetExclude("**/build/**")
        eclipse().configFile(rootProject.file("config/spotless/eclipse-java-formatter.xml"))
    }
    format("xml") {
        target("**/*.xml")
        targetExclude("**/build/**", "**/.*/**")
        trimTrailingWhitespace()
        indentWithSpaces(4)
        endWithNewline()
    }
}
