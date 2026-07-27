package com.shopl.sdg.component.util.simple_text_input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.text_input.InputState
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInputField
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInputState
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInputStyle
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInputType
import com.shopl.sdg_common.enums.Keyboard
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_common.util.keyboardAsState
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

private val SDGSimpleTextInputHeight = 40.dp
private val SDGSimpleTextInputBorderWidth = 1.dp
private const val SDGSimpleTextInputMaxLength = 50

/**
 * TextFieldValue를 사용하는 Simple Text Input입니다.
 *
 * 숫자 포맷을 적용하지 않는 입력 필드에서 커서와 선택 영역을 직접 제어할 수 있습니다.
 */
@Composable
fun SDGSimpleTextInput(
    input: TextFieldValue,
    hint: String,
    state: SDGSimpleTextInputState,
    inputField: SDGSimpleTextInputField,
    style: SDGSimpleTextInputStyle,
    onInputChange: (TextFieldValue) -> Unit,
    focusRequester: FocusRequester? = null,
    maxLines: Int = 1,
    marginValues: PaddingValues = PaddingValues(),
    maxLength: Int = SDGSimpleTextInputMaxLength,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    onFocusChanged: ((FocusState) -> Unit)? = null,
) {
    require(maxLength in 0..SDGSimpleTextInputMaxLength) {
        "maxLength는 0 이상 $SDGSimpleTextInputMaxLength 이하여야 합니다."
    }

    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()
    var isFocused by remember { mutableStateOf(false) }
    val isEnabled = state != SDGSimpleTextInputState.Disabled
    val backgroundColor = resolveBackgroundColor(state, inputField, style)
    val outlinedBorderColor = resolveOutlinedBorderColor(state)
    val textColor = resolveTextColor(state)
    val showPlaceholder =
        state == SDGSimpleTextInputState.Default && !isFocused && input.text.isEmpty()
    val showEllipsizedText =
        !isFocused &&
            input.text.isNotEmpty() &&
            maxLines == 1 &&
            (state == SDGSimpleTextInputState.Completed || state == SDGSimpleTextInputState.Error)

    BasicTextField(
        modifier = Modifier.simpleTextInputModifier(
            marginValues = marginValues,
            maxLines = maxLines,
            backgroundColor = backgroundColor,
            style = style,
            outlinedBorderColor = outlinedBorderColor,
            focusRequester = focusRequester,
            onFocusChanged = { focusState ->
                val gainedFocus = focusState.isFocused && !isFocused
                isFocused = focusState.isFocused
                if (gainedFocus && state == SDGSimpleTextInputState.Completed) {
                    onInputChange(input.copy(selection = TextRange(input.text.length)))
                }
                onFocusChanged?.invoke(focusState)
            },
        ),
        value = input,
        onValueChange = { value ->
            if (value.text.length <= maxLength) {
                onInputChange(value)
            }
        },
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        enabled = isEnabled,
        textStyle = SDGTypography.Body1R.style.copy(color = textColor),
        singleLine = maxLines == 1,
        maxLines = maxLines,
        cursorBrush = SolidColor(SDGColor.Neutral700),
        decorationBox = { textField ->
            Box(modifier = Modifier.fillMaxWidth()) {
                if (showPlaceholder) {
                    SDGText(
                        text = hint,
                        textColor = SDGColor.Neutral350,
                        typography = SDGTypography.Body1R,
                    )
                }
                if (showEllipsizedText) {
                    SDGText(
                        text = visualTransformation.filter(input.annotatedString).text,
                        textColor = textColor,
                        typography = SDGTypography.Body1R,
                        maxLines = 1,
                        overflow = overflow,
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
    input: TextFieldValue,
    hint: String,
    inputState: InputState,
    onInputChange: (TextFieldValue) -> Unit,
    focusRequester: FocusRequester? = null,
    maxLines: Int = 1,
    backgroundColor: Color = SDGColor.Neutral0,
    marginValues: PaddingValues = PaddingValues(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    onFocusChanged: ((FocusState) -> Unit)? = null,
) {
    SDGSimpleTextInput(
        input = input,
        hint = hint,
        state = inputState.toSimpleTextInputState(input.text),
        inputField = backgroundColor.toSimpleTextInputField(),
        style = type.toSimpleTextInputStyle(),
        onInputChange = onInputChange,
        focusRequester = focusRequester,
        maxLines = maxLines,
        marginValues = marginValues,
        maxLength = SDGSimpleTextInputMaxLength,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        overflow = overflow,
        onFocusChanged = onFocusChanged,
    )
}

/**
 * [SDGSimpleTextInput]
 *
 * @version 2.0.0
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
    input: TextFieldValue,
    hint: String,
    state: SDGSimpleTextInputState,
    inputField: SDGSimpleTextInputField,
    style: SDGSimpleTextInputStyle,
    decimalFormat: DecimalFormat? = null,
    focusRequester: FocusRequester? = null,
    maxLines: Int = 1,
    marginValues: PaddingValues = PaddingValues(),
    maxLength: Int = SDGSimpleTextInputMaxLength,
    alignCenter: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    onInputChange: (TextFieldValue) -> Unit,
    onFocusChanged: ((FocusState) -> Unit)? = null,
    minValue: Double? = null,
    maxValue: Double? = null,
) {
    require(maxLength in 0..SDGSimpleTextInputMaxLength) {
        "maxLength는 0 이상 $SDGSimpleTextInputMaxLength 이하여야 합니다."
    }

    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()
    var isFocused by remember { mutableStateOf(false) }
    val minBigDecimal = remember(minValue) { minValue?.toBigDecimal() }
    val maxBigDecimal = remember(maxValue) { maxValue?.toBigDecimal() }
    val isEnabled = state != SDGSimpleTextInputState.Disabled
    val backgroundColor = resolveBackgroundColor(state, inputField, style)
    val outlinedBorderColor = resolveOutlinedBorderColor(state)
    val textColor = resolveTextColor(state)
    val showPlaceholder =
        state == SDGSimpleTextInputState.Default && !isFocused && input.text.isEmpty()

    val displayValue = decimalFormat?.let { formatter ->
        val numberValue = input.text.removeSuffix(".").toBigDecimalOrNull()
        val partialDecimalValue = input.text.formatPartialDecimalValue(formatter)
        val shouldKeepInput = numberValue == null

        if (shouldKeepInput) {
            input
        } else if (partialDecimalValue != null) {
            val originValueLength = input.text.length
            val displayValueLength = partialDecimalValue.length
            input.copy(
                text = partialDecimalValue,
                selection = TextRange(input.selection.end + (displayValueLength - originValueLength))
            )
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
        modifier = Modifier.simpleTextInputModifier(
            marginValues = marginValues,
            maxLines = maxLines,
            backgroundColor = backgroundColor,
            style = style,
            outlinedBorderColor = outlinedBorderColor,
            focusRequester = focusRequester,
            onFocusChanged = { focusState ->
                val gainedFocus = focusState.isFocused && !isFocused
                isFocused = focusState.isFocused
                if (gainedFocus && state == SDGSimpleTextInputState.Completed) {
                    onInputChange(input.copy(selection = TextRange(input.text.length)))
                }
                onFocusChanged?.invoke(focusState)
            },
        ),
        value = displayValue,
        onValueChange = { originValue ->
            if (originValue.text.length > maxLength) {
                return@BasicTextField
            }
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
                    val normalizedText = value.text.toNormalizedNumberText(formatter)
                    if (!normalizedText.isValidFractionLength(formatter)) {
                        return@onSuccess
                    }

                    if (parsedValue == null
                        || (value.text.endsWith(decimalSeparator)
                                && value.text.count { it == decimalSeparator } == 1)
                    ) {
                        onInputChange(
                            value.copy(
                                text = normalizedText,
                                selection = TextRange(normalizedText.length),
                            )
                        )
                    } else {
                        val parsedBigDecimal = normalizedText
                            .removeSuffix(suffix = ".")
                            .toBigDecimalOrNull()

                        val selection = if (normalizedText.length != value.text.length) {
                            TextRange(normalizedText.length)
                        } else {
                            value.selection
                        }

                        if (parsedBigDecimal == null) {
                            return@onSuccess
                        }

                        val isInRange =
                            (minBigDecimal == null || parsedBigDecimal >= minBigDecimal) &&
                                    (maxBigDecimal == null || parsedBigDecimal <= maxBigDecimal)

                        if (isInRange) {
                            onInputChange(
                                value.copy(
                                    text = normalizedText,
                                    selection = selection,
                                )
                            )
                        }
                    }
                }
                    .onFailure {
                        val isFilteredValueEmpty =
                            value.text.none { it.isDigit() || it == decimalSeparator || it == '-' }
                        if (isFilteredValueEmpty) {
                            onInputChange(value.copy(text = ""))
                        } else {
                            onInputChange(value)
                        }
                    }
            } ?: onInputChange(value)
        },
        keyboardOptions = keyboardOptions,
        enabled = isEnabled,
        textStyle = SDGTypography.Body1R.style.copy(
            color = textColor,
            textAlign = if (alignCenter) {
                TextAlign.Center
            } else {
                TextAlign.Start
            },
        ),
        singleLine = maxLines == 1,
        maxLines = maxLines,
        cursorBrush = SolidColor(SDGColor.Neutral700),
        decorationBox = { textField ->
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier.then(
                        if (alignCenter) {
                            Modifier.align(Alignment.Center)
                        } else Modifier
                    ),
                ) {
                    if (showPlaceholder) {
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

                    if (
                        !isFocused &&
                        displayValue.text.isNotEmpty() &&
                        maxLines == 1 &&
                        (state == SDGSimpleTextInputState.Completed || state == SDGSimpleTextInputState.Error)
                    ) {
                        SDGText(
                            modifier = Modifier.then(
                                if (alignCenter) {
                                    Modifier.align(Alignment.Center)
                                } else Modifier
                            ),
                            text = displayValue.text,
                            textColor = textColor,
                            typography = SDGTypography.Body1R,
                            textAlign = if (alignCenter) {
                                TextAlign.Center
                            } else {
                                TextAlign.Start
                            },
                            maxLines = 1,
                            overflow = overflow,
                        )
                        Box(modifier = Modifier.alpha(0f)) {
                            textField()
                        }
                    } else {
                        textField()
                    }
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
}

/**
 * 신규 숫자 Simple Text Input API와의 하위 호환성을 위한 레거시 API입니다.
 */
@Deprecated(
    message = "SDGSimpleTextInput의 state, inputField, style 기반 API를 사용하세요.",
)
@Composable
fun SDGSimpleTextInput(
    type: SDGSimpleTextInputType,
    input: TextFieldValue,
    hint: String,
    inputState: InputState,
    decimalFormat: DecimalFormat? = null,
    focusRequester: FocusRequester? = null,
    maxLines: Int = 1,
    backgroundColor: Color = SDGColor.Neutral0,
    marginValues: PaddingValues = PaddingValues(),
    alignCenter: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    onInputChange: (TextFieldValue) -> Unit,
    onFocusChanged: ((FocusState) -> Unit)? = null,
    minValue: Double? = null,
    maxValue: Double? = null,
) {
    SDGSimpleTextInput(
        input = input,
        hint = hint,
        state = inputState.toSimpleTextInputState(input.text),
        inputField = backgroundColor.toSimpleTextInputField(),
        style = type.toSimpleTextInputStyle(),
        decimalFormat = decimalFormat,
        focusRequester = focusRequester,
        maxLines = maxLines,
        marginValues = marginValues,
        maxLength = SDGSimpleTextInputMaxLength,
        alignCenter = alignCenter,
        keyboardOptions = keyboardOptions,
        overflow = overflow,
        onInputChange = onInputChange,
        onFocusChanged = onFocusChanged,
        minValue = minValue,
        maxValue = maxValue,
    )
}

private fun String.isTypingDecimalSeparator(formatter: DecimalFormat): Boolean {
    val decimalSeparator = formatter.decimalFormatSymbols.decimalSeparator
    return endsWith(decimalSeparator) && count { it == decimalSeparator } == 1
}

private fun String.toNormalizedNumberText(formatter: DecimalFormat) = replace(
    oldValue = formatter.decimalFormatSymbols.groupingSeparator.toString(),
    newValue = ""
)
    .replace(oldValue = formatter.decimalFormatSymbols.decimalSeparator.toString(), newValue = ".")

private fun String.formatPartialDecimalValue(formatter: DecimalFormat): String? {
    if (!isTypingNormalizedDecimalSeparator() && !shouldKeepNormalizedFractionInput(formatter)) return null

    val decimalSeparator = formatter.decimalFormatSymbols.decimalSeparator
    val integer = substringBefore(".")
    val fraction = substringAfter(".")
    val isNegative = integer.startsWith("-")
    val integerValue = if (integer.isEmpty() || integer == "-") {
        0.toBigDecimal()
    } else {
        integer.toBigDecimalOrNull() ?: return null
    }

    val formattedInteger = formatter.format(integerValue)
    val displayInteger = if (isNegative && !formattedInteger.startsWith("-")) {
        "-$formattedInteger"
    } else {
        formattedInteger
    }

    return displayInteger + decimalSeparator + fraction
}

private fun String.isTypingNormalizedDecimalSeparator(): Boolean {
    return endsWith(".") && count { it == '.' } == 1
}

private fun String.isValidFractionLength(formatter: DecimalFormat): Boolean {
    if (!contains(".")) return true
    if (formatter.maximumFractionDigits <= 0) return false

    val fraction = substringAfter(".")
    return fraction.length <= formatter.maximumFractionDigits
}

private fun String.shouldKeepNormalizedFractionInput(formatter: DecimalFormat): Boolean {
    if (formatter.maximumFractionDigits <= 0 || !contains(".")) return false

    val fraction = substringAfter(".")
    return fraction.isNotEmpty() &&
            fraction.length <= formatter.maximumFractionDigits &&
            fraction.last() == '0'
}

/** Simple Text Input의 공통 modifier를 적용합니다. */
private fun Modifier.simpleTextInputModifier(
    marginValues: PaddingValues,
    maxLines: Int,
    backgroundColor: Color,
    style: SDGSimpleTextInputStyle,
    outlinedBorderColor: Color,
    focusRequester: FocusRequester?,
    onFocusChanged: (FocusState) -> Unit,
): Modifier {
    return padding(marginValues)
        .fillMaxWidth()
        .then(
            if (maxLines == 1) {
                Modifier.height(SDGSimpleTextInputHeight)
            } else {
                Modifier.heightIn(min = SDGSimpleTextInputHeight)
            },
        )
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
        .onFocusChanged(onFocusChanged)
        .padding(
            horizontal = SDGSpacing.Spacing12,
            vertical = SDGSpacing.Spacing10,
        )
}

/** 상태와 스타일에 맞는 배경색을 반환합니다. */
private fun resolveBackgroundColor(
    state: SDGSimpleTextInputState,
    inputField: SDGSimpleTextInputField,
    style: SDGSimpleTextInputStyle,
): Color {
    return when {
        state == SDGSimpleTextInputState.Error && style == SDGSimpleTextInputStyle.Solid -> {
            SDGColor.Red300_a10
        }

        style == SDGSimpleTextInputStyle.Outlined -> SDGColor.Neutral0
        inputField == SDGSimpleTextInputField.LightGray -> SDGColor.Neutral50
        else -> SDGColor.Neutral0
    }
}

/** 상태에 맞는 Outlined border 색상을 반환합니다. */
private fun resolveOutlinedBorderColor(state: SDGSimpleTextInputState): Color {
    return if (state == SDGSimpleTextInputState.Error) {
        SDGColor.Red300
    } else {
        SDGColor.Neutral200
    }
}

/** 상태에 맞는 입력 텍스트 색상을 반환합니다. */
private fun resolveTextColor(state: SDGSimpleTextInputState): Color {
    return when (state) {
        SDGSimpleTextInputState.Disabled -> SDGColor.Neutral300
        else -> SDGColor.Neutral700
    }
}

/** 레거시 InputState를 신규 입력 상태로 변환합니다. */
private fun InputState.toSimpleTextInputState(text: String): SDGSimpleTextInputState {
    return when (this) {
        InputState.Enable -> if (text.isEmpty()) {
            SDGSimpleTextInputState.Default
        } else {
            SDGSimpleTextInputState.Completed
        }

        InputState.Disable -> SDGSimpleTextInputState.Disabled
        is InputState.Error -> SDGSimpleTextInputState.Error
    }
}

/** 레거시 배경색을 신규 입력 필드 유형으로 변환합니다. */
private fun Color.toSimpleTextInputField(): SDGSimpleTextInputField {
    return when (this) {
        SDGColor.Neutral50 -> SDGSimpleTextInputField.LightGray
        else -> SDGSimpleTextInputField.White
    }
}

/** 레거시 타입을 신규 스타일 유형으로 변환합니다. */
private fun SDGSimpleTextInputType.toSimpleTextInputStyle(): SDGSimpleTextInputStyle {
    return when (this) {
        SDGSimpleTextInputType.BASIC -> SDGSimpleTextInputStyle.Solid
        SDGSimpleTextInputType.LINE -> SDGSimpleTextInputStyle.Outlined
    }
}

@Preview
@Composable
private fun PreviewSDGSimpleTextInput() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        var input by remember { mutableStateOf(TextFieldValue("")) }
        SDGSimpleTextInput(
            input = input,
            hint = "이름을 입력하세요",
            state = SDGSimpleTextInputState.Default,
            inputField = SDGSimpleTextInputField.White,
            style = SDGSimpleTextInputStyle.Outlined,
            onInputChange = { input = it },
        )


        val defaultDecimalFormat = DecimalFormat(
            "###,###.###",
        )
        var numberInput by remember { mutableStateOf(TextFieldValue("")) }
        SDGSimpleTextInput(
            input = numberInput,
            hint = "금액을 입력하세요(Default Decimal Format)",
            state = SDGSimpleTextInputState.Default,
            inputField = SDGSimpleTextInputField.White,
            style = SDGSimpleTextInputStyle.Outlined,
            decimalFormat = defaultDecimalFormat,
            maxValue = 100000.0,
            onInputChange = { numberInput = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        SDGText(
            text = "Origin Value : ${numberInput.text}",
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body3R
        )

        val indonesiaDecimalFormat = DecimalFormat(
            "###,###.###",
            DecimalFormatSymbols.getInstance(Locale("id", "ID"))
        )
        var indonesiaNumberInput by remember { mutableStateOf(TextFieldValue("")) }
        SDGSimpleTextInput(
            input = indonesiaNumberInput,
            hint = "금액을 입력하세요(Indonesia Decimal Format)",
            state = SDGSimpleTextInputState.Default,
            inputField = SDGSimpleTextInputField.White,
            style = SDGSimpleTextInputStyle.Outlined,
            decimalFormat = indonesiaDecimalFormat,
            maxValue = 100000.0,
            onInputChange = { indonesiaNumberInput = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        SDGText(
            text = "Origin Value : ${indonesiaNumberInput.text}",
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body3R
        )
    }
}
