package com.shopl.sdg.component.button.bottom.preview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.button.bottom.SDGBottomButtonSpec
import com.shopl.sdg.component.button.bottom.SDGBottomButtonType

internal class SDGBottomButtonPreviewParameterProvider :
    PreviewParameterProvider<SDGBottomButtonPreviewParameter> {

    override val values: Sequence<SDGBottomButtonPreviewParameter> = sequenceOf(
        버튼명_활성_Full_Positive(),
        버튼명_활성_Full_Negative(),
        버튼명_활성_Full_Normal(),
        버튼명_활성_Full_NormalDark(),
        버튼명_비활성_Full_Positive(),
        버튼명_활성_Adaptive_Normal(),
        버튼명_비활성_Adaptive_Negative(),
    )

    private fun 버튼명_활성_Full_Positive() = SDGBottomButtonPreviewParameter(
        title = "버튼명 활성 Full Positive",
        type = SDGBottomButtonType.POSITIVE,
        spec = SDGBottomButtonSpec.FULL,
        enabled = true,
    )

    private fun 버튼명_활성_Full_Negative() = SDGBottomButtonPreviewParameter(
        title = "버튼명 활성 Full Negative",
        type = SDGBottomButtonType.NEGATIVE,
        spec = SDGBottomButtonSpec.FULL,
        enabled = true,
    )

    private fun 버튼명_활성_Full_Normal() = SDGBottomButtonPreviewParameter(
        title = "버튼명 활성 Full Normal",
        type = SDGBottomButtonType.NORMAL,
        spec = SDGBottomButtonSpec.FULL,
        enabled = true,
    )

    private fun 버튼명_활성_Full_NormalDark() = SDGBottomButtonPreviewParameter(
        title = "버튼명 활성 Full Normal Dark",
        type = SDGBottomButtonType.NORMAL_DARK,
        spec = SDGBottomButtonSpec.FULL,
        enabled = true,
    )

    private fun 버튼명_비활성_Full_Positive() = SDGBottomButtonPreviewParameter(
        title = "버튼명 비활성 Full Positive",
        type = SDGBottomButtonType.POSITIVE,
        spec = SDGBottomButtonSpec.FULL,
        enabled = false,
    )

    private fun 버튼명_활성_Adaptive_Normal() = SDGBottomButtonPreviewParameter(
        title = "버튼명 활성 Adaptive Normal",
        type = SDGBottomButtonType.NORMAL,
        spec = SDGBottomButtonSpec.ADAPTIVE,
        enabled = true,
    )

    private fun 버튼명_비활성_Adaptive_Negative() = SDGBottomButtonPreviewParameter(
        title = "버튼명 비활성 Adaptive Negative",
        type = SDGBottomButtonType.NEGATIVE,
        spec = SDGBottomButtonSpec.ADAPTIVE,
        enabled = false,
    )
}

internal data class SDGBottomButtonPreviewParameter(
    val title: String,
    val type: SDGBottomButtonType,
    val spec: SDGBottomButtonSpec,
    val enabled: Boolean = true,
    val marginValues: PaddingValues = PaddingValues(),
)
