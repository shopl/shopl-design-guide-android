package com.shopl.sdg.component.thumbnail.extension

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.thumbnail.SDGThumbnailRow
import com.shopl.sdg.component.thumbnail.THUMBNAILS_PER_ROW
import com.shopl.sdg.component.thumbnail.model.SDGThumbnailsLine
import com.shopl.sdg.component.thumbnail.model.SDGThumbnailsType
import com.shopl.sdg.component.thumbnail.preview.SDGThumbnailsExtensionPreviewParameterProvider
import com.shopl.sdg.component.thumbnail.preview.SDGThumbnailsPreviewParams
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing

private object SDGThumbnailRowContentType

/**
 * SDG - Component - Thumbnails for [LazyListScope]
 *
 * [com.shopl.sdg.component.thumbnail.SDGThumbnails]와 동일한 UI 및 동작을 제공하면서,
 * 각 썸네일 행을 상위 Lazy layout의 item으로 구성합니다.
 *
 * @version 2.3.43
 *
 * @param type 썸네일 유형과 썸네일 목록 및 클릭 이벤트
 * @param line 썸네일 표시 방식과 클리어 아이콘 설정
 * @param failureImageBackgroundColor 이미지 로드 실패 시 표시할 배경색
 * @param marginValues 컴포넌트 외부 여백
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=6870-15446&m=dev">Figma</a>
 */
fun LazyListScope.SDGThumbnails(
    type: SDGThumbnailsType,
    line: SDGThumbnailsLine,
    failureImageBackgroundColor: Color = SDGColor.Neutral0,
    marginValues: PaddingValues = PaddingValues(),
) {
    val visibleThumbnails = when (line) {
        is SDGThumbnailsLine.SingleLine -> type.thumbnails.take(n = THUMBNAILS_PER_ROW)
        is SDGThumbnailsLine.MultiLine -> type.thumbnails
    }
    val rowCount = (visibleThumbnails.size + THUMBNAILS_PER_ROW - 1) / THUMBNAILS_PER_ROW

    if (rowCount == 0) {
        item(
            contentType = SDGThumbnailRowContentType,
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues = marginValues),
            )
        }
        return
    }

    items(
        count = rowCount,
        key = { rowIndex ->
            visibleThumbnails[rowIndex * THUMBNAILS_PER_ROW].id
        },
        contentType = { SDGThumbnailRowContentType },
    ) { rowIndex ->
        val rowStartIndex = rowIndex * THUMBNAILS_PER_ROW
        val rowThumbnails = visibleThumbnails.subList(
            fromIndex = rowStartIndex,
            toIndex = minOf(
                a = rowStartIndex + THUMBNAILS_PER_ROW,
                b = visibleThumbnails.size,
            ),
        )
        val layoutDirection = LocalLayoutDirection.current

        SDGThumbnailRow(
            modifier = Modifier
                .fillMaxWidth()
                .absolutePadding(
                    left = marginValues.calculateLeftPadding(layoutDirection = layoutDirection),
                    top = if (rowIndex == 0) {
                        marginValues.calculateTopPadding()
                    } else {
                        SDGSpacing.Spacing8
                    },
                    right = marginValues.calculateRightPadding(layoutDirection = layoutDirection),
                    bottom = if (rowIndex == rowCount - 1) {
                        marginValues.calculateBottomPadding()
                    } else {
                        0.dp
                    },
                ),
            thumbnails = rowThumbnails,
            type = type,
            line = line,
            rowStartIndex = rowStartIndex,
            failureImageBackgroundColor = failureImageBackgroundColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLazyListScopeSDGThumbnails(
    @PreviewParameter(provider = SDGThumbnailsExtensionPreviewParameterProvider::class)
    params: SDGThumbnailsPreviewParams,
) {
    LazyColumn(
        modifier = Modifier.background(color = SDGColor.Neutral50),
        contentPadding = PaddingValues(all = SDGSpacing.Spacing20),
    ) {
        SDGThumbnails(
            type = params.type,
            line = params.line,
            failureImageBackgroundColor = SDGColor.Neutral0,
            marginValues = PaddingValues(),
        )
    }
}
