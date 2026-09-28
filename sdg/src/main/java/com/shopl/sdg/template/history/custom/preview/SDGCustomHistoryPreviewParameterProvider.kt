package com.shopl.sdg.template.history.custom.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.shopl.sdg.template.history.custom.SDGCustomHistoryPosition
import com.shopl.sdg_common.foundation.SDGColor

private val HeaderBackgroundModifier = Modifier.background(
    SDGColor.PurpleP_a10,
    RoundedCornerShape(8.dp),
)

internal class SDGCustomHistoryPreviewParameterProvider :
    PreviewParameterProvider<SDGCustomHistoryPreviewParams> {

    override val values: Sequence<SDGCustomHistoryPreviewParams> = sequenceOf(
        독립_이력(),
        첫번째_이력(),
        중간_이력(),
        여러_줄_헤더(),
        Header_배경_없음(),
        마지막_이력(),
        Body_패딩_적용(),
    )

    private fun 독립_이력() = SDGCustomHistoryPreviewParams(
        position = SDGCustomHistoryPosition.FIRST,
        header = "근무 이력이 등록되었습니다",
        body = null,
        headerModifier = HeaderBackgroundModifier,
    )

    private fun 첫번째_이력() = SDGCustomHistoryPreviewParams(
        position = SDGCustomHistoryPosition.TOP,
        header = "근무 일정이 변경되었습니다",
        body = "변경된 일정: 09:00 - 18:00",
        headerModifier = HeaderBackgroundModifier,
    )

    private fun 중간_이력() = SDGCustomHistoryPreviewParams(
        position = SDGCustomHistoryPosition.MIDDLE,
        header = "담당자가 배정되었습니다",
        body = "담당자: 홍길동\n처리 예정: 오늘 18:00",
        headerModifier = HeaderBackgroundModifier,
    )

    private fun 여러_줄_헤더() = SDGCustomHistoryPreviewParams(
        position = SDGCustomHistoryPosition.MIDDLE,
        header = "근무 일정이 변경되었습니다\n2026. 09. 28 09:30",
        body = "변경된 일정: 10:00 - 18:00",
        headerModifier = HeaderBackgroundModifier,
    )

    private fun 마지막_이력() = SDGCustomHistoryPreviewParams(
        position = SDGCustomHistoryPosition.LAST,
        header = "검토가 완료되었습니다",
        body = null,
        headerModifier = HeaderBackgroundModifier,
    )

    private fun Header_배경_없음() = 중간_이력().copy(
        headerModifier = Modifier,
    )

    private fun Body_패딩_적용() = SDGCustomHistoryPreviewParams(
        position = SDGCustomHistoryPosition.MIDDLE,
        header = "본문에 추가 여백이 적용되었습니다",
        body = "본문 영역에 기본값과 다른 여백을 적용한 예시입니다.",
        headerModifier = HeaderBackgroundModifier,
        bodyContentPadding = PaddingValues(
            start = 16.dp,
            top = 8.dp,
            end = 16.dp,
            bottom = 8.dp,
        ),
    )
}
