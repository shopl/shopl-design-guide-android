package com.shopl.sdg.component.check_option.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.check_option.model.SDGCheckOptionSelectedBackgroundColor
import com.shopl.sdg.component.check_option.model.SDGCheckOptionSize
import com.shopl.sdg.component.check_option.model.SDGCheckOptionState
import com.shopl.sdg.component.check_option.model.SDGCheckOptionStyle

internal class SDGCheckOptionPreviewParameterProvider :
    PreviewParameterProvider<SDGCheckOptionPreviewParams> {

    override val values: Sequence<SDGCheckOptionPreviewParams> = sequenceOf(
        기본_상태_MEDIUM_SOLID(),
        선택된_상태_NORMAL_MEDIUM_SOLID(),
        선택된_상태_NEUTRAL_MEDIUM_SOLID(),
        비활성_상태_MEDIUM_SOLID(),
        기본_상태_LARGE_SOLID(),
        선택된_상태_NORMAL_LARGE_SOLID(),
        선택된_상태_NEUTRAL_LARGE_SOLID(),
        비활성_상태_LARGE_SOLID(),
        기본_상태_MEDIUM_LINE(),
        선택된_상태_NORMAL_MEDIUM_LINE(),
        선택된_상태_NEUTRAL_MEDIUM_LINE(),
        비활성_상태_MEDIUM_LINE(),
        기본_상태_LARGE_LINE(),
        선택된_상태_NORMAL_LARGE_LINE(),
        선택된_상태_NEUTRAL_LARGE_LINE(),
        비활성_상태_LARGE_LINE(),
    )

    private fun 기본_상태_MEDIUM_SOLID() = SDGCheckOptionPreviewParams(
        state = SDGCheckOptionState.DEFAULT,
        selectedBackgroundColor = SDGCheckOptionSelectedBackgroundColor.NORMAL,
        size = SDGCheckOptionSize.MEDIUM,
        style = SDGCheckOptionStyle.SOLID,
    )

    private fun 선택된_상태_NORMAL_MEDIUM_SOLID() = SDGCheckOptionPreviewParams(
        state = SDGCheckOptionState.SELECTED,
        selectedBackgroundColor = SDGCheckOptionSelectedBackgroundColor.NORMAL,
        size = SDGCheckOptionSize.MEDIUM,
        style = SDGCheckOptionStyle.SOLID,
    )

    private fun 선택된_상태_NEUTRAL_MEDIUM_SOLID() = SDGCheckOptionPreviewParams(
        state = SDGCheckOptionState.SELECTED,
        selectedBackgroundColor = SDGCheckOptionSelectedBackgroundColor.NEUTRAL,
        size = SDGCheckOptionSize.MEDIUM,
        style = SDGCheckOptionStyle.SOLID,
    )

    private fun 비활성_상태_MEDIUM_SOLID() = SDGCheckOptionPreviewParams(
        state = SDGCheckOptionState.DISABLED,
        selectedBackgroundColor = SDGCheckOptionSelectedBackgroundColor.NORMAL,
        size = SDGCheckOptionSize.MEDIUM,
        style = SDGCheckOptionStyle.SOLID,
    )

    private fun 기본_상태_LARGE_SOLID() = SDGCheckOptionPreviewParams(
        state = SDGCheckOptionState.DEFAULT,
        selectedBackgroundColor = SDGCheckOptionSelectedBackgroundColor.NORMAL,
        size = SDGCheckOptionSize.LARGE,
        style = SDGCheckOptionStyle.SOLID,
    )

    private fun 선택된_상태_NORMAL_LARGE_SOLID() = SDGCheckOptionPreviewParams(
        state = SDGCheckOptionState.SELECTED,
        selectedBackgroundColor = SDGCheckOptionSelectedBackgroundColor.NORMAL,
        size = SDGCheckOptionSize.LARGE,
        style = SDGCheckOptionStyle.SOLID,
    )

    private fun 선택된_상태_NEUTRAL_LARGE_SOLID() = SDGCheckOptionPreviewParams(
        state = SDGCheckOptionState.SELECTED,
        selectedBackgroundColor = SDGCheckOptionSelectedBackgroundColor.NEUTRAL,
        size = SDGCheckOptionSize.LARGE,
        style = SDGCheckOptionStyle.SOLID,
    )

    private fun 비활성_상태_LARGE_SOLID() = SDGCheckOptionPreviewParams(
        state = SDGCheckOptionState.DISABLED,
        selectedBackgroundColor = SDGCheckOptionSelectedBackgroundColor.NORMAL,
        size = SDGCheckOptionSize.LARGE,
        style = SDGCheckOptionStyle.SOLID,
    )

    private fun 기본_상태_MEDIUM_LINE() = SDGCheckOptionPreviewParams(
        state = SDGCheckOptionState.DEFAULT,
        selectedBackgroundColor = SDGCheckOptionSelectedBackgroundColor.NORMAL,
        size = SDGCheckOptionSize.MEDIUM,
        style = SDGCheckOptionStyle.LINE,
    )

    private fun 선택된_상태_NORMAL_MEDIUM_LINE() = SDGCheckOptionPreviewParams(
        state = SDGCheckOptionState.SELECTED,
        selectedBackgroundColor = SDGCheckOptionSelectedBackgroundColor.NORMAL,
        size = SDGCheckOptionSize.MEDIUM,
        style = SDGCheckOptionStyle.LINE,
    )

    private fun 선택된_상태_NEUTRAL_MEDIUM_LINE() = SDGCheckOptionPreviewParams(
        state = SDGCheckOptionState.SELECTED,
        selectedBackgroundColor = SDGCheckOptionSelectedBackgroundColor.NEUTRAL,
        size = SDGCheckOptionSize.MEDIUM,
        style = SDGCheckOptionStyle.LINE,
    )

    private fun 비활성_상태_MEDIUM_LINE() = SDGCheckOptionPreviewParams(
        state = SDGCheckOptionState.DISABLED,
        selectedBackgroundColor = SDGCheckOptionSelectedBackgroundColor.NORMAL,
        size = SDGCheckOptionSize.MEDIUM,
        style = SDGCheckOptionStyle.LINE,
    )

    private fun 기본_상태_LARGE_LINE() = SDGCheckOptionPreviewParams(
        state = SDGCheckOptionState.DEFAULT,
        selectedBackgroundColor = SDGCheckOptionSelectedBackgroundColor.NORMAL,
        size = SDGCheckOptionSize.LARGE,
        style = SDGCheckOptionStyle.LINE,
    )

    private fun 선택된_상태_NORMAL_LARGE_LINE() = SDGCheckOptionPreviewParams(
        state = SDGCheckOptionState.SELECTED,
        selectedBackgroundColor = SDGCheckOptionSelectedBackgroundColor.NORMAL,
        size = SDGCheckOptionSize.LARGE,
        style = SDGCheckOptionStyle.LINE,
    )

    private fun 선택된_상태_NEUTRAL_LARGE_LINE() = SDGCheckOptionPreviewParams(
        state = SDGCheckOptionState.SELECTED,
        selectedBackgroundColor = SDGCheckOptionSelectedBackgroundColor.NEUTRAL,
        size = SDGCheckOptionSize.LARGE,
        style = SDGCheckOptionStyle.LINE,
    )

    private fun 비활성_상태_LARGE_LINE() = SDGCheckOptionPreviewParams(
        state = SDGCheckOptionState.DISABLED,
        selectedBackgroundColor = SDGCheckOptionSelectedBackgroundColor.NORMAL,
        size = SDGCheckOptionSize.LARGE,
        style = SDGCheckOptionStyle.LINE,
    )
}
