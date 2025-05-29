package com.shopl.sdg.component.text_input

import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.TextToolbar
import androidx.compose.ui.platform.TextToolbarStatus
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shopl.sdg_common.enums.Keyboard
import com.shopl.sdg_common.enums.OutlineType
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.IOText
import com.shopl.sdg_common.ui.components.IOTypeface
import com.shopl.sdg_common.ui.components.TypefaceConfig
import com.shopl.sdg_resource.R
import java.text.DecimalFormat

@Stable
sealed class InputState {
    object Enable : InputState()
    object Disable : InputState()
    data class Error(val message: String) : InputState()
}

@Composable
fun SDGFixedInput(
    outlineType: OutlineType,
    height: Dp = 104.dp,
    input: String?,
    hint: String,
    inputState: InputState,
    focusRequester: FocusRequester? = null,
    backgroundColor: Color = SDGColor.Neutral0,
    marginValues: PaddingValues = PaddingValues(),
    maxLength: Int = Int.MAX_VALUE,
    enableOnError: Boolean = false,
    onInputChange: (String) -> Unit,
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
        color = SDGColor.Neutral700,
        fontSize = 16.sp,
        fontFamily = IOTypeface.REGULAR.fontFamily,
        letterSpacing = 0.sp,
        lineHeight = 20.sp,
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

/**
 * Copy & Paste deny
 * CompositionLocalProvider(
 *      LocalTextToolbar provides EmptyTextToolbar
 *  ) {
 *      SDGUnderlineInput()
 *  }
 */
@Composable
fun SDGUnderlineInput(
    input: String?,
    hint: String,
    inputState: InputState,
    backgroundColor: Color = SDGColor.Neutral200,
    marginValues: PaddingValues = PaddingValues(),
    onInputChange: (String) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
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
        .onFocusChanged {
            //Log.e("SDGInput", "Focus : ${it.isFocused}")
        }

    val textStyle = TextStyle(
        color = textColor,
        fontSize = 18.sp,
        fontFamily = FontFamily(typeface = TypefaceConfig.normal),
        letterSpacing = 0.sp,
        lineHeight = 22.sp,
    )

    var maxHeight by remember { mutableStateOf(-1) }
    val lineHeight = with(LocalDensity.current) { 22.sp.toPx() }

    LaunchedEffect(key1 = input) {
        if (maxHeight < scrollState.maxValue) {
            scrollState.animateScrollBy(lineHeight)
            maxHeight = scrollState.maxValue
        }
    }

    LaunchedEffect(key1 = isKeyboardOpen) {
        //Log.e("SDGInput", "SideEffect isKeyboardOpen : $isKeyboardOpen")
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
                            IOText(
                                text = hint,
                                textColor = SDGColor.Neutral300,
                                fontSize = 18.sp,
                                lineHeight = 22.sp,
                            )
                        }
                    }

                    if (!input.isNullOrEmpty() && isKeyboardOpen == Keyboard.Opened) {
                        Image(
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .clickable {
                                    onInputChange.invoke("")
                                },
                            painter = painterResource(
                                id = R.drawable.ic_input_delete
                            ),
                            contentDescription = null
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

@SuppressLint("NewApi")
@Composable
fun keyboardAsState(): State<Keyboard> {
    val keyboardState = remember { mutableStateOf(Keyboard.Closed) }
    val view = LocalView.current
    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            keyboardState.value = if (keypadHeight > screenHeight * 0.15) {
                Keyboard.Opened
            } else {
                Keyboard.Closed
            }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }

    return keyboardState
}

object EmptyTextToolbar : TextToolbar {
    override val status: TextToolbarStatus = TextToolbarStatus.Hidden

    override fun hide() {}

    override fun showMenu(
        rect: androidx.compose.ui.geometry.Rect,
        onCopyRequested: (() -> Unit)?,
        onPasteRequested: (() -> Unit)?,
        onCutRequested: (() -> Unit)?,
        onSelectAllRequested: (() -> Unit)?
    ) {
    }

}

@Stable
enum class SDGSimpleInputType {
    BASIC, LINE
}

@Composable
fun SDGSimpleInput(
    type: SDGSimpleInputType,
    input: String,
    hint: String,
    state: InputState,
    focusRequester: FocusRequester? = null,
    maxLines: Int = 1,
    backgroundColor: Color = SDGColor.Neutral0,
    marginValues: PaddingValues = PaddingValues(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onInputChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()

    LaunchedEffect(key1 = isKeyboardOpen) {
        if (isKeyboardOpen == Keyboard.Closed) {
            focusManager.clearFocus()
        }
    }

    val inputBgColor = when (state) {
        InputState.Disable,
        InputState.Enable -> backgroundColor

        is InputState.Error -> {
            if (type == SDGSimpleInputType.LINE) {
                backgroundColor
            } else {
                SDGColor.Red300_a10
            }
        }
    }

    val lineColor = when (state) {
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
        enabled = state != InputState.Disable,
        textStyle = TextStyle(
            color = if (state == InputState.Disable) SDGColor.Neutral300 else SDGColor.Neutral700,
            fontSize = 16.sp,
            fontFamily = FontFamily(typeface = TypefaceConfig.normal),
            letterSpacing = 0.sp,
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
                        if (type == SDGSimpleInputType.LINE) {
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
                        IOText(text = hint, textColor = SDGColor.Neutral300, fontSize = 14.sp)
                    }
                    innerTextField()
                }
            }
        },
    )
}

@Composable
fun SDGSimpleInput(
    type: SDGSimpleInputType,
    input: TextFieldValue,
    hint: String,
    state: InputState,
    focusRequester: FocusRequester? = null,
    maxLines: Int = 1,
    backgroundColor: Color = SDGColor.Neutral0,
    marginValues: PaddingValues = PaddingValues(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onInputChange: (TextFieldValue) -> Unit,
    onFocusChanged: ((FocusState) -> Unit)? = null
) {
    val inputBgColor = when (state) {
        InputState.Disable,
        InputState.Enable -> backgroundColor

        is InputState.Error -> {
            if (type == SDGSimpleInputType.LINE) {
                backgroundColor
            } else {
                SDGColor.Red300_a10
            }
        }
    }

    val lineColor = when (state) {
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
        enabled = state != InputState.Disable,
        textStyle = TextStyle(
            color = if (state == InputState.Disable) SDGColor.Neutral300 else SDGColor.Neutral700,
            fontSize = 16.sp,
            fontFamily = FontFamily(typeface = TypefaceConfig.normal),
            letterSpacing = 0.sp,
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
                        if (type == SDGSimpleInputType.LINE) {
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
                        IOText(text = hint, textColor = SDGColor.Neutral300, fontSize = 14.sp)
                    }
                    innerTextField()
                }
            }
        },
    )
}

@Composable
fun SDGSimpleInput(
    type: SDGSimpleInputType,
    input: TextFieldValue,
    hint: String,
    state: InputState,
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
    val inputBgColor = when (state) {
        InputState.Disable,
        InputState.Enable -> backgroundColor

        is InputState.Error -> {
            if (type == SDGSimpleInputType.LINE) {
                backgroundColor
            } else {
                SDGColor.Red300_a10
            }
        }
    }

    val lineColor = when (state) {
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
        enabled = state != InputState.Disable,
        textStyle = TextStyle(
            color = if (state == InputState.Disable) SDGColor.Neutral300 else SDGColor.Neutral700,
            fontSize = 16.sp,
            fontFamily = FontFamily(typeface = TypefaceConfig.normal),
            letterSpacing = 0.sp,
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
                        if (type == SDGSimpleInputType.LINE) {
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
                        IOText(
                            modifier = Modifier.then(
                                if (alignCenter) {
                                    Modifier.align(Alignment.Center)
                                } else Modifier
                            ),
                            text = hint,
                            textColor = SDGColor.Neutral300,
                            fontSize = 14.sp,
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
private fun PrevInput(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier
            .fillMaxWidth()
            .height(500.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(color = SDGColor.Neutral0)
        ) {
            SDGFixedInput(
                marginValues = PaddingValues(20.dp),
                outlineType = OutlineType.OUTLINE,
                input = "12341234",
                hint = "asdfasdf",
                inputState = InputState.Enable,
                onInputChange = {}
            )

            SDGUnderlineInput(
                marginValues = PaddingValues(20.dp),
                //input = "1234123412341234123412341234123412341234123412341234123412341234",
                input = "12341234",
                hint = "asdfasdf",
                inputState = InputState.Enable,
                onInputChange = {}
            )

            SDGSimpleInput(
                type = SDGSimpleInputType.BASIC,
                input = "start",
                backgroundColor = SDGColor.Transparent,
                hint = "hint",
                state = InputState.Enable,
                maxLines = 1,
                onInputChange = { }
            )
        }
    }
}