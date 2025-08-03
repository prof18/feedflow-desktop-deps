import org.gradle.kotlin.dsl.withType
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.about.libraries)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.flatpak.gradle.generator)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.sqldelight)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(libs.androidx.lifecycle.viewModel)
            api(libs.immutable.collections)
            api(libs.io.coil.compose)
            api(libs.lyricist)
            api(libs.touchlab.kermit)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.material3AdaptiveNavigationSuite)
            implementation(compose.materialIconsExtended)
            implementation(compose.runtime)
            implementation(compose.runtime)
            implementation(libs.arrow.fx.coroutines)
            implementation(libs.com.prof18.rss.parser)
            implementation(libs.components.ui.tooling.preview)
            implementation(libs.immutable.collections)
            implementation(libs.jsoup)
            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.date.time)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.ktor.content.negotiation)
            implementation(libs.ktor.logging)
            implementation(libs.ktor.resources)
            implementation(libs.ktor.serialization)
            implementation(libs.multiplatform.settings)
            implementation(libs.readability4j)
            implementation(libs.saket.swipe)
            implementation(libs.skie.annotation)
            implementation(libs.sqldelight.coroutine.extensions)
            implementation(libs.sqldelight.primitive.adapter)
            implementation(libs.sqldelight.runtime)
            implementation(libs.stately.concurrency)
            implementation(libs.touchlab.kermit)
            implementation(project.dependencies.platform(libs.koin.bom))
        }

        jvmMain.dependencies {
            api(libs.dropbox.core)
            api(libs.io.coil.network)
            api(libs.sentry)
            implementation(compose.components.resources)
            implementation(compose.desktop.currentOs)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.preview)
            implementation(libs.bundles.about.libraries)
            implementation(libs.flexmark.html2md.converter)
            implementation(libs.jsoup)
            implementation(libs.jsystem.theme.detector)
            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.kotlinx.date.time)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.material.window.size)
            implementation(libs.multiplatform.markdown.renderer.coil)
            implementation(libs.multiplatform.markdown.renderer.m3)
            implementation(libs.slf4j.nop)
            implementation(libs.sqldelight.sqlite.driver)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.transition)
            implementation(project.dependencies.platform(libs.koin.bom))
        }
    }
}


compose.desktop {
    application {
        mainClass = "com.prof18.feedflow.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.prof18.feedflow"
            packageVersion = "1.0.0"
        }
    }
}
dependencies {
    add("kspCommonMainMetadata", libs.lyricist.processorXml)
}

ksp {
    arg("lyricist.xml.resourcesPath", "$projectDir/src/commonMain/resources/locale")
    arg("lyricist.packageName", "com.prof18.feedflow.i18n")
    arg("lyricist.xml.moduleName", "FeedFlow")
    arg("lyricist.xml.defaultLanguageTag", "en")
    arg("lyricist.xml.generateComposeAccessors", "false")
}



kotlin.sourceSets.commonMain {
    kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
}

sqldelight {
    databases {
        create("FeedFlowDB") {
            packageName.set("com.prof18.feedflow.db")
            schemaOutputDirectory.set(file("src/commonMain/sqldelight/com/prof18/feedflow/schema"))
            verifyMigrations.set(true)
        }
    }
}

tasks.flatpakGradleGenerator {
    outputFile = project.file("flatpak-sources.json")
    downloadDirectory.set("./offline-repository")
    excludeConfigurations.set(listOf("testCompileClasspath", "testRuntimeClasspath"))
}