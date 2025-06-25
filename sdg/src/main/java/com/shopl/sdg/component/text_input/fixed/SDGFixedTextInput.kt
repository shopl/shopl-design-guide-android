package com.shopl.sdg.component.text_input.fixed

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shopl.sdg.component.text_input.InputState
import com.shopl.sdg_common.enums.Keyboard
import com.shopl.sdg_common.enums.OutlineType
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.IOText
import com.shopl.sdg_common.ui.components.IOTypeface
import com.shopl.sdg_common.util.keyboardAsState

/**
 * SDG - Text Input - Fixed Text Input
 *
 * 50자 이상의 텍스트 필드값을 입력할 수 있는 인풋 컴포넌트
 *
 * @param inputState [InputState] 활성화/비활성화/에러 여부
 * @param onInputChange 인풋 값 변경 콜백
 * @param enableOnError 에러 활성화 여부
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=18232-12815&m=dev">Figma</a>
 */
@Composable
fun SDGFixedTextInput(
    outlineType: OutlineType,
    input: String?,
    hint: String,
    inputState: InputState,
    onInputChange: (String) -> Unit,
    height: Dp = 104.dp,
    focusRequester: FocusRequester? = null,
    backgroundColor: Color = SDGColor.Neutral0,
    marginValues: PaddingValues = PaddingValues(),
    maxLength: Int = Int.MAX_VALUE,
    enableOnError: Boolean = false,
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()

    LaunchedEffect(key1 = isKeyboardOpen) {
        if (isKeyboardOpen == Keyboard.Closed) {
            focusManager.clearFocus()
        }
    }

    val bgColor = when (inputState) {
        InputState.Enable -> backgroundColor
        InputState.Disable -> backgroundColor
        is InputState.Error -> SDGColor.Red300_a10
    }

    val inputModifier = Modifier
        .padding(marginValues)
        .fillMaxWidth()
        .height(height)
        .background(
            color = bgColor,
            shape = RoundedCornerShape(12.dp)
        )

    val textStyle = TextStyle(
        color = ,
        fontSize = 16.sp,
        fontFamily = IOTypeface.REGULAR.fontFamily,
        letterSpacing = 0.sp,
        lineHeight = 20.sp,
    )
    val textStyle = SDGTypography.Body1R.style.copy(
        color = SDGColor.Neutral700,
    )

    val lineHeight = with(LocalDensity.current) { 20.sp.toPx() }

    LaunchedEffect(key1 = scrollState.maxValue) {
        scrollState.animateScrollBy(lineHeight)
    }

    BasicTextField(
        modifier = inputModifier
            .then(
                if (outlineType == OutlineType.OUTLINE) {
                    Modifier.border(
                        width = 1.dp,
                        color = SDGColor.Neutral200,
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    Modifier
                }
            )
            .then(
                if (focusRequester != null) {
                    Modifier.focusRequester(focusRequester)
                } else {
                    Modifier
                }
            )
            .padding(12.dp)
            .verticalScroll(scrollState),
        value = input ?: "",
        onValueChange = {
            if (it.length <= maxLength) {
                onInputChange(it)
            }
        },
        enabled = when (inputState) {
            InputState.Enable -> true
            is InputState.Error -> enableOnError
            else -> false
        },
        textStyle = textStyle,
        cursorBrush = SolidColor(SDGColor.Neutral700),
        decorationBox = { textField ->
            textField()
            if (input.isNullOrEmpty()) {
                IOText(
                    text = hint,
                    textColor = SDGColor.Neutral300,
                    fontSize = 16.sp
                )
            }
        },
    )
}

@Preview
@Composable
private fun PreviewSDGFixedTextInput() {
    SDGFixedTextInput(
        marginValues = PaddingValues(20.dp),
        outlineType = OutlineType.OUTLINE,
        input = "12341234",
        hint = "asdfasdf",
        inputState = InputState.Enable,
        onInputChange = {}
    )
}
