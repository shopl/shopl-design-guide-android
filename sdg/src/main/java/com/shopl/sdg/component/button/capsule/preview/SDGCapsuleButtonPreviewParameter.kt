package com.shopl.sdg.component.button.capsule.preview

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.button.capsule.SDGCapsuleButtonSize
import com.shopl.sdg.component.button.capsule.SDGCapsuleButtonType
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_resource.R

internal class SDGCapsuleButtonPreviewParameterProvider : PreviewParameterProvider<SDGCapsuleButtonPreviewParam> {
    override val values: Sequence<SDGCapsuleButtonPreviewParam> = sequenceOf(
        작성하기_활성_Solid(),
        작성하기_비활성_Solid(),
        작성하기_활성_Line(),
        작성하기_비활성_Line(),
        작성하기_왼쪽아이콘_Solid(),
        작성하기_오른쪽아이콘_Line(),
        작성하기_양쪽아이콘_Solid(),
        작성하기_IconDownSized(),
        작성하기_FullWidth_Solid(),
        작성하기_FullWidth_Line(),
        작성하기_Large_Solid(),
        작성하기_Medium_Solid(),
        작성하기_Small_Solid(),
        작성하기_XSmall_Solid()
    )

    private fun 작성하기_활성_Solid(): SDGCapsuleButtonPreviewParam {
        return SDGCapsuleButtonPreviewParam(
            label = "작성하기 활성 Solid 버튼",
            labelColor = SDGColor.Neutral0,
            backgroundColor = SDGColor.Neutral700
        )
    }

    private fun 작성하기_비활성_Solid(): SDGCapsuleButtonPreviewParam {
        return SDGCapsuleButtonPreviewParam(
            enable = false,
            label = "작성하기 비활성 Solid 버튼",
            labelColor = SDGColor.Neutral0,
            backgroundColor = SDGColor.Neutral700
        )
    }

    private fun 작성하기_활성_Line(): SDGCapsuleButtonPreviewParam {
        return SDGCapsuleButtonPreviewParam(
            type = SDGCapsuleButtonType.Line(SDGColor.Neutral700),
            label = "작성하기 활성 Line 버튼",
            labelColor = SDGColor.Neutral700,
            backgroundColor = SDGColor.Neutral0
        )
    }

    private fun 작성하기_비활성_Line(): SDGCapsuleButtonPreviewParam {
        return SDGCapsuleButtonPreviewParam(
            enable = false,
            type = SDGCapsuleButtonType.Line(SDGColor.Neutral300),
            label = "작성하기 비활성 Line 버튼",
            labelColor = SDGColor.Neutral700,
            backgroundColor = SDGColor.Neutral0
        )
    }

    private fun 작성하기_왼쪽아이콘_Solid(): SDGCapsuleButtonPreviewParam {
        return SDGCapsuleButtonPreviewParam(
            label = "편집",
            leftIcon = R.drawable.ic_common_edit,
            leftIconTint = SDGColor.Neutral0,
            backgroundColor = SDGColor.Primary300
        )
    }

    private fun 작성하기_오른쪽아이콘_Line(): SDGCapsuleButtonPreviewParam {
        return SDGCapsuleButtonPreviewParam(
            type = SDGCapsuleButtonType.Line(SDGColor.Primary300),
            label = "더보기",
            labelColor = SDGColor.Primary300,
            rightIcon = R.drawable.ic_alignup, // 임의의 아이콘 사용 (프로젝트 내 존재하는 아이콘으로 가정)
            rightIconTint = SDGColor.Primary300,
            backgroundColor = SDGColor.Neutral0
        )
    }

    private fun 작성하기_양쪽아이콘_Solid(): SDGCapsuleButtonPreviewParam {
        return SDGCapsuleButtonPreviewParam(
            label = "양쪽 아이콘",
            leftIcon = R.drawable.ic_common_checkbold,
            leftIconTint = SDGColor.Neutral0,
            rightIcon = R.drawable.ic_common_caution,
            rightIconTint = SDGColor.Neutral0,
            backgroundColor = SDGColor.GreenG
        )
    }

    private fun 작성하기_IconDownSized(): SDGCapsuleButtonPreviewParam {
        return SDGCapsuleButtonPreviewParam(
            label = "아이콘 작게",
            leftIcon = R.drawable.ic_common_edit,
            leftIconTint = SDGColor.Neutral0,
            iconDownSized = true,
            backgroundColor = SDGColor.Secondary300
        )
    }

    private fun 작성하기_FullWidth_Solid(): SDGCapsuleButtonPreviewParam {
        return SDGCapsuleButtonPreviewParam(
            isFillMaxWidth = true,
            label = "확인",
            backgroundColor = SDGColor.Neutral900
        )
    }

    private fun 작성하기_FullWidth_Line(): SDGCapsuleButtonPreviewParam {
        return SDGCapsuleButtonPreviewParam(
            isFillMaxWidth = true,
            type = SDGCapsuleButtonType.Line(SDGColor.Neutral500),
            label = "취소",
            labelColor = SDGColor.Neutral500,
            backgroundColor = SDGColor.Neutral0
        )
    }

    private fun 작성하기_Large_Solid(): SDGCapsuleButtonPreviewParam {
        return SDGCapsuleButtonPreviewParam(
            size = SDGCapsuleButtonSize.Large,
            label = "Large 버튼",
            backgroundColor = SDGColor.Primary400
        )
    }

    private fun 작성하기_Medium_Solid(): SDGCapsuleButtonPreviewParam {
        return SDGCapsuleButtonPreviewParam(
            size = SDGCapsuleButtonSize.Medium,
            label = "Medium 버튼",
            backgroundColor = SDGColor.Primary400
        )
    }

    private fun 작성하기_Small_Solid(): SDGCapsuleButtonPreviewParam {
        return SDGCapsuleButtonPreviewParam(
            size = SDGCapsuleButtonSize.Small,
            label = "Small 버튼",
            backgroundColor = SDGColor.Primary300
        )
    }

    private fun 작성하기_XSmall_Solid(): SDGCapsuleButtonPreviewParam {
        return SDGCapsuleButtonPreviewParam(
            size = SDGCapsuleButtonSize.XSmall,
            label = "XSmall 버튼",
            backgroundColor = SDGColor.Neutral500
        )
    }
}

internal data class SDGCapsuleButtonPreviewParam(
    val size: SDGCapsuleButtonSize = SDGCapsuleButtonSize.Medium,
    val type: SDGCapsuleButtonType = SDGCapsuleButtonType.Solid,
    val label: String = "버튼",
    val labelColor: Color = SDGColor.Neutral0,
    val backgroundColor: Color = SDGColor.Primary300,
    val enable: Boolean = true,
    val leftIcon: Int? = null,
    val leftIconTint: Color? = null,
    val rightIcon: Int? = null,
    val rightIconTint: Color? = null,
    val isFillMaxWidth: Boolean = false,
    val iconDownSized: Boolean = false
)