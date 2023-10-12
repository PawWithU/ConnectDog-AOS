// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1"
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
    }
}

apply(plugin = "com.vanniktech.dependency.graph.generator")
