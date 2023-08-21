import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin") //Hilt
    kotlin("kapt") //Hilt (Keep this the last item in the list)
}

val localPropertiesFile = gradleLocalProperties(rootDir)

android {
    namespace = "com.example.marvelcharacters"
    buildToolsVersion = "30.0.3"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storePassword = getValueFromLocal("CERTIFICATE_PASSWORD")
            keyPassword = getValueFromLocal("CERTIFICATE_PASSWORD")
            keyAlias = getValueFromLocal("CERTIFICATE_ALIAS")
            storeFile = file("MarvelCharactersKeyStore.jks")
            enableV1Signing = true
            enableV2Signing = true
        }
    }

    buildTypes {
        getByName("debug") {
            for (item in getDevBuildConfigFields())
                buildConfigField("String", item.key, item.value)
        }

        create("staging") {
            initWith(buildTypes.getByName("debug"))
            matchingFallbacks.add("debug")

            for (item in getDevBuildConfigFields())
                buildConfigField("String", item.key, item.value)
        }

        getByName("release") {
            ndk.debugSymbolLevel = "SYMBOL_TABLE"
            signingConfig = signingConfigs.getByName("release")

            for (item in getReleaseBuildConfigFields())
                buildConfigField("String", item.key, item.value)
        }

        all {
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
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

fun getDevBuildConfigFields() = mapOf(
    "MARVEL_PUBLIC_KEY" to getValueFromLocal("MARVEL_PUBLIC_KEY"),
    "MARVEL_PRIVATE_KEY" to getValueFromLocal("MARVEL_PRIVATE_KEY"),
    "MARVEL_BASE_URL" to getValueFromLocal("MARVEL_BASE_URL")
)

fun getReleaseBuildConfigFields() = mapOf(
    "MARVEL_PUBLIC_KEY" to getValueFromLocal("PRODUCTION_MARVEL_PUBLIC_KEY"),
    "MARVEL_PRIVATE_KEY" to getValueFromLocal("PRODUCTION_MARVEL_PRIVATE_KEY"),
    "MARVEL_BASE_URL" to getValueFromLocal("PRODUCTION_MARVEL_BASE_URL")
)

fun getValueFromLocal(key: String): String {
    /**
     * Find the key in system environment variables first.
     * If exist then return it, otherwise find it in local.properties file.
     * This step to prevent CI/CD systems to make builds fails.
     **/
    return System.getenv(key) ?: localPropertiesFile.getProperty(key)
}

dependencies {
    fun listImplementation(dependencies: Any?) {
        if (dependencies is List<*>)
            dependencies.forEach { implementation(it!!) }
    }

    fun listApi(dependencies: Any?) {
        if (dependencies is List<*>)
            dependencies.forEach { api(it!!) }
    }

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.6.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.6.0")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.22")
    implementation("androidx.preference:preference-ktx:1.2.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1") {
        //exclude Kotlin stdLib to avoid conflicts with version used by the app.
        exclude("org.jetbrains.kotlin", "kotlin-stdlib-jdk8")
    }
    implementation("io.coil-kt:coil:2.4.0")


    //Hilt >>>> DON"T FORGET to use Hilt plugins too
    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-android-compiler:2.47")


    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    testImplementation("org.mockito:mockito-core:3.12.4")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("com.google.truth:truth:1.1.4")


    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

// (Hilt) Allow references to generated code
kapt {
    correctErrorTypes = true
}