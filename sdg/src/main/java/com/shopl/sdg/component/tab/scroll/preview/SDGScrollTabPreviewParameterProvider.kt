package com.shopl.sdg.component.tab.scroll.preview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.tab.scroll.SDGScrollTabSize
import com.shopl.sdg.component.tab.scroll.SDGScrollTabStyle
import com.shopl.sdg_common.foundation.SDGColor
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

internal class SDGScrollTabPreviewParameterProvider :
    PreviewParameterProvider<SDGScrollTabPreviewParameter> {

    override val values: Sequence<SDGScrollTabPreviewParameter> = sequence {
        SDGScrollTabSize.entries.forEach { size ->
            SDGScrollTabStyle.entries.forEach { style ->
                (0..4).forEach { selectedTab ->
                    yield(
                        SDGScrollTabPreviewParameter(
                            style = style,
                            size = size,
                            selectedTab = selectedTab,
                        )
                    )
                }
                yield(
                    SDGScrollTabPreviewParameter(
                        style = style,
                        size = size,
                        titles = persistentListOf("짧은 라벨", "매우 긴 라벨입니다. 말줄임표가 보여야 합니다.", "중간 라벨"),
                        selectedTab = 1,
                        maxItemWidth = 100.dp,
                    )
                )
            }
            yield(
                SDGScrollTabPreviewParameter(
                    size = size,
                    titles = persistentListOf("전체", "진행중", "완료", "보류", "취소", "예정", "검토중", "승인됨", "거절됨", "삭제됨"),
                    selectedTab = 9,
                    contentPadding = PaddingValues(horizontal = 16.dp),
                )
            )
            yield(
                SDGScrollTabPreviewParameter(
                    size = size,
                    titles = persistentListOf("Label 1", "Label 2"),
                    contentPadding = PaddingValues(20.dp),
                )
            )
            yield(
                SDGScrollTabPreviewParameter(
                    size = size,
                    titles = persistentListOf("Label 1", "Label 2"),
                    isFillMaxWidth = false,
                    marginValues = PaddingValues(20.dp),
                )
            )
        }
        yield(
            SDGScrollTabPreviewParameter(
                style = SDGScrollTabStyle.OnlyText,
                backgroundColor = SDGColor.Primary300,
            )
        )
    }
}

internal data class SDGScrollTabPreviewParameter(
    val style: SDGScrollTabStyle = SDGScrollTabStyle.WithUnderline,
    val size: SDGScrollTabSize = SDGScrollTabSize.Large,
    val titles: PersistentList<String> = persistentListOf("Label", "Label", "Label", "Label", "Label"),
    val selectedTab: Int = 0,
    val isFillMaxWidth: Boolean = true,
    val contentPadding: PaddingValues = PaddingValues(0.dp),
    val marginValues: PaddingValues = PaddingValues(0.dp),
    val maxItemWidth: Dp? = null,
    val backgroundColor: Color = SDGColor.Neutral0,
)
