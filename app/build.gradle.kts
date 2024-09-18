import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
}

val keystorePropertiesFile = rootProject.file("local.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    namespace = "com.raphaeloliveira.taskbeat"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.raphaeloliveira.taskbeat"
        minSdk = 21
        targetSdk = 34
        versionCode = 2
        versionName = "1.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("release")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            ndk.debugSymbolLevel = "FULL"
        }
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        buildConfig = true
        viewBinding = true
    }
    ndkVersion = "27.0.11902837 rc2"
}

dependencies {

    val room_version = "2.6.1"

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.core:core-splashscreen:1.0.1")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.7")

    implementation ("io.insert-koin:koin-android:3.1.3")

    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.2")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.8.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    kapt ("androidx.lifecycle:lifecycle-compiler:2.8.2")
    implementation ("androidx.lifecycle:lifecycle-service:2.8.2")
    implementation ("androidx.lifecycle:lifecycle-process:2.8.2")
    implementation ("androidx.lifecycle:lifecycle-reactivestreams-ktx:2.8.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.8.2")

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-guava:$room_version")

    implementation ("com.google.android.play:integrity:1.3.0")

    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation("androidx.room:room-testing:$room_version")
    
}