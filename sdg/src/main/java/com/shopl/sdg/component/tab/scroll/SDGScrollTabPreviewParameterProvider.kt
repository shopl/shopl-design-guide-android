package com.shopl.sdg.component.tab.scroll

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGColor
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

internal class SDGScrollTabPreviewParameterProvider :
    PreviewParameterProvider<SDGScrollTabPreviewParameter> {

    override val values: Sequence<SDGScrollTabPreviewParameter> = sequenceOf(
        기본_Line(),
        기본_Text(),
        긴_텍스트_Line(),
        스크롤_많은_아이템_Line(),
        스크롤_적은_아이템_Text(),
        선택_없음_Text(),
        여백_지정_Line(),
        배경색_변경_Text()
    )

    private fun 기본_Line(): SDGScrollTabPreviewParameter {
        return SDGScrollTabPreviewParameter(
            type = SDGScrollTabType.Line,
            titles = persistentListOf("Label", "Label", "Label"),
            selectedIndex = 0
        )
    }

    private fun 기본_Text(): SDGScrollTabPreviewParameter {
        return SDGScrollTabPreviewParameter(
            type = SDGScrollTabType.Text,
            titles = persistentListOf("Label", "Label", "Label"),
            selectedIndex = 0
        )
    }

    private fun 긴_텍스트_Line(): SDGScrollTabPreviewParameter {
        return SDGScrollTabPreviewParameter(
            type = SDGScrollTabType.Line,
            titles = persistentListOf("짧은 라벨", "매우 긴 라벨입니다. 말줄임표가 보여야 합니다.", "중간 라벨"),
            selectedIndex = 1,
            maxItemWidth = 100.dp
        )
    }

    private fun 스크롤_많은_아이템_Line(): SDGScrollTabPreviewParameter {
        return SDGScrollTabPreviewParameter(
            type = SDGScrollTabType.Line,
            titles = persistentListOf(
                "전체", "진행중", "완료", "보류", "취소",
                "예정", "검토중", "승인됨", "거절됨", "삭제됨"
            ),
            selectedIndex = 0,
            contentPadding = PaddingValues(horizontal = 16.dp)
        )
    }

    private fun 스크롤_적은_아이템_Text(): SDGScrollTabPreviewParameter {
        return SDGScrollTabPreviewParameter(
            type = SDGScrollTabType.Line,
            titles = persistentListOf("전체", "진행중", "완료"),
            selectedIndex = 0,
            isFillMaxWidth = false,
            contentPadding = PaddingValues(horizontal = 16.dp)
        )
    }

    private fun 선택_없음_Text(): SDGScrollTabPreviewParameter {
        return SDGScrollTabPreviewParameter(
            type = SDGScrollTabType.Text,
            titles = persistentListOf("옵션 A", "옵션 B", "옵션 C"),
            selectedIndex = null
        )
    }

    private fun 여백_지정_Line(): SDGScrollTabPreviewParameter {
        return SDGScrollTabPreviewParameter(
            type = SDGScrollTabType.Line,
            titles = persistentListOf("Label 1", "Label 2"),
            selectedIndex = 0,
            marginValues = PaddingValues(20.dp),
            isFillMaxWidth = false
        )
    }

    private fun 배경색_변경_Text(): SDGScrollTabPreviewParameter {
        return SDGScrollTabPreviewParameter(
            type = SDGScrollTabType.Text,
            titles = persistentListOf("탭 1", "탭 2"),
            selectedIndex = 0,
            backgroundColor = SDGColor.Primary300
        )
    }
}

internal data class SDGScrollTabPreviewParameter(
    val type: SDGScrollTabType = SDGScrollTabType.Line,
    val titles: PersistentList<String>,
    val selectedIndex: Int?,
    val isFillMaxWidth: Boolean = true,
    val contentPadding: PaddingValues = PaddingValues(horizontal = 0.dp),
    val marginValues: PaddingValues = PaddingValues(0.dp),
    val maxItemWidth: Dp? = null,
    val backgroundColor: Color = SDGColor.Neutral0
)