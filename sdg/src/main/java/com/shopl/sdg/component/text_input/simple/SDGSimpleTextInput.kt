package com.shopl.sdg.component.text_input.simple

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.text_input.InputState
import com.shopl.sdg.component.text_input.simple.preview.SDGSimpleTextInputPreviewParameter
import com.shopl.sdg.component.text_input.simple.preview.SDGSimpleTextInputPreviewParameterProvider
import com.shopl.sdg_common.enums.Keyboard
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_common.util.keyboardAsState

private val SDGSimpleTextInputHeight = 40.dp
private val SDGSimpleTextInputBorderWidth = 1.dp
private const val SDGSimpleTextInputMaxLength = 50

/**
 * SDG - Text Input - Simple Text Input
 *
 * @version 2.3.36
 *
 * 50자 이하의 텍스트 필드값을 입력할 수 있는 인풋 컴포넌트
 *
 * @param text 입력된 텍스트
 * @param placeholder [SDGSimpleTextInputState.Default] 상태에서 표시할 안내 문구
 * @param state 인풋 상태
 * @param inputField Solid 스타일의 배경 유형
 * @param style Solid 또는 Outlined 스타일
 * @param onTextChange 텍스트 변경 콜백
 * @param visualTransformation 숫자 GroupSeparator, 마스킹 처리 등이 필요한 경우
 * @param keyboardOptions 키보드 옵션
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=6897-15134&m=dev">Figma</a>
 */
@Composable
fun SDGSimpleTextInput(
    text: String,
    placeholder: String,
    state: SDGSimpleTextInputState,
    inputField: SDGSimpleTextInputField,
    style: SDGSimpleTextInputStyle,
    onTextChange: (String) -> Unit,
    focusRequester: FocusRequester? = null,
    marginValues: PaddingValues = PaddingValues(),
    maxLength: Int = SDGSimpleTextInputMaxLength,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()
    var isFocused by remember { mutableStateOf(false) }
    var textFieldValueState by remember {
        mutableStateOf(
            TextFieldValue(
                text = text,
                selection = TextRange(text.length),
            ),
        )
    }
    val textFieldValue = textFieldValueState.copy(text = text)
    val isEnabled = state != SDGSimpleTextInputState.Disabled
    val showPlaceholder =
        state == SDGSimpleTextInputState.Default && !isFocused && text.isEmpty()
    val showEllipsizedText =
        !isFocused &&
            text.isNotEmpty() &&
            (state == SDGSimpleTextInputState.Completed || state == SDGSimpleTextInputState.Error)

    val backgroundColor = when {
        state == SDGSimpleTextInputState.Error && style == SDGSimpleTextInputStyle.Solid -> {
            SDGColor.Red300_a10
        }

        style == SDGSimpleTextInputStyle.Outlined -> SDGColor.Neutral0
        inputField == SDGSimpleTextInputField.LightGray -> SDGColor.Neutral50
        else -> SDGColor.Neutral0
    }
    val outlinedBorderColor = if (state == SDGSimpleTextInputState.Error) {
        SDGColor.Red300
    } else {
        SDGColor.Neutral200
    }
    val textColor = when (state) {
        SDGSimpleTextInputState.Disabled -> SDGColor.Neutral300
        else -> SDGColor.Neutral700
    }

    BasicTextField(
        modifier = Modifier
            .padding(marginValues)
            .fillMaxWidth()
            .height(SDGSimpleTextInputHeight)
            .clip(SDGCornerRadius.BoxRadius.Radius12)
            .background(backgroundColor)
            .then(
                when (style) {
                    SDGSimpleTextInputStyle.Solid -> Modifier
                    SDGSimpleTextInputStyle.Outlined -> Modifier.border(
                        width = SDGSimpleTextInputBorderWidth,
                        color = outlinedBorderColor,
                        shape = SDGCornerRadius.BoxRadius.Radius12,
                    )
                },
            )
            .then(
                if (focusRequester != null) {
                    Modifier.focusRequester(focusRequester)
                } else {
                    Modifier
                },
            )
            .onFocusChanged { focusState ->
                val gainedFocus = focusState.isFocused && !isFocused
                isFocused = focusState.isFocused
                if (gainedFocus && state == SDGSimpleTextInputState.Completed) {
                    textFieldValueState = textFieldValueState.copy(
                        text = text,
                        selection = TextRange(text.length),
                    )
                }
            }
            .padding(
                horizontal = SDGSpacing.Spacing12,
                vertical = SDGSpacing.Spacing10,
            ),
        value = textFieldValue,
        onValueChange = { updatedValue ->
            if (updatedValue.text.length <= maxLength) {
                textFieldValueState = updatedValue
                if (updatedValue.text != text) {
                    onTextChange(updatedValue.text)
                }
            }
        },
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        enabled = isEnabled,
        textStyle = SDGTypography.Body1R.style.copy(color = textColor),
        singleLine = true,
        maxLines = 1,
        cursorBrush = SolidColor(SDGColor.Neutral700),
        decorationBox = { textField ->
            Box(modifier = Modifier.fillMaxWidth()) {
                if (showPlaceholder) {
                    SDGText(
                        text = placeholder,
                        textColor = SDGColor.Neutral350,
                        typography = SDGTypography.Body1R,
                    )
                }
                if (showEllipsizedText) {
                    SDGText(
                        text = visualTransformation.filter(AnnotatedString(text)).text,
                        textColor = textColor,
                        typography = SDGTypography.Body1R,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Box(modifier = Modifier.alpha(0f)) {
                        textField()
                    }
                } else {
                    textField()
                }
            }
        },
    )

    LaunchedEffect(isKeyboardOpen, isEnabled) {
        if (!isEnabled && isFocused) {
            focusManager.clearFocus()
        } else if (isKeyboardOpen == Keyboard.Closed) {
            focusManager.clearFocus()
        }
    }

    SideEffect {
        if (
            textFieldValue.selection != textFieldValueState.selection ||
            textFieldValue.composition != textFieldValueState.composition
        ) {
            textFieldValueState = textFieldValue
        }
    }
}

/**
 * 신규 Simple Text Input API와의 하위 호환성을 위한 레거시 API입니다.
 */
@Deprecated(
    message = "SDGSimpleTextInput의 state, inputField, style 기반 API를 사용하세요.",
)
@Composable
fun SDGSimpleTextInput(
    type: SDGSimpleTextInputType,
    input: String,
    hint: String,
    inputState: InputState,
    onInputChange: (String) -> Unit,
    focusRequester: FocusRequester? = null,
    maxLines: Int = 1,
    backgroundColor: Color = SDGColor.Neutral0,
    marginValues: PaddingValues = PaddingValues(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    val state = when (inputState) {
        InputState.Enable -> if (input.isEmpty()) {
            SDGSimpleTextInputState.Default
        } else {
            SDGSimpleTextInputState.Completed
        }

        InputState.Disable -> SDGSimpleTextInputState.Disabled
        is InputState.Error -> SDGSimpleTextInputState.Error
    }
    val inputField = when (backgroundColor) {
        SDGColor.Neutral50 -> SDGSimpleTextInputField.LightGray
        else -> SDGSimpleTextInputField.White
    }
    val style = when (type) {
        SDGSimpleTextInputType.BASIC -> SDGSimpleTextInputStyle.Solid
        SDGSimpleTextInputType.LINE -> SDGSimpleTextInputStyle.Outlined
    }

    SDGSimpleTextInput(
        text = input,
        placeholder = hint,
        state = state,
        inputField = inputField,
        style = style,
        onTextChange = onInputChange,
        focusRequester = focusRequester,
        marginValues = marginValues,
        maxLength = SDGSimpleTextInputMaxLength,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGSimpleTextInput(
    @PreviewParameter(SDGSimpleTextInputPreviewParameterProvider::class)
    parameter: SDGSimpleTextInputPreviewParameter,
) {
    with(parameter) {
        SDGSimpleTextInput(
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
