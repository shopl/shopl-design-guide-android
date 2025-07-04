package com.shopl.sdg.component.text_input.underline

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shopl.sdg.component.text_input.InputState
import com.shopl.sdg_common.enums.Keyboard
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_common.util.keyboardAsState
import com.shopl.sdg_resource.R

/**
 * SDG - Text Input - Underline Input
 *
 * 고유한 정보의 값을 입력하고 확인하는 인풋 컴포넌트
 *
 * @param inputState [InputState] 활성화/비활성화/에러 여부
 * @param onInputChange 인풋 값 변경 콜백
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=18232-14747&m=dev">Figma</a>
 */
@Composable
fun SDGUnderlineInput(
    input: String?,
    hint: String,
    inputState: InputState,
    onInputChange: (String) -> Unit,
    backgroundColor: Color = SDGColor.Neutral200,
    marginValues: PaddingValues = PaddingValues(),
) {
    val scrollState = rememberScrollState()
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val focusManager = LocalFocusManager.current

    val isKeyboardOpen by keyboardAsState()

    val lineColor = when (inputState) {
        InputState.Enable -> backgroundColor
        InputState.Disable -> backgroundColor
        is InputState.Error -> SDGColor.Red300
    }

    val textColor = when (inputState) {
        InputState.Disable -> SDGColor.Neutral300
        else -> SDGColor.Neutral700
    }

    val inputModifier = Modifier
        .padding(marginValues)
        .fillMaxWidth()
        .focusRequester(focusRequester = focusRequester)

    val textStyle = SDGTypography.Title2R.style.copy(
        color = textColor,
    )

    var maxHeight by remember { mutableIntStateOf(-1) }
    val lineHeight = with(LocalDensity.current) { 22.sp.toPx() }

    LaunchedEffect(key1 = input) {
        if (maxHeight < scrollState.maxValue) {
            scrollState.animateScrollBy(lineHeight)
            maxHeight = scrollState.maxValue
        }
    }

    LaunchedEffect(key1 = isKeyboardOpen) {
        if (isKeyboardOpen == Keyboard.Closed) {
            focusManager.clearFocus()
        }
    }

    BasicTextField(
        modifier = inputModifier,
        value = input ?: "",
        onValueChange = {
            onInputChange(it)
        },
        enabled = inputState != InputState.Disable,
        textStyle = textStyle,
        singleLine = true,
        cursorBrush = SolidColor(SDGColor.Neutral700),
        decorationBox = { textField ->
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 8.dp,
                        bottom = 8.dp
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.weight(1F)) {
                        textField()
                        if (input.isNullOrEmpty()) {
                            SDGText(
                                text = hint,
                                textColor = SDGColor.Neutral300,
                                typography = SDGTypography.Title2R
                            )
                        }
                    }

                    if (!input.isNullOrEmpty() && isKeyboardOpen == Keyboard.Opened) {
                        SDGImage(
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .clickable {
                                    onInputChange.invoke("")
                                },
                            resId = R.drawable.ic_input_delete,
                            color = null
                        )
                    }
                }
                Spacer(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = lineColor)
                )
            }
        },
    )
}

@Preview
@Composable
private fun PreviewSDGUnderlineInput() {
    SDGUnderlineInput(
        marginValues = PaddingValues(20.dp),
        input = "12341234",
        hint = "asdfasdf",
        inputState = InputState.Enable,
        onInputChange = {}
    )
}