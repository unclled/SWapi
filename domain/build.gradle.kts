plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.androidx.paging.common)

    implementation(libs.javax.inject)
}