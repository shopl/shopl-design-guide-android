package com.shopl.sdg.component.text_input.simple

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.text_input.InputState
import com.shopl.sdg_common.enums.Keyboard
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_common.util.keyboardAsState

/**
 * SDG - Text Input - Simple Text Input
 *
 * 50자 이내의 텍스트 필드값을 입력할 수 있는 인풋 컴포넌트
 *
 * @param inputState [InputState] 활성화/비활성화/에러 여부
 * @param keyboardOptions [KeyboardOptions]
 * @param onInputChange 인풋 값 변경 콜백
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=6897-15134&m=dev">Figma</a>
 */
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
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()

    LaunchedEffect(key1 = isKeyboardOpen) {
        if (isKeyboardOpen == Keyboard.Closed) {
            focusManager.clearFocus()
        }
    }

    val inputBgColor = when (inputState) {
        InputState.Disable,
        InputState.Enable -> backgroundColor

        is InputState.Error -> {
            if (type == SDGSimpleTextInputType.LINE) {
                backgroundColor
            } else {
                SDGColor.Red300_a10
            }
        }
    }

    val lineColor = when (inputState) {
        InputState.Disable,
        InputState.Enable -> SDGColor.Neutral200

        is InputState.Error -> SDGColor.Red300
    }

    BasicTextField(
        modifier = Modifier
            .then(
                if (focusRequester != null) {
                    Modifier.focusRequester(focusRequester)
                } else {
                    Modifier
                }
            ),
        value = input,
        onValueChange = onInputChange,
        keyboardOptions = keyboardOptions,
        enabled = inputState != InputState.Disable,
        textStyle = SDGTypography.Body1R.style.copy(
            color = if (inputState == InputState.Disable) SDGColor.Neutral300 else SDGColor.Neutral700,
        ),
        singleLine = maxLines == 1,
        maxLines = maxLines,
        cursorBrush = SolidColor(SDGColor.Neutral700),
        decorationBox = { innerTextField ->
            Row(
                Modifier
                    .padding(marginValues)
                    .fillMaxWidth()
                    .heightIn(min = 40.dp)
                    .background(
                        color = inputBgColor,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .then(
                        if (type == SDGSimpleTextInputType.LINE) {
                            Modifier.border(
                                width = 1.dp,
                                brush = SolidColor(lineColor),
                                shape = RoundedCornerShape(12.dp)
                            )
                        } else {
                            Modifier
                        }
                    )
                    .padding(vertical = 10.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    if (input.isEmpty()) {
                        SDGText(
                            text = hint,
                            textColor = SDGColor.Neutral300,
                            typography = SDGTypography.Body1R
                        )
                    }
                    innerTextField()
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGSimpleTextInput() {
    SDGSimpleTextInput(
        type = SDGSimpleTextInputType.BASIC,
        input = "start",
        backgroundColor = SDGColor.Transparent,
        hint = "hint",
        inputState = InputState.Enable,
        maxLines = 1,
        onInputChange = { }
    )
}