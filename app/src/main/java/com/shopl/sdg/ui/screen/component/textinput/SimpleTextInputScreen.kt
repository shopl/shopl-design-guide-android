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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInput
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInputField
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInputState
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInputStyle
import com.shopl.sdg.enums.SDGSampleStatus
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.ui.base.SDGSampleBaseComponentScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_resource.R
import kotlinx.collections.immutable.persistentListOf

private const val SIMPLE_TEXT_INPUT_SAMPLE_TEXT = "Text"

/**
 * SDG Sample App - Component - Text Input - SimpleTextInput
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=6897-15134&m=dev">Figma</a>
 */
@Composable
internal fun SimpleTextInputScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    val types = persistentListOf<SDGSampleBaseTabItem<SDGSimpleTextInputStyle>>(
        SDGSampleBaseTabItem(
            title = SDGSimpleTextInputStyle.Solid.styleName,
            item = SDGSimpleTextInputStyle.Solid,
        ),
        SDGSampleBaseTabItem(
            title = SDGSimpleTextInputStyle.Outlined.styleName,
            item = SDGSimpleTextInputStyle.Outlined,
        ),
    )
    val specs = persistentListOf<SDGSampleBaseTabItem<SDGSimpleTextInputField>>(
        SDGSampleBaseTabItem(
            title = SDGSimpleTextInputField.LightGray.fieldName,
            item = SDGSimpleTextInputField.LightGray,
        ),
        SDGSampleBaseTabItem(
            title = SDGSimpleTextInputField.White.fieldName,
            item = SDGSimpleTextInputField.White,
        ),
    )

    SDGSampleBaseComponentScaffold(
        componentName = ComponentScene.TextInput.SimpleTextInput.displayLabel,
        componentDescription = "50자 이하의 텍스트 필드값을 입력할 수 있는 인풋 컴포넌트",
        types = types,
        specs = specs,
        guideLineDescriptions = persistentListOf(
            "Completed → Focused 상태로 변경했을 경우 커서는 텍스트의 마지막에 위치합니다."
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
    style: SDGSimpleTextInputStyle,
    inputField: SDGSimpleTextInputField,
    status: SDGSampleStatus,
) {
    var text by remember { mutableStateOf(SIMPLE_TEXT_INPUT_SAMPLE_TEXT) }
    val state = when {
        status == SDGSampleStatus.DISABLED -> SDGSimpleTextInputState.Disabled
        text.isEmpty() -> SDGSimpleTextInputState.Default
        else -> SDGSimpleTextInputState.Completed
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
        SDGSimpleTextInput(
            text = text,
            placeholder = stringResource(id = R.string.text_hint_study_place),
            state = state,
            inputField = inputField,
            style = style,
            onTextChange = { text = it },
        )
    }
}

@Preview
@Composable
private fun PreviewSimpleTextInputScreen() {
    ShoplDesignGuideTheme {
        SimpleTextInputScreen(
            onClickBack = {},
            onClickMenu = {},
        )
    }
}
