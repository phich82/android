plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.java.sample"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.java.sample"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.6.0")
    implementation("androidx.navigation:navigation-ui:2.6.0")
    implementation("androidx.preference:preference:1.2.0")

    implementation("androidx.activity:activity:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // HHttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.android.volley:volley:1.2.1")

    // Json
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.1")

    // Load image
    implementation("com.squareup.picasso:picasso:2.8")

    // Socket (client)
    implementation("io.socket:socket.io-client:2.1.1") {
        // excluding org.json which is provided by Android
        exclude(group = "org.json", module = "json")
    }

    // Google Map
    implementation("com.google.android.gms:play-services-maps:19.0.0")

    // Google AdMod & Firebase
    implementation("com.google.android.gms:play-services-ads:23.6.0")
    implementation("com.google.firebase:firebase-ads:23.6.0")
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")

    // Google Youtube
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.1")
    // Old Youtube lib
    implementation(files("libs/YouTubeAndroidPlayerApi.jar"))

}