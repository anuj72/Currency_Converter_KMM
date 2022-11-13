plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("7.2.1").apply(false)
    id("com.android.library").version("7.2.1").apply(false)
    kotlin("android").version("1.7.10").apply(false)
    kotlin("multiplatform").version("1.7.10").apply(false)
    id("com.rickclephas.kmp.nativecoroutines").version ("0.12.6").apply(false)
    id("com.squareup.sqldelight").version ("1.5.4").apply(false)

}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
