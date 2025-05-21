package com.shopl.sdg.component.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.shopl.sdg_common.foundation.SDGColor

/**
 * Progress
 */
@Composable
fun SDGSystemProgress(
    modifier: Modifier = Modifier,
    size: Dp = 32.dp,
    strokeWidth: Dp? = null
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        CircularProgressIndicator(
            color = SDGColor.Primary300,
            modifier = Modifier
                .align(Alignment.Center)
                .size(size),
            strokeWidth = strokeWidth ?: ProgressIndicatorDefaults.CircularStrokeWidth,
        )
    }
}

@Composable
fun SDGSystemProgressPopup() {
    Dialog(
        onDismissRequest = { },
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        (LocalView.current.parent as? DialogWindowProvider)?.window?.setDimAmount(0f)

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(SDGColor.Neutral0_a60)
        ) {
            SDGSystemProgress(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview(name = "interactive mode로 확인")
@Composable
private fun PrevSDGProgress(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier
            .background(color = SDGColor.Neutral0)
            .padding(10.dp)
    ) {
        SDGSystemProgress()
    }
}