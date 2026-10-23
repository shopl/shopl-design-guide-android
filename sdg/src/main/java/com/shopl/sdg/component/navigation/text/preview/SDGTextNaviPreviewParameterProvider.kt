package com.shopl.sdg.component.navigation.text.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.navigation.text.SDGTextNaviActionItem
import com.shopl.sdg.component.navigation.text.SDGTextNaviBackButton
import com.shopl.sdg.component.navigation.text.SDGTextNaviLabel
import com.shopl.sdg.component.navigation.text.ShowActionIcon
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_resource.R

internal class SDGTextNaviPreviewParameterProvider : PreviewParameterProvider<SDGTextNaviPreviewParameter> {
    override val values: Sequence<SDGTextNaviPreviewParameter> = sequenceOf(
        기본_상태(),
        뒤로가기_숨김(),
        제목_숨김(),
        액션_아이콘_숨김(),
        긴_제목(),
        긴_액션_라벨(),
        파란_배경(),
        분홍_배경(),
        투명_배경(),
    )

    private fun 기본_상태(): SDGTextNaviPreviewParameter {
        return SDGTextNaviPreviewParameter(
            label = SDGTextNaviLabel.True(text = "Label", textColor = SDGColor.Neutral900),
            backButton = SDGTextNaviBackButton.True(
                onClick = {},
                iconColor = SDGColor.Neutral700,
            ),
            showActionIcon = ShowActionIcon.True(
                iconResId = R.drawable.ic_common_plus,
                iconColor = null,
            ),
            actionLabel = SDGTextNaviActionItem.Label(text = "Label", textColor = SDGColor.Neutral700, onClick = {}),
            backgroundColor = SDGColor.Neutral100,
        )
    }

    private fun 뒤로가기_숨김(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(backButton = SDGTextNaviBackButton.False)
    }

    private fun 제목_숨김(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(label = SDGTextNaviLabel.False)
    }

    private fun 액션_아이콘_숨김(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(showActionIcon = ShowActionIcon.False)
    }

    private fun 긴_제목(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(
            label = SDGTextNaviLabel.True(
                text = "긴 화면 타이틀이 들어가는 경우 말줄임표시 확인 필요",
                textColor = SDGColor.Neutral900,
            ),
        )
    }

    private fun 긴_액션_라벨(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(
            actionLabel = SDGTextNaviActionItem.Label(
                text = "변경사항 저장하기",
                textColor = SDGColor.Neutral700,
                onClick = {},
            ),
        )
    }

    private fun 파란_배경(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(
            label = SDGTextNaviLabel.True(text = "Label", textColor = SDGColor.Neutral0),
            backButton = SDGTextNaviBackButton.True(
                onClick = {},
                iconColor = SDGColor.Neutral0,
            ),
            showActionIcon = ShowActionIcon.True(
                iconResId = R.drawable.ic_common_plus,
                iconColor = SDGColor.Neutral0,
            ),
            actionLabel = SDGTextNaviActionItem.Label(
                text = "Label",
                textColor = SDGColor.Neutral0,
                onClick = {},
            ),
            backgroundColor = SDGColor.Primary300,
        )
    }

    private fun 분홍_배경(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(backgroundColor = SDGColor.Red300_a10)
    }

    private fun 투명_배경(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(backgroundColor = SDGColor.Transparent)
    }
}
