plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk=31

    defaultConfig {
        applicationId="com.wssg.kaiyan"
        minSdk=21
        targetSdk=31
        versionCode=1
        versionName="1.0"

        testInstrumentationRunner="androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled=false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
dependPaging()
dependNetWork()
dependGlide()
dependencies {
    implementation(project(":lib_base"))
    implementation("xyz.doikki.android.dkplayer:dkplayer-java:3.3.6")

    implementation("xyz.doikki.android.dkplayer:player-exo:3.3.6")

    implementation("xyz.doikki.android.dkplayer:player-ijk:3.3.6")
    implementation("xyz.doikki.android.dkplayer:dkplayer-ui:3.3.6")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

}