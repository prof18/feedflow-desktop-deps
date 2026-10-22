import org.gradle.kotlin.dsl.withType
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(feedflowLibs.plugins.about.libraries)
    alias(feedflowLibs.plugins.compose.compiler)
    alias(feedflowLibs.plugins.compose.hot.reload)
    alias(feedflowLibs.plugins.compose.multiplatform)
    alias(libs.plugins.flatpak.gradle.generator)
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
            implementation(feedflowLibs.compose.multiplatform.ui.tooling.preview)
            implementation(feedflowLibs.compose.multiplatform.foundation)
            implementation(feedflowLibs.compose.multiplatform.material.icons.extended)
            implementation(feedflowLibs.compose.multiplatform.runtime)
            implementation(feedflowLibs.arrow.fx.coroutines)
            implementation(feedflowLibs.com.prof18.rss.parser)
            implementation(feedflowLibs.immutable.collections)
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
            implementation(feedflowLibs.saket.swipe)
            implementation(feedflowLibs.skie.annotation)
            implementation(feedflowLibs.sqldelight.coroutine.extensions)
            implementation(feedflowLibs.sqldelight.primitive.adapter)
            implementation(feedflowLibs.sqldelight.runtime)
            implementation(feedflowLibs.stately.concurrency)
            implementation(feedflowLibs.touchlab.kermit)
            implementation(feedflowLibs.csv)
            implementation(feedflowLibs.flatlaf)
//            implementation(feedflowLibs.ht
            implementation(feedflowLibs.koin.composeVM)
            implementation(project.dependencies.platform(feedflowLibs.koin.bom))
        }

        jvmMain.dependencies {
            api(feedflowLibs.dropbox.core)
            api(feedflowLibs.io.coil.network)
            api(feedflowLibs.sentry)
            implementation(feedflowLibs.compose.multiplatform.components.resources)
            implementation(feedflowLibs.compose.multiplatform.material.icons.extended)
            implementation(compose.desktop.currentOs)
            implementation(feedflowLibs.bundles.about.libraries)
            implementation(libs.kotlinx.coroutines.core.old)
            implementation(feedflowLibs.jsystem.theme.detector)
            implementation(feedflowLibs.koin.core)
            implementation(feedflowLibs.kotlinx.coroutines.core)
            implementation(feedflowLibs.kotlinx.coroutines.swing)
            implementation(feedflowLibs.kotlinx.date.time)
            implementation(feedflowLibs.kotlinx.serialization.json)
            implementation(feedflowLibs.ktor.client.core)
            implementation(feedflowLibs.ktor.client.okhttp)
            implementation(feedflowLibs.slf4j.nop)
            implementation(feedflowLibs.sqldelight.sqlite.driver)
            implementation(project.dependencies.platform(feedflowLibs.koin.bom))
            implementation(libs.ksp.aa.embeddable)
            implementation(libs.compose.annotation.internal)
            implementation(libs.okio)
            implementation(feedflowLibs.google.api.client)
            implementation(feedflowLibs.google.oauth.client.jetty)
            implementation(feedflowLibs.google.api.services.drive)
            implementation(feedflowLibs.jetbrains.navigation3.ui)
            implementation(feedflowLibs.jetbrains.lifecycle.viewmodel.navigation3)
            implementation(feedflowLibs.koin.composeVM)
            implementation(feedflowLibs.ktor.client.auth)
            implementation(feedflowLibs.compose.unstyled.scrollbars)
            implementation(feedflowLibs.compose.unstyled.slider)
            implementation(feedflowLibs.immutable.collections)
            implementation(feedflowLibs.klead)
            implementation(feedflowLibs.jewel.int.ui.standalone)
            implementation(feedflowLibs.jewel.platform.icons)
            implementation(feedflowLibs.jewel.markdown.core)
            implementation(feedflowLibs.jewel.markdown.standalone.styling)
            implementation(feedflowLibs.jewel.markdown.images)
            implementation(feedflowLibs.jewel.markdown.tables)
            implementation(feedflowLibs.jewel.markdown.strikethrough)
            implementation(feedflowLibs.jewel.markdown.autolink)
            implementation(feedflowLibs.about.libraries.core)
        }

        jvmTest.dependencies {
            implementation(kotlin("test"))
            implementation(feedflowLibs.kotlinx.coroutines.test)
            implementation(compose.desktop.uiTestJUnit4)
            implementation(feedflowLibs.spectre.core)
            implementation(feedflowLibs.spectre.recording)
            implementation(feedflowLibs.spectre.testing)
            runtimeOnly(feedflowLibs.spectre.recording.linux)
            implementation(feedflowLibs.junit.jupiter.api)
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
    components {
        withModule("de.jangassen:jfa") {
            allVariants {
                withDependencies {
                    // jfa requests an obsolete jpms classifier; Jewel supplies the regular JNA artifact.
                    removeAll { it.group == "net.java.dev.jna" && it.name == "jna" }
                }
            }
        }
    }
    modules {
        module("org.jetbrains.intellij.deps.kotlinx:kotlinx-coroutines-core-jvm") {
            replacedBy("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm", "Use the upstream coroutine runtime")
        }
    }
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