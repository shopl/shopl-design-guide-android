package com.shopl.sdg.ui

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import com.shopl.sdg_common.foundation.SDGColor

fun ComponentActivity.setEdgeToEdgeConfig() {
    val systemBarColor = SDGColor.Neutral900.toArgb()
    enableEdgeToEdge(
        statusBarStyle = SystemBarStyle.dark(scrim = systemBarColor),
    )
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        // Force the 3-button navigation bar to be transparent
        // See: https://developer.android.com/develop/ui/views/layout/edge-to-edge#create-transparent
        window.isNavigationBarContrastEnforced = false
    }
}