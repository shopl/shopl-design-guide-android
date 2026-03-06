package com.shopl.sdg.template.popup.bottom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing12
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing24
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing28
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

private const val MAX_SHEET_HEIGHT_RATIO = 0.7f

/**
 * SDG - Template - Popup - Bottom Popup
 *
 * 앱 콘텐츠 앞 하단에 표시되며, 태스크 수행을 위한 콘텐츠를 포함하는 모달형 템플릿
 *
 * @param buttonOption 하단 버튼 옵션 (1버튼, 2버튼)
 * @param onDismissRequest 외부 클릭이나 뒤로가기로 팝업이 닫힐 때 호출
 * @param title 팝업 타이틀 (Optional)
 * @param titleAlignment 타이틀의 정렬 방향 (기본: Left)
 * @param sheetState 만약 중간 펼침(PartiallyExpanded) 상태가 필요하다면
 *                   rememberModalBottomSheetState(skipPartiallyExpanded = true)으로 설정
 * @param body 팝업 본문 콘텐츠
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19210-2895&m=dev">Figma</a>
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SDGBottomPopup(
    buttonOption: SDGBottomPopupButtonOption,
    onDismissRequest: () -> Unit,
    title: String? = null,
    titleAlignment: TextAlign = TextAlign.Left,
    sheetState: SheetState = rememberModalBottomSheetState(),
    body: @Composable (ColumnScope.() -> Unit),
) {
    val containerSize = LocalWindowInfo.current.containerSize
    val density = LocalDensity.current.density
    val screenHeight = (containerSize.height / density).dp
    val maxSheetHeight = screenHeight * MAX_SHEET_HEIGHT_RATIO

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = RoundedCornerShape(
            topStart = SDGCornerRadius.Radius20,
            topEnd = SDGCornerRadius.Radius20
        ),
        containerColor = SDGColor.Neutral0,
        scrimColor = SDGColor.Neutral900_a40,
        dragHandle = null,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = maxSheetHeight)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(space = Spacing12),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Spacing24, start = Spacing24, end = Spacing24)
            ) {
                if (!title.isNullOrBlank()) {
                    SDGText(
                        modifier = Modifier.fillMaxWidth(),
                        text = title,
                        typography = SDGTypography.Title2SB,
                        textColor = SDGColor.Neutral700,
                        textAlign = titleAlignment,
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(weight = 1f, fill = false)
                        .verticalScroll(state = rememberScrollState())
                        .padding(top = Spacing4, bottom = Spacing28)
                ) {
                    body()
                }
            }

            SDGBottomPopupButton(option = buttonOption)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun PreviewSDGBottomPopup() {
    SDGBottomPopup(
        title = "하단 팝업 타이틀",
        buttonOption = SDGBottomPopupButtonOption.TwoOption(
            startLabel = "Label",
            onClickStart = {},
            endLabel = "Label",
            onClickEnd = {},
        ),
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        onDismissRequest = {},
        body = {
            SDGText(
                text = "본문 영역입니다. 70% 높이까지 확장 후 스크롤됩니다.",
                typography = SDGTypography.Body1R,
                textColor = SDGColor.Neutral600
            )
        }
    )
}
