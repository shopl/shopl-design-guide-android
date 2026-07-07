package com.shopl.sdg.component.guide_container

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.shopl.sdg.component.guide_container.preview.SDGGuideContainerPreviewParameterProvider
import com.shopl.sdg.component.guide_container.preview.SDGGuideContainerPreviewParams
import com.shopl.sdg.component.text_input.InputState
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInput
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInputType
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Guide Container
 *
 * 특정 콘텐츠 영역 하단에 정보성이나 경고성 문구를 선택적으로 보여주는 템플릿
 *
 * @version 2.1.10
 *
 * @param guide 가이드 메시지 노출/비노출 상태.
 *              Show: message, messageColor(Default: Neutral700), messageAlign(Default: Start)
 *              Hide: 비노출
 * @param marginValues 전체 컨테이너 외부 여백
 * @param contentArea 컨텐츠 영역에 배치할 컴포저블
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=24707-1385&m=dev">Figma</a>
 */
@Composable
fun SDGGuideContainer(
    guide: SDGGuideUiState = SDGGuideUiState.Hide,
    marginValues: PaddingValues = PaddingValues(),
    contentArea: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier.padding(paddingValues = marginValues),
        verticalArrangement = Arrangement.spacedBy(space = SDGSpacing.Spacing10)
    ) {
        contentArea()

        if (guide is SDGGuideUiState.Show) {
            SDGText(
                modifier = Modifier.fillMaxWidth(),
                text = guide.message,
                textColor = guide.messageColor,
                typography = SDGTypography.Body3R,
                textAlign = guide.messageAlign,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGGuideContainer(
    @PreviewParameter(SDGGuideContainerPreviewParameterProvider::class)
    params: SDGGuideContainerPreviewParams
) {
    SDGGuideContainer(
        guide = params.guide,
        contentArea = {
            SDGSimpleTextInput(
                type = SDGSimpleTextInputType.BASIC,
                input = "",
                hint = "placeholder",
                inputState = InputState.Enable,
                backgroundColor = SDGColor.Neutral50,
                onInputChange = {},
            )
        }
    )
}