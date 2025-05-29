package com.shopl.sdg.template.popup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.shopl.sdg_common.foundation.SDGColor

@Composable
fun SDGProgressPopup(
    withDim: Boolean = false
) {
    Dialog(
        onDismissRequest = { },
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        if (!withDim) {
            (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0F)
        } else {
            (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0.4F)
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(32.dp)
        ) {
            CircularProgressIndicator(
                color = SDGColor.Primary300,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}