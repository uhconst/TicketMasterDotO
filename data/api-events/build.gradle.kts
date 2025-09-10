plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.uhc.api.events"
    compileSdk = 36

    defaultConfig {
        minSdk = 29

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}

dependencies {
    implementation(project(":lib-build-config"))
    implementation(project(":lib-network-utils"))

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.gson)
    implementation(libs.koin.android)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.koin.test)
}