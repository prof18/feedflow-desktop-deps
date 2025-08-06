plugins {
    alias(feedflowLibs.plugins.kotlin.android) apply false
    alias(feedflowLibs.plugins.kotlin.multiplatform) apply false
    alias(feedflowLibs.plugins.kotlin.serialization) apply false
    alias(feedflowLibs.plugins.sqldelight) apply false
    alias(feedflowLibs.plugins.compose.multiplatform) apply false
    alias(feedflowLibs.plugins.ksp) apply false
    alias(feedflowLibs.plugins.about.libraries) apply false
    alias(feedflowLibs.plugins.crashlytics) apply false
    alias(feedflowLibs.plugins.detekt) apply false
    alias(feedflowLibs.plugins.org.jetbrains.kotlin.jvm) apply false
    alias(feedflowLibs.plugins.compose.compiler) apply false
    alias(feedflowLibs.plugins.skie) apply false
    alias(feedflowLibs.plugins.compose.hotreload) apply false
    alias(libs.plugins.flatpak.gradle.generator)
}

tasks.register("clean", Delete::class) {
    delete(layout.buildDirectory.get())
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            if (project.findProperty("composeCompilerReports") == "true") {
                freeCompilerArgs = listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${layout.buildDirectory.get().asFile.absolutePath}/compose_compiler",
                )
            }
            if (project.findProperty("composeCompilerMetrics") == "true") {
                freeCompilerArgs = listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${layout.buildDirectory.get().asFile.absolutePath}/compose_compiler",
                )
            }
        }
    }
}

tasks.flatpakGradleGenerator {
    outputFile = project.file("flatpak-sources-root.json")
    downloadDirectory.set("./offline-repository")
}
