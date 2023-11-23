// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.google.services)
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ktlint)
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
}

true // Needed to make the Suppress annotation work for the plugins block

buildscript {
    repositories {
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        mavenCentral()
    }
    dependencies {
        // gradlew generateProjectDependencyGraph
        classpath(libs.dependency.graph)
        classpath(libs.google.service)
    }
}

apply(plugin = "com.vanniktech.dependency.graph.generator")
