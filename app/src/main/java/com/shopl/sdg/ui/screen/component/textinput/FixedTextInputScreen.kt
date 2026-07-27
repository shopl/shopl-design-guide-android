package com.shopl.sdg.ui.screen.component.textinput

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.text_input.fixed.SDGFixedTextInput
import com.shopl.sdg.component.text_input.fixed.SDGFixedTextInputField
import com.shopl.sdg.component.text_input.fixed.SDGFixedTextInputState
import com.shopl.sdg.component.text_input.fixed.SDGFixedTextInputStyle
import com.shopl.sdg.enums.SDGSampleStatus
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.ui.base.SDGSampleBaseComponentScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import kotlinx.collections.immutable.persistentListOf

private const val FIXED_TEXT_INPUT_SAMPLE_TEXT =
    "가슴속에 못 시인의 나의 별이 봅니다. 어머님, 멀리 별 이런 나는 추억과 남은 걱정도 " +
        "쓸쓸함과 있습니다. 다하지 딴은 나의 한 둘 언덕 이름과 별 까닭입니다. " +
        "별을 하나에 소학교 이름과, 봅니다. 하늘에는 별이 계속 이어집니다."

/**
 * SDG Sample App - Component - Text Input - FixedTextInput
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=18232-12815&m=dev">Figma</a>
 */
@Composable
internal fun FixedTextInputScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    val types = persistentListOf<SDGSampleBaseTabItem<SDGFixedTextInputStyle>>(
        SDGSampleBaseTabItem(
            title = SDGFixedTextInputStyle.Solid.styleName,
            item = SDGFixedTextInputStyle.Solid,
        ),
        SDGSampleBaseTabItem(
            title = SDGFixedTextInputStyle.Outlined.styleName,
            item = SDGFixedTextInputStyle.Outlined,
        ),
    )
    val specs = persistentListOf<SDGSampleBaseTabItem<SDGFixedTextInputField>>(
        SDGSampleBaseTabItem(
            title = SDGFixedTextInputField.LightGray.fieldName,
            item = SDGFixedTextInputField.LightGray,
        ),
        SDGSampleBaseTabItem(
            title = SDGFixedTextInputField.White.fieldName,
            item = SDGFixedTextInputField.White,
        ),
    )

    SDGSampleBaseComponentScaffold(
        componentName = ComponentScene.TextInput.FixedTextInput.displayLabel,
        componentDescription = "50자 이상의 텍스트 필드값을 입력할 수 있는 인풋 컴포넌트",
        types = types,
        specs = specs,
        guideLineDescriptions = persistentListOf(
            "입력 영역을 초과한 텍스트는 세로로 스크롤할 수 있습니다.",
            "스크롤바는 입력된 텍스트 길이에 따라 가변합니다.",
        ),
        componentContent = { currentType, currentSpec, currentStatus ->
            if (currentType != null && currentSpec != null) {
                ComponentContent(
                    style = currentType,
                    inputField = currentSpec,
                    status = currentStatus,
                )
            }
        },
        onClickBack = onClickBack,
        onClickMenu = onClickMenu,
    )
}

@Composable
private fun ComponentContent(
    style: SDGFixedTextInputStyle,
    inputField: SDGFixedTextInputField,
    status: SDGSampleStatus,
) {
    var text by remember { mutableStateOf(FIXED_TEXT_INPUT_SAMPLE_TEXT) }
    val state = when {
        status == SDGSampleStatus.DISABLED -> SDGFixedTextInputState.Disabled
        text.isEmpty() -> SDGFixedTextInputState.Default
        else -> SDGFixedTextInputState.Completed
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = SDGSpacing.Spacing16,
                vertical = SDGSpacing.Spacing40,
            ),
        contentAlignment = Alignment.Center,
    ) {
        SDGFixedTextInput(
            text = text,
            placeholder = "Placeholder",
            state = state,
            inputField = inputField,
            style = style,
            onTextChange = { text = it },
        )
    }
}

@Preview
@Composable
private fun PreviewFixedTextInputScreen() {
    ShoplDesignGuideTheme {
        FixedTextInputScreen(
            onClickBack = {},
            onClickMenu = {},
        )
    }
}
