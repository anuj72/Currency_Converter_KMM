plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.7.10"
    id("com.rickclephas.kmp.nativecoroutines")
    id("com.squareup.sqldelight")

}

kotlin {
    android()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val ktorVersion = "2.0.3"
        val napierVersion="2.6.1"
        val sqlDelightVersion="1.5.4"
        val kotlinXDateTimeVersion="0.4.0"

        val commonMain by getting {
            dependencies{
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0-RC")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
                implementation("io.github.aakira:napier:$napierVersion")
                implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$kotlinXDateTimeVersion")
                implementation("com.russhwolf:multiplatform-settings-no-arg:1.0.0-RC")
                implementation(kotlin("stdlib-common"))
                implementation("co.touchlab:kermit:1.2.2") //Add latest version






            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))


            }
        }
        val androidMain by getting{
            dependencies{
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
                implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies{
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
                implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")

            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }


}





android {
    namespace = "com.app.currencyconverter"
    compileSdk = 32
    defaultConfig {
        minSdk = 23
        targetSdk = 32
    }
}
dependencies {
    testImplementation("junit:junit:4.12")
}



sqldelight {
    database("MyAppDb") {
        packageName = "com.app.currency.cache"
        sourceFolders = listOf("sqldelight")

    }
}


