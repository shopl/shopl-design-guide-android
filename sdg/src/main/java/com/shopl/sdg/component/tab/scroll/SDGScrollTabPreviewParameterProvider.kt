package com.shopl.sdg.component.tab.scroll

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

internal class SDGScrollTabPreviewParameterProvider :
    PreviewParameterProvider<SDGScrollTabPreviewParameter> {

    override val values: Sequence<SDGScrollTabPreviewParameter> = sequenceOf(
        SDGScrollTabPreviewParameter(
            titles = persistentListOf("Label", "긴 Label은 이렇게 처리됩니다.", "Label", "Label", "Label", "Label", "Label", "Label", "Label"),
            selectedIndex = 0,
            maxItemWidth = 50.dp
        ),
        SDGScrollTabPreviewParameter(
            titles = persistentListOf("전체", "진행중", "완료", "보류", "취소"),
            selectedIndex = 2,
            maxItemWidth = null
        ),
    )
}

internal data class SDGScrollTabPreviewParameter(
    val titles: PersistentList<String>,
    val selectedIndex: Int,
    val contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    val maxItemWidth: Dp?,
)
