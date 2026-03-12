package com.shopl.sdg.component.guide_container

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
 * @param guide 가이드 메시지 노출: SDGGuideUiState.Show(message = 표기할 메시지, messageColor = 메시지 색상(Default: Neutral700))
 *              가이드 메시지 비노출: SDGGuideUiState.Hide
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
                text = guide.message,
                textColor = guide.messageColor,
                typography = SDGTypography.Body3R,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGGuideContainer() {
    SDGGuideContainer(
        guide = SDGGuideUiState.Show(
            message = "PreviewSDGGuideContainer"
        ),
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
