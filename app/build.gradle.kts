plugins {
    alias(libs.plugins.android.application)
    id("checkstyle")
    id("pmd")
}

android {
    namespace = "com.c71n93.hat"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.c71n93.hat"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

checkstyle {
    toolVersion = "11.0.1"
    config = resources.text.fromFile(rootProject.file("config/checkstyle/checkstyle.xml"))
    configProperties["cache.file"] = file("$buildDir/checkstyle/cache").absolutePath
}

pmd {
    toolVersion = "7.0.0"
    ruleSetFiles = files(rootProject.file("config/pmd/pmd.xml"))
    ruleSets = listOf()
}

tasks.register<Checkstyle>("checkstyleMain") {
    description = "Checkstyle for main sources."
    group = "verification"
    source(android.sourceSets["main"].java.srcDirs)
    include("**/*.java")
    classpath = files()
}

tasks.register<Checkstyle>("checkstyleTest") {
    description = "Checkstyle for test sources."
    group = "verification"
    source(android.sourceSets["test"].java.srcDirs)
    include("**/*.java")
    classpath = files()
}

tasks.register<Pmd>("pmdMain") {
    description = "PMD for main sources."
    group = "verification"
    source(android.sourceSets["main"].java.srcDirs)
    include("**/*.java")
    classpath = files()
}

tasks.register<Pmd>("pmdTest") {
    description = "PMD for test sources."
    group = "verification"
    source(android.sourceSets["test"].java.srcDirs)
    include("**/*.java")
    classpath = files()
}

tasks.named("check") {
    dependsOn(
        "checkstyleMain",
        "checkstyleTest",
        "pmdMain",
        "pmdTest"
    )
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.mockito.android)
}
