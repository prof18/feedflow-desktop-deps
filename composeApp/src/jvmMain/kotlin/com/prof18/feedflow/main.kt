package com.prof18.feedflow

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "feedflow-desktop-deps",
    ) {
        App()
    }
}