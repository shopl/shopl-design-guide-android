package com.shopl.sdg.component.check_option.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.check_option.SDGCheckOptionColor
import com.shopl.sdg.component.check_option.SDGCheckOptionSize
import com.shopl.sdg.component.check_option.SDGCheckOptionStatus
import com.shopl.sdg.component.check_option.SDGCheckOptionType

internal class SDGCheckOptionPreviewParameterProvider :
    PreviewParameterProvider<SDGCheckOptionPreviewParams> {

    override val values: Sequence<SDGCheckOptionPreviewParams> = sequenceOf(
        기본_상태_MEDIUM_SOLID_BASIC(),
        선택된_상태_MEDIUM_SOLID_BASIC(),
        비활성_상태_MEDIUM_SOLID_BASIC(),
        기본_상태_MEDIUM_SOLID_SPECIAL(),
        선택된_상태_MEDIUM_SOLID_SPECIAL(),
        비활성_상태_MEDIUM_SOLID_SPECIAL(),
        기본_상태_LARGE_SOLID_BASIC(),
        선택된_상태_LARGE_SOLID_BASIC(),
        비활성_상태_LARGE_SOLID_BASIC(),
        기본_상태_LARGE_SOLID_SPECIAL(),
        선택된_상태_LARGE_SOLID_SPECIAL(),
        비활성_상태_LARGE_SOLID_SPECIAL(),
        기본_상태_MEDIUM_LINE_BASIC(),
        선택된_상태_MEDIUM_LINE_BASIC(),
        비활성_상태_MEDIUM_LINE_BASIC(),
        기본_상태_MEDIUM_LINE_SPECIAL(),
        선택된_상태_MEDIUM_LINE_SPECIAL(),
        비활성_상태_MEDIUM_LINE_SPECIAL(),
        기본_상태_LARGE_LINE_BASIC(),
        선택된_상태_LARGE_LINE_BASIC(),
        비활성_상태_LARGE_LINE_BASIC(),
        기본_상태_LARGE_LINE_SPECIAL(),
        선택된_상태_LARGE_LINE_SPECIAL(),
        비활성_상태_LARGE_LINE_SPECIAL(),
    )

    private fun 기본_상태_MEDIUM_SOLID_BASIC() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.SOLID,
        status = SDGCheckOptionStatus.DEFAULT,
        selectedColor = SDGCheckOptionColor.BASIC,
        size = SDGCheckOptionSize.MEDIUM
    )

    private fun 선택된_상태_MEDIUM_SOLID_BASIC() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.SOLID,
        status = SDGCheckOptionStatus.SELECTED,
        selectedColor = SDGCheckOptionColor.BASIC,
        size = SDGCheckOptionSize.MEDIUM
    )

    private fun 비활성_상태_MEDIUM_SOLID_BASIC() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.SOLID,
        status = SDGCheckOptionStatus.DISABLED,
        selectedColor = SDGCheckOptionColor.BASIC,
        size = SDGCheckOptionSize.MEDIUM
    )

    private fun 기본_상태_MEDIUM_SOLID_SPECIAL() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.SOLID,
        status = SDGCheckOptionStatus.DEFAULT,
        selectedColor = SDGCheckOptionColor.SPECIAL,
        size = SDGCheckOptionSize.MEDIUM
    )

    private fun 선택된_상태_MEDIUM_SOLID_SPECIAL() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.SOLID,
        status = SDGCheckOptionStatus.SELECTED,
        selectedColor = SDGCheckOptionColor.SPECIAL,
        size = SDGCheckOptionSize.MEDIUM
    )

    private fun 비활성_상태_MEDIUM_SOLID_SPECIAL() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.SOLID,
        status = SDGCheckOptionStatus.DISABLED,
        selectedColor = SDGCheckOptionColor.SPECIAL,
        size = SDGCheckOptionSize.MEDIUM
    )

    private fun 기본_상태_LARGE_SOLID_BASIC() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.SOLID,
        status = SDGCheckOptionStatus.DEFAULT,
        selectedColor = SDGCheckOptionColor.BASIC,
        size = SDGCheckOptionSize.LARGE
    )

    private fun 선택된_상태_LARGE_SOLID_BASIC() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.SOLID,
        status = SDGCheckOptionStatus.SELECTED,
        selectedColor = SDGCheckOptionColor.BASIC,
        size = SDGCheckOptionSize.LARGE
    )

    private fun 비활성_상태_LARGE_SOLID_BASIC() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.SOLID,
        status = SDGCheckOptionStatus.DISABLED,
        selectedColor = SDGCheckOptionColor.BASIC,
        size = SDGCheckOptionSize.LARGE
    )

    private fun 기본_상태_LARGE_SOLID_SPECIAL() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.SOLID,
        status = SDGCheckOptionStatus.DEFAULT,
        selectedColor = SDGCheckOptionColor.SPECIAL,
        size = SDGCheckOptionSize.LARGE
    )

    private fun 선택된_상태_LARGE_SOLID_SPECIAL() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.SOLID,
        status = SDGCheckOptionStatus.SELECTED,
        selectedColor = SDGCheckOptionColor.SPECIAL,
        size = SDGCheckOptionSize.LARGE
    )

    private fun 비활성_상태_LARGE_SOLID_SPECIAL() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.SOLID,
        status = SDGCheckOptionStatus.DISABLED,
        selectedColor = SDGCheckOptionColor.SPECIAL,
        size = SDGCheckOptionSize.LARGE
    )

    private fun 기본_상태_MEDIUM_LINE_BASIC() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.LINE,
        status = SDGCheckOptionStatus.DEFAULT,
        selectedColor = SDGCheckOptionColor.BASIC,
        size = SDGCheckOptionSize.MEDIUM
    )

    private fun 선택된_상태_MEDIUM_LINE_BASIC() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.LINE,
        status = SDGCheckOptionStatus.SELECTED,
        selectedColor = SDGCheckOptionColor.BASIC,
        size = SDGCheckOptionSize.MEDIUM
    )

    private fun 비활성_상태_MEDIUM_LINE_BASIC() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.LINE,
        status = SDGCheckOptionStatus.DISABLED,
        selectedColor = SDGCheckOptionColor.BASIC,
        size = SDGCheckOptionSize.MEDIUM
    )

    private fun 기본_상태_MEDIUM_LINE_SPECIAL() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.LINE,
        status = SDGCheckOptionStatus.DEFAULT,
        selectedColor = SDGCheckOptionColor.SPECIAL,
        size = SDGCheckOptionSize.MEDIUM
    )

    private fun 선택된_상태_MEDIUM_LINE_SPECIAL() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.LINE,
        status = SDGCheckOptionStatus.SELECTED,
        selectedColor = SDGCheckOptionColor.SPECIAL,
        size = SDGCheckOptionSize.MEDIUM
    )

    private fun 비활성_상태_MEDIUM_LINE_SPECIAL() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.LINE,
        status = SDGCheckOptionStatus.DISABLED,
        selectedColor = SDGCheckOptionColor.SPECIAL,
        size = SDGCheckOptionSize.MEDIUM
    )

    private fun 기본_상태_LARGE_LINE_BASIC() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.LINE,
        status = SDGCheckOptionStatus.DEFAULT,
        selectedColor = SDGCheckOptionColor.BASIC,
        size = SDGCheckOptionSize.LARGE
    )

    private fun 선택된_상태_LARGE_LINE_BASIC() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.LINE,
        status = SDGCheckOptionStatus.SELECTED,
        selectedColor = SDGCheckOptionColor.BASIC,
        size = SDGCheckOptionSize.LARGE
    )

    private fun 비활성_상태_LARGE_LINE_BASIC() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.LINE,
        status = SDGCheckOptionStatus.DISABLED,
        selectedColor = SDGCheckOptionColor.BASIC,
        size = SDGCheckOptionSize.LARGE
    )

    private fun 기본_상태_LARGE_LINE_SPECIAL() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.LINE,
        status = SDGCheckOptionStatus.DEFAULT,
        selectedColor = SDGCheckOptionColor.SPECIAL,
        size = SDGCheckOptionSize.LARGE
    )

    private fun 선택된_상태_LARGE_LINE_SPECIAL() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.LINE,
        status = SDGCheckOptionStatus.SELECTED,
        selectedColor = SDGCheckOptionColor.SPECIAL,
        size = SDGCheckOptionSize.LARGE
    )

    private fun 비활성_상태_LARGE_LINE_SPECIAL() = SDGCheckOptionPreviewParams(
        type = SDGCheckOptionType.LINE,
        status = SDGCheckOptionStatus.DISABLED,
        selectedColor = SDGCheckOptionColor.SPECIAL,
        size = SDGCheckOptionSize.LARGE
    )

}
