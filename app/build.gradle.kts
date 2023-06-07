@file:Suppress("UnstableApiUsage", "DSL_SCOPE_VIOLATION")

plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.dagger.hilt.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
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

    buildTypes.apply {
        maybeCreate("release").apply {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }

    compileOptions.apply {
        // Flag to enable support for the new language APIs
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions.apply {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures.apply {
        compose = true
        buildConfig = true
    }

    composeOptions.apply {
        kotlinCompilerExtensionVersion = rootProject.libs.versions.compose.compiler.get()
    }

    packagingOptions.apply {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.core.appcompat)

    // Material Design
    implementation(libs.core.material)
    implementation(libs.compose.material)
    implementation(libs.compose.material.icons)

    // Compose
    implementation(platform(libs.compose))
    implementation(libs.compose.activity)
    implementation(libs.compose.navigation)
    ksp(libs.compose.navigation.ksp)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.custom.view)
    debugImplementation(libs.compose.custom.view.pooling)
    debugImplementation(libs.compose.ui.tooling)
    androidTestImplementation(platform(libs.compose))
    androidTestImplementation(libs.compose.ui.test.junit)

    // lifecycle
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.viewmodel.compose)

    // Appyx Navigation - for navigation
    implementation(libs.appyx)

    // preferences datastore
    implementation(libs.datastore)

    // hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.compose)
    kapt(libs.hilt.compiler)

    //Ktor-Client
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.logging)

    // Kotlinx
    implementation(libs.kotlin.immutable)
    implementation(libs.kotlin.serialization)

    // Napier
    implementation(libs.napier)

    // Room
    ksp(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    testImplementation(libs.junit)
    testImplementation(libs.junit.engine)
    androidTestImplementation(libs.expresso)
    androidTestImplementation(libs.junit.android)
}