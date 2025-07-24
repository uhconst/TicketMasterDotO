plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures  {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.koin.android)

    implementation(libs.room.runtime) //todo shouldn't need in APP
    ksp(libs.room.compiler)
    api(project(":feature-events")) //todo shouldn't need in APP

    api(project(":repo-favourites"))
    api(project(":domain-events")) //todo
    api(project(":domain-favourites")) //todo
    implementation(project(":lib-build-config"))

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}