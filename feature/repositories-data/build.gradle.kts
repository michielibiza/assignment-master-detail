@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinKsp)
}

android {
    namespace = "nl.michiel.feature.repositories.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.retrofit)
    implementation(libs.retrofitmoshi)
    implementation(libs.loggingInterceptor)
    implementation(libs.koin)
    implementation(libs.kotlin.coroutines)
    implementation(libs.room)
    implementation(libs.roomKtx)
    implementation(libs.timber)

    annotationProcessor(libs.roomCompiler)
    ksp(libs.roomCompiler)

    implementation(project(":feature:repositories-domain"))

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.testCoroutines)
}
