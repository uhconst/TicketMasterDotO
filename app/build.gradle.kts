plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinxSerialization)
}

android {
    namespace = "com.uhc.ticketmasterdoto"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.uhc.ticketmasterdoto"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_URL", "\"https://app.ticketmaster.com/\"")
            buildConfigField("String", "API_KEY", "\"5ike7MSNlAAvxYKqXhSyNY324bnkkwld\"")
        }

        debug {
            isDebuggable = true
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_URL", "\"https://app.ticketmaster.com/\"")
            buildConfigField("String", "API_KEY", "\"5ike7MSNlAAvxYKqXhSyNY324bnkkwld\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
    buildFeatures  {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(":feature-about"))
    implementation(project(":feature-events"))
    implementation(project(":lib-build-config"))
    implementation(project(":lib-compose-utils"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.koin.android)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}