// Top-level build file where you can add configuration options common to all sub-projects/modules.
@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn", "-Xjsr305=strict")
            jvmTarget = "17"
        }
    }
}