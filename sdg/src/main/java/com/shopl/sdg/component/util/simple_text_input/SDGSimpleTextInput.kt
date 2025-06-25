package com.shopl.sdg.component.util.simple_text_input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.text_input.InputState
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInputType
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import java.text.DecimalFormat

/**
 * [SDGSimpleTextInput]
 *
 * @param onFocusChanged 입력 필드의 포커스 상태가 변경될 때 호출되는 콜백
 *
 * @sample
 * ```
 * var input by remember { mutableStateOf(TextFieldValue("")) }
 * SDGSimpleTextInput(
 *     type = SDGSimpleTextInputType.LINE,
 *     input = input,
 *     hint = "이름을 입력하세요",
 *     inputState = InputState.Enable,
 *     onInputChange = { input = it }
 * )
 * ```
 */
@Composable
fun SDGSimpleTextInput(
    type: SDGSimpleTextInputType,
    input: TextFieldValue,
    hint: String,
    inputState: InputState,
    focusRequester: FocusRequester? = null,
    maxLines: Int = 1,
    backgroundColor: Color = SDGColor.Neutral0,
    marginValues: PaddingValues = PaddingValues(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onInputChange: (TextFieldValue) -> Unit,
    onFocusChanged: ((FocusState) -> Unit)? = null
) {
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
            )
            .then(
                onFocusChanged?.let {
                    Modifier.onFocusChanged { onFocusChanged(it) }
                } ?: Modifier
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
                    if (input.text.isEmpty()) {
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

/**
 * [SDGSimpleTextInput]
 *
 * 숫자 입력에 대해 [DecimalFormat]을 통한 실시간 포맷팅 및 값의 최소/최대 범위 유효성 검사를 지원합니다.
 *
 * @param alignCenter 입력값 및 힌트 텍스트를 중앙 정렬할지 여부. 기본값은 false (왼쪽 정렬)
 * @param decimalFormat 숫자 입력에 대해 적용할 [DecimalFormat]. 지정 시 실시간 포맷팅 및 유효성 검사 적용
 * @param minValue 숫자 입력일 경우 허용하는 최소값
 * @param maxValue 숫자 입력일 경우 허용하는 최대값
 *
 * @sample
 * ```
 * var input by remember { mutableStateOf(TextFieldValue("")) }
 * SDGSimpleTextInput(
 *     type = SDGSimpleTextInputType.LINE,
 *     input = input,
 *     hint = "금액을 입력하세요",
 *     inputState = InputState.Enable,
 *     decimalFormat = DecimalFormat("#,###"),
 *     minValue = 1000.0,
 *     maxValue = 1000000.0,
 *     onInputChange = { input = it }
 * )
 * ```
 */
@Composable
fun SDGSimpleTextInput(
    type: SDGSimpleTextInputType,
    input: TextFieldValue,
    hint: String,
    inputState: InputState,
    focusRequester: FocusRequester? = null,
    maxLines: Int = 1,
    backgroundColor: Color = SDGColor.Neutral0,
    marginValues: PaddingValues = PaddingValues(),
    alignCenter: Boolean = false,
    decimalFormat: DecimalFormat? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onInputChange: (TextFieldValue) -> Unit,
    onFocusChanged: ((FocusState) -> Unit)? = null,
    minValue: Double? = null,
    maxValue: Double? = null,
) {
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

    val displayValue = decimalFormat?.let { formatter ->
        val numberValue = input.text.toDoubleOrNull()
        if (numberValue == null ||
            (input.text.endsWith(formatter.decimalFormatSymbols.decimalSeparator)
                    && input.text.count { it == formatter.decimalFormatSymbols.decimalSeparator } == 1)
        ) {
            input
        } else {
            val originValueLength = input.text.length
            val formattedValue = formatter.format(numberValue)
            val displayValueLength = formattedValue.length
            if (input.selection.end > 0) {
                input.copy(
                    text = formattedValue,
                    selection = TextRange(input.selection.end + (displayValueLength - originValueLength))
                )
            } else {
                input.copy(
                    text = formattedValue,
                )
            }
        }
    } ?: input

    BasicTextField(
        modifier = Modifier
            .then(
                if (focusRequester != null) {
                    Modifier.focusRequester(focusRequester)
                } else {
                    Modifier
                }
            )
            .then(
                onFocusChanged?.let {
                    Modifier.onFocusChanged { onFocusChanged(it) }
                } ?: Modifier
            ),
        value = displayValue,
        onValueChange = { originValue ->
            var value = originValue
            decimalFormat?.let { formatter ->
                val decimalSeparator = formatter.decimalFormatSymbols.decimalSeparator
                val groupingSeparator = formatter.decimalFormatSymbols.groupingSeparator
                runCatching {
                    var valueText = value.text
                    if (keyboardOptions.keyboardType == KeyboardType.Number) {
                        if (decimalSeparator == ',' && valueText.endsWith(".")) {
                            // 삼성 키보드 이슈 대응
                            // decimalSeparator == ',' 일때, 숫자 키패드의 , 가 비활성화 됨
                            valueText = valueText.dropLast(1) + decimalSeparator
                            value = value.copy(text = valueText)
                        }
                        if (valueText.endsWith(groupingSeparator)) {
                            valueText = valueText.dropLast(1)
                            value = value.copy(text = valueText)
                        }
                    }
                    formatter.parse(valueText)
                }.onSuccess { parsedValue ->
                    if (parsedValue == null
                        || (value.text.endsWith(decimalSeparator)
                                && value.text.count { it == decimalSeparator } == 1)
                    ) {
                        onInputChange(value)
                    } else {
                        val selection = if (parsedValue.toString().length != value.text.length) {
                            TextRange(parsedValue.toString().length)
                        } else {
                            value.selection
                        }
                        if (minValue != null && maxValue != null) {
                            if (parsedValue.toDouble() in minValue..maxValue) {
                                onInputChange(
                                    value.copy(
                                        text = parsedValue.toString(),
                                        selection = selection
                                    )
                                )
                            }
                        } else {
                            onInputChange(
                                value.copy(
                                    text = parsedValue.toString(),
                                    selection = selection
                                )
                            )
                        }
                    }
                }
                    .onFailure {
                        val isFilteredValueEmpty = value.text.none { it.isDigit() || it == decimalSeparator || it == '-' }
                        if (isFilteredValueEmpty) {
                            onInputChange(value.copy(text = ""))
                        } else {
                            onInputChange(value)
                        }
                    }
            } ?: onInputChange(value)
        },
        keyboardOptions = keyboardOptions,
        enabled = inputState != InputState.Disable,
        textStyle = SDGTypography.Body1R.style.copy(
            color = if (inputState == InputState.Disable) SDGColor.Neutral300 else SDGColor.Neutral700,
            textAlign = if (alignCenter) {
                TextAlign.Center
            } else TextAlign.Start
        ),
        singleLine = maxLines == 1,
        maxLines = maxLines,
        cursorBrush = SolidColor(SDGColor.Neutral700),
        decorationBox = { innerTextField ->
            Box(
                Modifier
                    .padding(marginValues)
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
            ) {
                Box(
                    modifier = Modifier.then(
                        if (alignCenter) {
                            Modifier.align(Alignment.Center)
                        } else Modifier
                    ),
                ) {
                    if (input.text.isEmpty()) {
                        SDGText(
                            modifier = Modifier.then(
                                if (alignCenter) {
                                    Modifier.align(Alignment.Center)
                                } else Modifier
                            ),
                            text = hint,
                            textColor = SDGColor.Neutral300,
                            typography = SDGTypography.Body1R,
                            textAlign = if (alignCenter) {
                                TextAlign.Center
                            } else TextAlign.Start
                        )
                    }
                    innerTextField()
                }
            }
        },
    )
}

@Preview
@Composable
private fun PreviewSDGSimpleTextInput() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        var input by remember { mutableStateOf(TextFieldValue("")) }
        SDGSimpleTextInput(
            type = SDGSimpleTextInputType.LINE,
            input = input,
            hint = "이름을 입력하세요",
            inputState = InputState.Enable,
            onInputChange = { input = it }
        )

        var numberInput by remember { mutableStateOf(TextFieldValue("")) }
        SDGSimpleTextInput(
            type = SDGSimpleTextInputType.LINE,
            input = numberInput,
            hint = "금액을 입력하세요",
            inputState = InputState.Enable,
            decimalFormat = DecimalFormat("#,###"),
            minValue = 1000.0,
            maxValue = 1000000.0,
            onInputChange = { numberInput = it }
        )
    }
}