import org.gradle.kotlin.dsl.withType
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(feedflowLibs.plugins.about.libraries)
    alias(feedflowLibs.plugins.compose.compiler)
    alias(feedflowLibs.plugins.compose.multiplatform)
    alias(feedflowLibs.plugins.flatpak.gradle.generator)
    alias(feedflowLibs.plugins.kotlin.multiplatform)
    alias(feedflowLibs.plugins.kotlin.serialization)
    alias(feedflowLibs.plugins.ksp)
    alias(feedflowLibs.plugins.sqldelight)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(feedflowLibs.androidx.lifecycle.viewModel)
            api(feedflowLibs.immutable.collections)
            api(feedflowLibs.io.coil.compose)
            api(feedflowLibs.lyricist)
            api(feedflowLibs.touchlab.kermit)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.material3AdaptiveNavigationSuite)
            implementation(compose.materialIconsExtended)
            implementation(compose.runtime)
            implementation(compose.runtime)
            implementation(feedflowLibs.arrow.fx.coroutines)
            implementation(feedflowLibs.com.prof18.rss.parser)
            implementation(feedflowLibs.components.ui.tooling.preview)
            implementation(feedflowLibs.immutable.collections)
            implementation(feedflowLibs.jsoup)
            implementation(feedflowLibs.koin.core)
            implementation(feedflowLibs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.coroutines.core.old)
            implementation(feedflowLibs.kotlinx.date.time)
            implementation(feedflowLibs.kotlinx.serialization.json)
            implementation(feedflowLibs.ktor.client.core)
            implementation(feedflowLibs.ktor.client.okhttp)
            implementation(feedflowLibs.ktor.content.negotiation)
            implementation(feedflowLibs.ktor.logging)
            implementation(feedflowLibs.ktor.resources)
            implementation(feedflowLibs.ktor.serialization)
            implementation(feedflowLibs.multiplatform.settings)
            implementation(feedflowLibs.readability4j)
            implementation(feedflowLibs.saket.swipe)
            implementation(feedflowLibs.skie.annotation)
            implementation(feedflowLibs.sqldelight.coroutine.extensions)
            implementation(feedflowLibs.sqldelight.primitive.adapter)
            implementation(feedflowLibs.sqldelight.runtime)
            implementation(feedflowLibs.stately.concurrency)
            implementation(feedflowLibs.touchlab.kermit)
            implementation(project.dependencies.platform(feedflowLibs.koin.bom))
        }

        jvmMain.dependencies {
            api(feedflowLibs.dropbox.core)
            api(feedflowLibs.io.coil.network)
            api(feedflowLibs.sentry)
            implementation(compose.components.resources)
            implementation(compose.desktop.currentOs)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.preview)
            implementation(feedflowLibs.bundles.about.libraries)
            implementation(feedflowLibs.flexmark.html2md.converter)
            implementation(feedflowLibs.jsoup)
            implementation(libs.kotlinx.coroutines.core.old)
            implementation(feedflowLibs.jsystem.theme.detector)
            implementation(feedflowLibs.koin.core)
            implementation(feedflowLibs.kotlinx.coroutines.core)
            implementation(feedflowLibs.kotlinx.coroutines.swing)
            implementation(feedflowLibs.kotlinx.date.time)
            implementation(feedflowLibs.kotlinx.serialization.json)
            implementation(feedflowLibs.ktor.client.core)
            implementation(feedflowLibs.ktor.client.okhttp)
            implementation(feedflowLibs.material.window.size)
            implementation(feedflowLibs.multiplatform.markdown.renderer.coil)
            implementation(feedflowLibs.multiplatform.markdown.renderer.m3)
            implementation(feedflowLibs.slf4j.nop)
            implementation(feedflowLibs.sqldelight.sqlite.driver)
            implementation(feedflowLibs.voyager.navigator)
            implementation(feedflowLibs.voyager.transition)
            implementation(project.dependencies.platform(feedflowLibs.koin.bom))
            implementation(libs.ksp.aa.embeddable)
            implementation(libs.compose.annotation.internal)
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
    add("kspCommonMainMetadata", feedflowLibs.lyricist.processorXml)
    add("kspJvm", feedflowLibs.lyricist.processorXml)
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
}