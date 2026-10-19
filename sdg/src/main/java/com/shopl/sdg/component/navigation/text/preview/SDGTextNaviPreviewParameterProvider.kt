package com.shopl.sdg.component.navigation.text.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.navigation.text.SDGTextNaviBackButton
import com.shopl.sdg.component.navigation.text.SDGTextNaviLabel
import com.shopl.sdg.component.navigation.text.SDGTextNaviRightItem
import com.shopl.sdg.component.navigation.text.SDGTextNaviSubIcon
import com.shopl.sdg.component.navigation.text.SDGTextNaviSubLabel
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_resource.R

internal class SDGTextNaviPreviewParameterProvider : PreviewParameterProvider<SDGTextNaviPreviewParameter> {
    override val values: Sequence<SDGTextNaviPreviewParameter> = sequenceOf(
        기본_상태(),
        뒤로가기_숨김(),
        제목_숨김(),
        보조_아이콘_숨김(),
        보조_텍스트_숨김(),
        우측_기능_숨김(),
        모든_보조요소_숨김(),
        긴_제목(),
        긴_보조_텍스트(),
        파란_배경(),
        분홍_배경(),
    )

    private fun 기본_상태(): SDGTextNaviPreviewParameter {
        return SDGTextNaviPreviewParameter(
            label = SDGTextNaviLabel.True(text = "Label"),
            backButton = SDGTextNaviBackButton.True(
                onClick = {},
                contentDescription = "뒤로가기",
            ),
            rightItem = SDGTextNaviRightItem(
                subLabel = SDGTextNaviSubLabel.True(text = "Label"),
                subIcon = SDGTextNaviSubIcon.True(iconResId = R.drawable.ic_common_plus),
                onClick = {},
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
            rightItem = SDGTextNaviRightItem(
                subLabel = SDGTextNaviSubLabel.True(text = "완료"),
                subIcon = SDGTextNaviSubIcon.False,
                onClick = {},
            ),
        )
    }

    private fun 보조_텍스트_숨김(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(
            rightItem = SDGTextNaviRightItem(
                subLabel = SDGTextNaviSubLabel.False,
                subIcon = SDGTextNaviSubIcon.True(
                    iconResId = R.drawable.ic_common_plus,
                    contentDescription = "추가",
                ),
                onClick = {},
            ),
        )
    }

    private fun 우측_기능_숨김(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(rightItem = null)
    }

    private fun 모든_보조요소_숨김(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(
            backButton = SDGTextNaviBackButton.False,
            rightItem = SDGTextNaviRightItem(
                subLabel = SDGTextNaviSubLabel.False,
                subIcon = SDGTextNaviSubIcon.False,
                onClick = {},
            ),
        )
    }

    private fun 긴_제목(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(
            label = SDGTextNaviLabel.True(text = "긴 화면 타이틀이 들어가는 경우 말줄임표시 확인 필요"),
        )
    }

    private fun 긴_보조_텍스트(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(
            rightItem = SDGTextNaviRightItem(
                subLabel = SDGTextNaviSubLabel.True(text = "변경사항 저장하기"),
                subIcon = SDGTextNaviSubIcon.True(iconResId = R.drawable.ic_common_plus),
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
                contentDescription = "뒤로가기",
            ),
            rightItem = SDGTextNaviRightItem(
                subLabel = SDGTextNaviSubLabel.True(text = "Label", textColor = SDGColor.Neutral0),
                subIcon = SDGTextNaviSubIcon.True(
                    iconResId = R.drawable.ic_common_plus,
                    iconColor = SDGColor.Neutral0,
                ),
                onClick = {},
            ),
            backgroundColor = SDGColor.Primary300,
        )
    }

    private fun 분홍_배경(): SDGTextNaviPreviewParameter {
        return 기본_상태().copy(backgroundColor = SDGColor.Red50)
    }
}
