@file:Suppress("UnstableApiUsage", "DSL_SCOPE_VIOLATION")

import java.io.FileInputStream
import java.util.Properties

plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.dagger.hilt.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.kotlin.parcelize.get().pluginId)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.jkangangi.en_dictionary"
    compileSdk = 33

    defaultConfig.apply {
        applicationId = namespace
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        vectorDrawables {
            useSupportLibrary = true
        }

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    buildTypes {
        debug {
            val properties = Properties()
            val fileInputStream = FileInputStream(project.rootProject.file("local.properties"))
            properties.load(fileInputStream)
            val apiKey = properties.getProperty("API_KEY")
            buildConfigField(type = "String", name = "API_KEY", value = "\"$apiKey\"")
        }
        release {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.libs.versions.compose.compiler.get()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    //core
    implementation(libs.core.ktx)
    implementation(libs.core.appcompat)
    implementation(libs.core.splashscreen)

    // Material Design
    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons)

    // Compose
    implementation(platform(libs.compose))
    implementation(libs.compose.activity)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.fonts)

    debugImplementation(libs.compose.ui.tooling)
    androidTestImplementation(platform(libs.compose))

    // lifecycle
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.viewmodel.compose)

    // Appyx Navigation
    implementation(libs.appyx)

    // preferences datastore
    implementation(libs.datastore)

    // hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.compose)
    kapt(libs.hilt.compiler)

    //Jsoup
    implementation(libs.jsoup)

    //Ktor-Client
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.contentnegotiation)

    // Kotlinx
    implementation(libs.kotlin.immutable)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.kotlin.serialization.ktor)


    // Room
    ksp(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    testImplementation(libs.junit)
    testImplementation(libs.junit.engine)
    androidTestImplementation(libs.junit.android)
}