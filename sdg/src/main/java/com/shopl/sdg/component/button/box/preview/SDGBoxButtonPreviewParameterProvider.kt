package com.shopl.sdg.component.button.box.preview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.button.box.SDGBoxButtonIconSize
import com.shopl.sdg.component.button.box.SDGBoxButtonShowIcon
import com.shopl.sdg.component.button.box.SDGBoxButtonSize
import com.shopl.sdg.component.button.box.SDGBoxButtonType
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing16
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
import com.shopl.sdg_resource.R

internal class SDGBoxButtonPreviewParameterProvider :
    PreviewParameterProvider<SDGBoxButtonPreviewParameter> {

    override val values: Sequence<SDGBoxButtonPreviewParameter> = sequenceOf(
        활성_Medium_Solid(),
        활성_Small_Solid(),
        활성_Small_Line(),
        활성_SSmall_Solid(),
        비활성_SSmall_Line(),
        작은_버튼_M_아이콘(),
        큰_버튼_XS_아이콘(),
        투명_배경_비활성_Line(),
        선택_상태(),
        비활성_Medium_Solid(),
        흰색_비활성_Medium_Solid(),
        긴_활성_XSmall_Line(),
        최대너비_짧은_라벨(),
        최대너비_한글_오른쪽_아이콘(),
        최대너비_영문_넓은_글자(),
        최대너비_영문_좁은_글자(),
        최대너비_아랍어_양쪽_아이콘(),
        최대너비_일본어_왼쪽_아이콘(),
        최대너비_아이콘_없음(),
        최대너비_전체너비_양쪽_아이콘(),
    )

    private fun 활성_Medium_Solid() = SDGBoxButtonPreviewParameter(
        label = "Medium Solid",
        labelColor = SDGColor.Neutral0,
        backgroundColor = SDGColor.Primary400,
        size = SDGBoxButtonSize.Medium,
        type = SDGBoxButtonType.Solid,
        isFillMaxWidth = true,
        marginValues = PaddingValues(horizontal = Spacing16, vertical = Spacing8),
        leftIc = SDGBoxButtonShowIcon.True(
            iconResId = R.drawable.ic_common_prev_s,
            iconColor = SDGColor.Neutral0,
            iconSize = SDGBoxButtonIconSize.M,
        ),
    )

    private fun 활성_Small_Solid() = SDGBoxButtonPreviewParameter(
        label = "Small Solid",
        labelColor = SDGColor.Neutral0,
        backgroundColor = SDGColor.Neutral350,
        size = SDGBoxButtonSize.Small,
        type = SDGBoxButtonType.Solid,
        rightIc = SDGBoxButtonShowIcon.True(
            iconResId = R.drawable.ic_common_next_s,
            iconColor = SDGColor.Neutral500,
            iconSize = SDGBoxButtonIconSize.S,
        ),
    )

    private fun 활성_Small_Line() = SDGBoxButtonPreviewParameter(
        label = "Small Line",
        labelColor = SDGColor.Primary400,
        backgroundColor = SDGColor.Neutral0,
        size = SDGBoxButtonSize.Small,
        type = SDGBoxButtonType.Line(SDGColor.Primary400),
        rightIc = SDGBoxButtonShowIcon.True(
            iconResId = R.drawable.ic_common_next_s,
            iconColor = SDGColor.Neutral500,
            iconSize = SDGBoxButtonIconSize.XS,
        ),
        marginValues = PaddingValues(horizontal = Spacing16, vertical = Spacing4),
    )

    private fun 활성_SSmall_Solid() = SDGBoxButtonPreviewParameter(
        label = "Label",
        labelColor = SDGColor.Neutral600,
        backgroundColor = SDGColor.Neutral200,
        size = SDGBoxButtonSize.SSmall,
        type = SDGBoxButtonType.Solid,
        leftIc = SDGBoxButtonShowIcon.True(
            iconResId = R.drawable.ic_common_prev_s,
            iconColor = SDGColor.Neutral600,
            iconSize = SDGBoxButtonIconSize.S,
        ),
        rightIc = SDGBoxButtonShowIcon.True(
            iconResId = R.drawable.ic_common_next_s,
            iconColor = SDGColor.Neutral600,
            iconSize = SDGBoxButtonIconSize.S,
        ),
    )

    private fun 비활성_SSmall_Line() = 활성_SSmall_Solid().copy(
        type = SDGBoxButtonType.Line(SDGColor.Neutral250),
        backgroundColor = SDGColor.Neutral0,
        enable = false,
    )

    private fun 작은_버튼_M_아이콘() = 활성_SSmall_Solid().copy(
        size = SDGBoxButtonSize.XSmall,
        leftIc = SDGBoxButtonShowIcon.True(
            iconResId = R.drawable.ic_common_prev_s,
            iconColor = null,
            iconSize = SDGBoxButtonIconSize.M,
        ),
        rightIc = SDGBoxButtonShowIcon.False,
    )

    private fun 큰_버튼_XS_아이콘() = 활성_Medium_Solid().copy(
        leftIc = SDGBoxButtonShowIcon.True(
            iconResId = R.drawable.ic_common_prev_s,
            iconColor = SDGColor.Neutral0,
            iconSize = SDGBoxButtonIconSize.XS,
        ),
    )

    private fun 투명_배경_비활성_Line() = 비활성_SSmall_Line().copy(
        backgroundColor = SDGColor.Transparent,
    )

    private fun 선택_상태() = 활성_SSmall_Solid().copy(
        backgroundColor = SDGColor.Neutral300,
        labelColor = SDGColor.Neutral900,
    )

    private fun 비활성_Medium_Solid() = SDGBoxButtonPreviewParameter(
        label = "비활성화 된 BoxButton",
        labelColor = SDGColor.Neutral700,
        backgroundColor = SDGColor.Secondary400,
        size = SDGBoxButtonSize.Medium,
        type = SDGBoxButtonType.Solid,
        enable = false,
        rightIc = SDGBoxButtonShowIcon.True(
            iconResId = R.drawable.ic_common_next_s,
            iconColor = SDGColor.Neutral0,
            iconSize = SDGBoxButtonIconSize.M,
        ),
    )

    private fun 흰색_비활성_Medium_Solid() = SDGBoxButtonPreviewParameter(
        label = "비활성화 된 흰 색상 라벨 BoxButton",
        labelColor = SDGColor.Neutral0,
        backgroundColor = SDGColor.Secondary400,
        size = SDGBoxButtonSize.Medium,
        type = SDGBoxButtonType.Solid,
        enable = false,
        rightIc = SDGBoxButtonShowIcon.True(
            iconResId = R.drawable.ic_common_next_s,
            iconColor = SDGColor.Neutral0,
            iconSize = SDGBoxButtonIconSize.M,
        ),
    )

    private fun 긴_활성_XSmall_Line() = SDGBoxButtonPreviewParameter(
        label = "위치한 영역 내에서 최대 길이까지 버튼명을 표시했는데 길어진다고 줄바꿈으로 전체를 표시할 수 없습니다.",
        labelColor = SDGColor.Neutral700,
        backgroundColor = SDGColor.Neutral0,
        size = SDGBoxButtonSize.XSmall,
        type = SDGBoxButtonType.Line(SDGColor.Neutral300),
        rightIc = SDGBoxButtonShowIcon.True(
            iconResId = R.drawable.ic_common_edit,
            iconColor = null,
            iconSize = SDGBoxButtonIconSize.XS,
        ),
        isFillMaxWidth = false,
    )

    private fun 최대너비_짧은_라벨() = SDGBoxButtonPreviewParameter(
        label = "A",
        labelColor = SDGColor.Neutral700,
        backgroundColor = SDGColor.Neutral100,
        size = SDGBoxButtonSize.XSmall,
        type = SDGBoxButtonType.Solid,
        maxWidth = 120.dp,
        rightIc = SDGBoxButtonShowIcon.True(
            iconResId = R.drawable.ic_common_help2,
            iconColor = null,
            iconSize = SDGBoxButtonIconSize.S,
        ),
    )

    private fun 최대너비_한글_오른쪽_아이콘() = SDGBoxButtonPreviewParameter(
        label = "가나다라마바사아자차카타파하",
        labelColor = SDGColor.Neutral700,
        backgroundColor = SDGColor.Neutral100,
        size = SDGBoxButtonSize.XSmall,
        type = SDGBoxButtonType.Solid,
        maxWidth = 120.dp,
        rightIc = SDGBoxButtonShowIcon.True(
            iconResId = R.drawable.ic_common_help2,
            iconColor = null,
            iconSize = SDGBoxButtonIconSize.S,
        ),
    )

    private fun 최대너비_영문_넓은_글자() = SDGBoxButtonPreviewParameter(
        label = "WWWWWWWWWWWWWW",
        labelColor = SDGColor.Neutral700,
        backgroundColor = SDGColor.Neutral100,
        size = SDGBoxButtonSize.XSmall,
        type = SDGBoxButtonType.Solid,
        maxWidth = 120.dp,
        rightIc = SDGBoxButtonShowIcon.True(
            iconResId = R.drawable.ic_common_help2,
            iconColor = null,
            iconSize = SDGBoxButtonIconSize.S,
        ),
    )

    private fun 최대너비_영문_좁은_글자() = SDGBoxButtonPreviewParameter(
        label = "iiiiiiiiiiiiiiiiiiiiiiiiiiii",
        labelColor = SDGColor.Neutral700,
        backgroundColor = SDGColor.Neutral100,
        size = SDGBoxButtonSize.XSmall,
        type = SDGBoxButtonType.Solid,
        maxWidth = 120.dp,
        rightIc = SDGBoxButtonShowIcon.True(
            iconResId = R.drawable.ic_common_help2,
            iconColor = null,
            iconSize = SDGBoxButtonIconSize.S,
        ),
    )

    private fun 최대너비_아랍어_양쪽_아이콘() = SDGBoxButtonPreviewParameter(
        label = "المستوى الممتاز للمبيعات",
        labelColor = SDGColor.Neutral700,
        backgroundColor = SDGColor.Neutral100,
        size = SDGBoxButtonSize.XSmall,
        type = SDGBoxButtonType.Solid,
        maxWidth = 120.dp,
        leftIc = SDGBoxButtonShowIcon.True(
            iconResId = R.drawable.ic_common_prev_s,
            iconColor = null,
            iconSize = SDGBoxButtonIconSize.S,
        ),
        rightIc = SDGBoxButtonShowIcon.True(
            iconResId = R.drawable.ic_common_help2,
            iconColor = null,
            iconSize = SDGBoxButtonIconSize.S,
        ),
    )

    private fun 최대너비_일본어_왼쪽_아이콘() = SDGBoxButtonPreviewParameter(
        label = "販売目標達成の最高等級",
        labelColor = SDGColor.Neutral700,
        backgroundColor = SDGColor.Neutral100,
        size = SDGBoxButtonSize.XSmall,
        type = SDGBoxButtonType.Solid,
        maxWidth = 120.dp,
        leftIc = SDGBoxButtonShowIcon.True(
            iconResId = R.drawable.ic_common_prev_s,
            iconColor = null,
            iconSize = SDGBoxButtonIconSize.S,
        ),
    )

    private fun 최대너비_아이콘_없음() = SDGBoxButtonPreviewParameter(
        label = "International sales grade",
        labelColor = SDGColor.Neutral700,
        backgroundColor = SDGColor.Neutral100,
        size = SDGBoxButtonSize.XSmall,
        type = SDGBoxButtonType.Solid,
        maxWidth = 120.dp,
    )

    private fun 최대너비_전체너비_양쪽_아이콘() = SDGBoxButtonPreviewParameter(
        label = "International sales grade",
        labelColor = SDGColor.Neutral700,
        backgroundColor = SDGColor.Neutral100,
        size = SDGBoxButtonSize.XSmall,
        type = SDGBoxButtonType.Solid,
        maxWidth = 120.dp,
        leftIc = SDGBoxButtonShowIcon.True(
            iconResId = R.drawable.ic_common_prev_s,
            iconColor = null,
            iconSize = SDGBoxButtonIconSize.S,
        ),
        rightIc = SDGBoxButtonShowIcon.True(
            iconResId = R.drawable.ic_common_help2,
            iconColor = null,
            iconSize = SDGBoxButtonIconSize.S,
        ),
        isFillMaxWidth = true,
    )
}
