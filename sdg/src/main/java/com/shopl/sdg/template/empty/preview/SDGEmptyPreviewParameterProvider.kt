package com.shopl.sdg.template.empty.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.template.empty.SDGEmptyBodyTextColor
import com.shopl.sdg.template.empty.SDGEmptyIllustrationBackgroundColor

internal class SDGEmptyPreviewParameterProvider :
    PreviewParameterProvider<SDGEmptyPreviewParameter> {

    override val values: Sequence<SDGEmptyPreviewParameter> = sequenceOf(
        결제_링크_버튼(),
        근무지_미등록(),
        긴_문구_버튼(),
    )

    private fun 결제_링크_버튼(): SDGEmptyPreviewParameter {
        return SDGEmptyPreviewParameter(
            illustrationBackgroundColor = SDGEmptyIllustrationBackgroundColor.PRIMARY_300_A10,
            title = "요금제가 궁금하신가요?",
            bodyText = "대시보드 [결제] 페이지에서 우리팀에 맞는 요금제를 확인해보세요!",
            bodyTextColor = SDGEmptyBodyTextColor.NEUTRAL_700,
            buttonLabel = "대시보드(PC) 링크 보기",
        )
    }

    private fun 근무지_미등록(): SDGEmptyPreviewParameter {
        return SDGEmptyPreviewParameter(
            illustrationBackgroundColor = SDGEmptyIllustrationBackgroundColor.PRIMARY_300_A10,
            title = "등록된 근무지가 없습니다.",
            bodyText = "근무지는 관리자가 [근무지] 메뉴에서 등록할 수 있어요.",
            bodyTextColor = SDGEmptyBodyTextColor.NEUTRAL_600,
            buttonLabel = null,
        )
    }

    private fun 긴_문구_버튼(): SDGEmptyPreviewParameter {
        return SDGEmptyPreviewParameter(
            illustrationBackgroundColor = SDGEmptyIllustrationBackgroundColor.NEUTRAL_150,
            title = "블라블라블라블라블라 대시보드(PC)에서만 지원됩니다.",
            bodyText = "블라블라블라블라블라블라블라블라블라블라블라블라블라블라\n(대시보드 > 출퇴근 > 위치 공유)",
            bodyTextColor = SDGEmptyBodyTextColor.NEUTRAL_600,
            buttonLabel = "대시보드(PC) 링크 보기",
        )
    }
}

internal data class SDGEmptyPreviewParameter(
    val illustrationBackgroundColor: SDGEmptyIllustrationBackgroundColor,
    val title: String?,
    val bodyText: String?,
    val bodyTextColor: SDGEmptyBodyTextColor,
    val buttonLabel: String?,
)
