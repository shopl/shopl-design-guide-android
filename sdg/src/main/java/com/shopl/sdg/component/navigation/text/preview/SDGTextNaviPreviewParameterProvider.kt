package com.shopl.sdg.component.navigation.text.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.navigation.text.SDGTextNaviBackButton
import com.shopl.sdg.component.navigation.text.SDGTextNaviLabel
import com.shopl.sdg.component.navigation.text.SDGTextNaviSubItem
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_resource.R
import kotlinx.collections.immutable.persistentListOf

internal class SDGTextNaviPreviewParameterProvider : PreviewParameterProvider<SDGTextNaviPreviewParameter> {
    override val values: Sequence<SDGTextNaviPreviewParameter> = sequenceOf(
        기본_상태(),
        뒤로가기_숨김(),
        제목_숨김(),
        보조_아이콘_숨김(),
        보조_텍스트_숨김(),
        보조_항목_숨김(),
        모든_보조요소_숨김(),
        긴_제목(),
        긴_보조_텍스트(),
        라벨_다음_아이콘(),
        여러_보조_항목(),
        파란_배경(),
        분홍_배경(),
    )

    private fun 기본_상태(): SDGTextNaviPreviewParameter {
        return SDGTextNaviPreviewParameter(
            label = SDGTextNaviLabel.True(text = "Label", textColor = SDGColor.Neutral900),
            backButton = SDGTextNaviBackButton.True(
                onClick = {},
            ),
            subItems = persistentListOf(
                SDGTextNaviSubItem.Icon(iconResId = R.drawable.ic_common_plus, onClick = {}),
                SDGTextNaviSubItem.Label(text = "Label", onClick = {}),
            ),
            backgroundColor = SDGColor.Neutral100,
        )
    }

    private fun 뒤로가기_숨김(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(backButton = SDGTextNaviBackButton.False)
    }

    private fun 제목_숨김(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(label = SDGTextNaviLabel.False)
    }

    private fun 보조_아이콘_숨김(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(
            subItems = persistentListOf(
                SDGTextNaviSubItem.Label(text = "완료", onClick = {}),
            ),
        )
    }

    private fun 보조_텍스트_숨김(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(
            subItems = persistentListOf(
                SDGTextNaviSubItem.Icon(
                    iconResId = R.drawable.ic_common_plus,
                    onClick = {},
                ),
            ),
        )
    }

    private fun 보조_항목_숨김(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(subItems = null)
    }

    private fun 모든_보조요소_숨김(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(
            backButton = SDGTextNaviBackButton.False,
            subItems = persistentListOf(),
        )
    }

    private fun 긴_제목(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(
            label = SDGTextNaviLabel.True(
                text = "긴 화면 타이틀이 들어가는 경우 말줄임표시 확인 필요",
                textColor = SDGColor.Neutral900,
            ),
        )
    }

    private fun 긴_보조_텍스트(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(
            subItems = persistentListOf(
                SDGTextNaviSubItem.Icon(iconResId = R.drawable.ic_common_plus, onClick = {}),
                SDGTextNaviSubItem.Label(text = "변경사항 저장하기", onClick = {}),
            ),
        )
    }

    private fun 라벨_다음_아이콘(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(
            subItems = persistentListOf(
                SDGTextNaviSubItem.Label(text = "Label", onClick = {}),
                SDGTextNaviSubItem.Icon(iconResId = R.drawable.ic_common_plus, onClick = {}),
            ),
        )
    }

    private fun 여러_보조_항목(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(
            subItems = persistentListOf(
                SDGTextNaviSubItem.Icon(iconResId = R.drawable.ic_common_plus, onClick = {}),
                SDGTextNaviSubItem.Label(text = "취소", onClick = {}),
                SDGTextNaviSubItem.Icon(iconResId = R.drawable.ic_common_plus, onClick = {}),
                SDGTextNaviSubItem.Label(text = "저장", onClick = {}),
            ),
        )
    }

    private fun 파란_배경(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(
            label = SDGTextNaviLabel.True(text = "Label", textColor = SDGColor.Neutral0),
            backButton = SDGTextNaviBackButton.True(
                onClick = {},
            ),
            subItems = persistentListOf(
                SDGTextNaviSubItem.Icon(
                    iconResId = R.drawable.ic_common_plus,
                    iconColor = SDGColor.Neutral0,
                    onClick = {},
                ),
                SDGTextNaviSubItem.Label(
                    text = "Label",
                    textColor = SDGColor.Neutral0,
                    onClick = {},
                ),
            ),
            backgroundColor = SDGColor.Primary300,
        )
    }

    private fun 분홍_배경(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(backgroundColor = SDGColor.Red50)
    }
}
