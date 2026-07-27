package com.shopl.sdg.component.text_input.fixed

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
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

private val SDGFixedTextInputHeight = 104.dp
private val SDGFixedTextInputBorderWidth = 1.dp
private val SDGFixedTextInputScrollbarWidth = 4.dp
private val SDGFixedTextInputScrollbarMinHeight = 16.dp

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
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    require(maxLength >= 0) { "maxLength는 0 이상이어야 합니다." }

    val scrollState = rememberScrollState()
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

    val backgroundColor = when {
        state == SDGFixedTextInputState.Error && style == SDGFixedTextInputStyle.Solid -> {
            SDGColor.Red300_a10
        }

        style == SDGFixedTextInputStyle.Outlined -> SDGColor.Neutral0
        inputField == SDGFixedTextInputField.LightGray -> SDGColor.Neutral50
        else -> SDGColor.Neutral0
    }
    val outlinedBorderColor = if (state == SDGFixedTextInputState.Error) {
        SDGColor.Red300
    } else {
        SDGColor.Neutral200
    }
    val textColor = when (state) {
        SDGFixedTextInputState.Disabled -> SDGColor.Neutral300
        else -> SDGColor.Neutral700
    }

    BasicTextField(
        modifier = Modifier
            .padding(marginValues)
            .fillMaxWidth()
            .height(SDGFixedTextInputHeight)
            .background(
                color = backgroundColor,
                shape = SDGCornerRadius.BoxRadius.Radius12,
            )
            .then(
                when (style) {
                    SDGFixedTextInputStyle.Solid -> Modifier
                    SDGFixedTextInputStyle.Outlined -> Modifier.border(
                        width = SDGFixedTextInputBorderWidth,
                        color = outlinedBorderColor,
                        shape = SDGCornerRadius.BoxRadius.Radius12,
                    )
                }
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
                if (gainedFocus) {
                    textFieldValueState = textFieldValueState.copy(
                        text = text,
                        selection = TextRange(text.length),
                    )
                }
            }
            .padding(SDGSpacing.Spacing12)
            .fixedTextInputScrollbar(scrollState)
            .verticalScroll(scrollState),
        value = textFieldValue,
        onValueChange = { newValue ->
            if (newValue.text.length <= maxLength) {
                textFieldValueState = newValue
                if (newValue.text != text) {
                    onTextChange(newValue.text)
                }
            }
        },
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
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

    LaunchedEffect(isKeyboardOpen) {
        if (isKeyboardOpen == Keyboard.Closed) {
            focusManager.clearFocus()
        }
    }

    LaunchedEffect(scrollState.maxValue) {
        scrollState.animateScrollTo(scrollState.maxValue)
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
 * Fixed Text Input의 콘텐츠가 입력 영역을 초과할 때 우측에 세로 스크롤바를 표시합니다.
 *
 * Thumb 높이는 viewport와 전체 콘텐츠 높이의 비율에 따라 가변하며 최소 높이를 보장합니다.
 * 스크롤바 위치는 [ScrollState.value]에 따라 갱신되고, overflow가 없으면 표시하지 않습니다.
 *
 * @param scrollState 텍스트 영역의 스크롤 범위와 현재 위치를 제공하는 상태
 */
private fun Modifier.fixedTextInputScrollbar(scrollState: ScrollState): Modifier {
    return drawWithContent {
        drawContent()

        val viewportSize = scrollState.viewportSize
        val maxScrollValue = scrollState.maxValue
        if (viewportSize <= 0 || maxScrollValue <= 0) {
            return@drawWithContent
        }

        val viewportHeight = size.height
        val contentHeight = viewportSize.toFloat() + maxScrollValue
        val minThumbHeight = minOf(
            SDGFixedTextInputScrollbarMinHeight.toPx(),
            viewportHeight,
        )
        val thumbHeight = (viewportHeight * viewportSize / contentHeight)
            .coerceIn(minThumbHeight, viewportHeight)
        val scrollFraction = scrollState.value.toFloat() / maxScrollValue
        val thumbOffsetY = (viewportHeight - thumbHeight) * scrollFraction
        val scrollbarWidth = SDGFixedTextInputScrollbarWidth.toPx()

        drawRoundRect(
            color = SDGColor.Neutral300,
            topLeft = Offset(
                x = size.width - scrollbarWidth,
                y = thumbOffsetY,
            ),
            size = Size(
                width = scrollbarWidth,
                height = thumbHeight,
            ),
            cornerRadius = CornerRadius(
                x = scrollbarWidth / 2,
                y = scrollbarWidth / 2,
            ),
        )
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
    height: Dp = SDGFixedTextInputHeight,
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
