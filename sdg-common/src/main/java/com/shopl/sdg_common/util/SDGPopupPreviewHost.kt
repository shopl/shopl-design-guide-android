package com.shopl.sdg_common.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

/**
 * Compose Preview에서 팝업 콘텐츠를 인라인으로 렌더링하기 위한 host입니다.
 *
 * Preview에서는 [androidx.compose.ui.window.Dialog]나
 * Material BottomSheet가 별도 window/sheet로 표시되지 않아 콘텐츠를 확인하기 어렵습니다.
 * 이 host는 preview 전용 fallback으로 dim 배경과 정렬 위치만 제공하고,
 * 실제 팝업의 runtime window 동작은 대체하지 않습니다.
 */
@Composable
fun SDGPopupPreviewHost(
    contentAlignment: Alignment,
    modifier: Modifier = Modifier,
    withDim: Boolean = true,
    scrimColor: Color = SDGColor.Neutral900_a40,
    content: @Composable () -> Unit
) {
    Box(
        modifier = if (withDim) {
            modifier
                .fillMaxSize()
                .background(scrimColor)
        } else {
            modifier
        },
        contentAlignment = contentAlignment
    ) {
        content()
    }
}

@Preview(name = "SDGModalBottomSheet", widthDp = 360, heightDp = 640)
@Composable
private fun PreviewSDGPopupPreviewHost() {
    SDGPopupPreviewHost(contentAlignment = Alignment.BottomCenter) {
        val items = listOf(1, 2, 3, 4, 5)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = SDGColor.Neutral0,
                    shape = RoundedCornerShape(
                        topStart = SDGCornerRadius.Radius20,
                        topEnd = SDGCornerRadius.Radius20
                    )
                )
                .navigationBarsPadding()
        ) {
            items.forEach {
                SDGText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    text = "Item $it",
                    typography = SDGTypography.Body1R,
                    textColor = SDGColor.Neutral700
                )
            }
        }
    }
}
