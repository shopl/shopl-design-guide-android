package com.shopl.sdg.component.text_input.fixed

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.text_input.InputState
import com.shopl.sdg.component.text_input.fixed.preview.SDGFixedTextInputPreviewParameter
import com.shopl.sdg.component.text_input.fixed.preview.SDGFixedTextInputPreviewParameterProvider
import com.shopl.sdg_common.enums.Keyboard
import com.shopl.sdg_common.enums.OutlineType
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing20
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_common.util.keyboardAsState

/**
 * SDG - Text Input - Fixed Text Input
 *
 * @version 2.3.36
 *
 * 50자 이상의 텍스트 필드값을 입력할 수 있는 인풋 컴포넌트
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=18232-12815&m=dev">Figma</a>
 */
@Composable
fun SDGFixedTextInput(
    text: String,
    placeholder: String,
    state: SDGFixedTextInputState,
    inputField: SDGFixedTextInputField,
    style: SDGFixedTextInputStyle,
    onTextChange: (String) -> Unit,
    focusRequester: FocusRequester? = null,
    marginValues: PaddingValues = PaddingValues(),
    maxLength: Int = Int.MAX_VALUE,
) {
    require(maxLength >= 0) { "maxLength는 0 이상이어야 합니다." }

    val scrollState = rememberScrollState()
    val defaultFocusRequester = remember { FocusRequester() }
    val resolvedFocusRequester = focusRequester ?: defaultFocusRequester
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()
    var isFocused by remember { mutableStateOf(false) }

    val backgroundColor = when {
        state == SDGFixedTextInputState.Error && style == SDGFixedTextInputStyle.Solid -> {
            SDGColor.Red300_a10
        }

        style == SDGFixedTextInputStyle.Outlined -> SDGColor.Neutral0
        inputField == SDGFixedTextInputField.LightGray -> SDGColor.Neutral50
        else -> SDGColor.Neutral0
    }
    val borderColor = when {
        state == SDGFixedTextInputState.Error && style == SDGFixedTextInputStyle.Outlined -> {
            SDGColor.Red300
        }

        style == SDGFixedTextInputStyle.Outlined -> SDGColor.Neutral200
        else -> null
    }
    val textColor = when (state) {
        SDGFixedTextInputState.Disabled -> SDGColor.Neutral300
        else -> SDGColor.Neutral700
    }

    BasicTextField(
        modifier = Modifier
            .padding(marginValues)
            .fillMaxWidth()
            .height(SDGSpacing.Spacing104)
            .background(
                color = backgroundColor,
                shape = SDGCornerRadius.BoxRadius.Radius12,
            )
            .then(
                if (borderColor != null) {
                    Modifier.border(
                        width = 1.dp,
                        color = borderColor,
                        shape = SDGCornerRadius.BoxRadius.Radius12,
                    )
                } else {
                    Modifier
                }
            )
            .focusRequester(resolvedFocusRequester)
            .onFocusChanged { isFocused = it.isFocused }
            .padding(SDGSpacing.Spacing12)
            .verticalScroll(scrollState),
        value = text,
        onValueChange = {
            if (it.length <= maxLength) {
                onTextChange(it)
            }
        },
        enabled = state != SDGFixedTextInputState.Disabled,
        textStyle = SDGTypography.Body1R.style.copy(color = textColor),
        cursorBrush = SolidColor(SDGColor.Neutral700),
        decorationBox = { textField ->
            Box {
                if (state == SDGFixedTextInputState.Default && !isFocused && text.isEmpty()) {
                    SDGText(
                        text = placeholder,
                        textColor = SDGColor.Neutral350,
                        typography = SDGTypography.Body1R,
                    )
                }
                textField()
            }
        },
    )

    LaunchedEffect(isKeyboardOpen, state) {
        if (
            isKeyboardOpen == Keyboard.Closed &&
            state != SDGFixedTextInputState.Focused
        ) {
            focusManager.clearFocus()
        }
    }

    LaunchedEffect(state, resolvedFocusRequester) {
        if (state == SDGFixedTextInputState.Focused) {
            resolvedFocusRequester.requestFocus()
        }
    }

    LaunchedEffect(scrollState.maxValue) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }
}

/**
 * 신규 Fixed Text Input API와의 하위 호환성을 위한 레거시 API입니다.
 */
@Deprecated(
    message = "SDGFixedTextInput의 state, inputField, style 기반 API를 사용하세요.",
)
@Composable
fun SDGFixedTextInput(
    outlineType: OutlineType,
    input: String?,
    hint: String,
    inputState: InputState,
    onInputChange: (String) -> Unit,
    height: Dp = SDGSpacing.Spacing104,
    focusRequester: FocusRequester? = null,
    backgroundColor: Color = SDGColor.Neutral0,
    marginValues: PaddingValues = PaddingValues(),
    maxLength: Int = Int.MAX_VALUE,
    enableOnError: Boolean = false,
) {
    val state = when (inputState) {
        InputState.Enable -> if (input.isNullOrEmpty()) {
            SDGFixedTextInputState.Default
        } else {
            SDGFixedTextInputState.Completed
        }

        InputState.Disable -> SDGFixedTextInputState.Disabled
        is InputState.Error -> SDGFixedTextInputState.Error
    }
    val inputField = when (backgroundColor) {
        SDGColor.Neutral50 -> SDGFixedTextInputField.LightGray
        else -> SDGFixedTextInputField.White
    }
    val style = when (outlineType) {
        OutlineType.BASIC -> SDGFixedTextInputStyle.Solid
        OutlineType.OUTLINE -> SDGFixedTextInputStyle.Outlined
    }
    val canChangeInput = inputState !is InputState.Error || enableOnError

    SDGFixedTextInput(
        text = input.orEmpty(),
        placeholder = hint,
        state = state,
        inputField = inputField,
        style = style,
        onTextChange = if (canChangeInput) onInputChange else { _ -> },
        focusRequester = focusRequester,
        marginValues = marginValues,
        maxLength = maxLength,
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGFixedTextInput(
    @PreviewParameter(SDGFixedTextInputPreviewParameterProvider::class)
    parameter: SDGFixedTextInputPreviewParameter,
) {
    with(parameter) {
        Box(modifier = Modifier
            .background(SDGColor.Neutral700)
            .padding(Spacing20)) {
            SDGFixedTextInput(
                text = text,
                placeholder = placeholder,
                state = state,
                inputField = inputField,
                style = style,
                onTextChange = {},
                marginValues = PaddingValues(SDGSpacing.Spacing20),
            )
        }
    }
}
