package com.shopl.sdg.template.popup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.util.SDGPopupPreviewContainer

@Composable
fun SDGProgressPopup(
    withDim: Boolean = false
) {
    if (LocalInspectionMode.current) {
        SDGProgressPopupInspectionPreview(withDim = withDim)
        return
    }

    Dialog(
        onDismissRequest = { },
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        if (!withDim) {
            (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0F)
        } else {
            (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0.4F)
        }
        SDGProgressPopupContent()
    }
}

@Composable
private fun SDGProgressPopupContent() {
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

/**
 * Compose Preview에서 Dialog 대신 Progress Popup 콘텐츠를 인라인으로 렌더링합니다.
 */
@Composable
private fun SDGProgressPopupInspectionPreview(withDim: Boolean) {
    SDGPopupPreviewContainer(
        contentAlignment = Alignment.Center,
        withDim = withDim
    ) {
        SDGProgressPopupContent()
    }
}

@Preview(name = "SDGProgressPopup", widthDp = 360, heightDp = 640)
@Composable
private fun PreviewSDGProgressPopup() {
    SDGProgressPopup(withDim = true)
}
