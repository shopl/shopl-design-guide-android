package com.shopl.sdg.component.search_bar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shopl.sdg_common.enums.Keyboard
import com.shopl.sdg_common.enums.OutlineType
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.IOText
import com.shopl.sdg_common.ui.components.TypefaceConfig
import com.shopl.sdg_common.util.keyboardAsState
import com.shopl.sdg_resource.R

@Deprecated("레거시 Box Search", replaceWith = ReplaceWith("SDGBoxSearch"))
@Composable
fun SDGBasicBoxSearch(
    outlineType: OutlineType,
    input: String,
    hint: String,
    enabled: Boolean,
    backgroundColor: Color = SDGColor.Neutral0,
    outlineColor: Color = SDGColor.Neutral200,
    marginValues: PaddingValues = PaddingValues(),
    onInputChange: (String) -> Unit,
    onDeleteClick: () -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    BasicTextField(
        modifier = Modifier,
        value = input,
        onValueChange = onInputChange,
        enabled = enabled,
        textStyle = TextStyle(
            color = SDGColor.Neutral700,
            fontSize = 14.sp,
            fontFamily = FontFamily(typeface = TypefaceConfig.normal),
            letterSpacing = 0.sp,
        ),
        singleLine = true,
        maxLines = 1,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(SDGColor.Neutral700),
        decorationBox = { innerTextField ->
            Row(
                Modifier
                    .padding(marginValues)
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .border(
                        width = 1.dp,
                        brush = if (outlineType == OutlineType.OUTLINE) SolidColor(outlineColor)
                        else
                            SolidColor(SDGColor.Transparent),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(vertical = 10.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(14.dp),
                    painter = painterResource(id = R.drawable.ic_common_search),
                    colorFilter = ColorFilter.tint(SDGColor.Neutral300),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Box(Modifier.weight(1f)) {
                    if (input.isEmpty()) {
                        IOText(text = hint, textColor = SDGColor.Neutral300, fontSize = 14.sp)
                    }
                    innerTextField()
                }
                Spacer(modifier = Modifier.width(12.dp))
                if (input.isNotEmpty()) {
                    Image(
                        modifier = Modifier
                            .size(14.dp)
                            .clickable(false) { onDeleteClick() },
                        painter = painterResource(id = R.drawable.ic_input_delete),
                        contentDescription = null
                    )
                }
            }
        },
    )
}


@Composable
fun SDGBasicRoundSearch(
    outlineType: OutlineType,
    input: String,
    hint: String,
    enabled: Boolean,
    isFillMaxWidth: Boolean = true,
    hintIconResId: Int? = null,
    onInputChange: (String) -> Unit,
    onDeleteClick: () -> Unit,
    backgroundColor: Color = SDGColor.Neutral0,
    outlineColor: Color = SDGColor.Neutral200,
    marginValues: PaddingValues = PaddingValues(),
    onHintIconClick: (() -> Unit)? = null,
    useStartRequester: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()
    var skipFirstEvent by remember { mutableStateOf(useStartRequester) }

    LaunchedEffect(key1 = Unit) {
        if (useStartRequester) {
            focusRequester.requestFocus()
        }
    }

    LaunchedEffect(key1 = isKeyboardOpen) {
        if (isKeyboardOpen == Keyboard.Closed && !skipFirstEvent) {
            focusManager.clearFocus()
        }
        skipFirstEvent = false
    }

    BasicTextField(
        modifier = Modifier.focusRequester(focusRequester),
        value = input,
        onValueChange = onInputChange,
        enabled = enabled,
        textStyle = TextStyle(
            color = SDGColor.Neutral700,
            fontSize = 14.sp,
            fontFamily = FontFamily(typeface = TypefaceConfig.normal),
            letterSpacing = 0.sp,
        ),
        singleLine = true,
        maxLines = 1,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(SDGColor.Neutral700),
        decorationBox = { innerTextField ->
            Row(
                Modifier
                    .padding(marginValues)
                    .then(
                        if (isFillMaxWidth) {
                            Modifier.fillMaxWidth()
                        } else {
                            Modifier
                        }
                    )
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .border(
                        width = 1.dp,
                        brush = if (outlineType == OutlineType.OUTLINE) SolidColor(outlineColor)
                        else
                            SolidColor(Color.Transparent),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(vertical = 10.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                hintIconResId?.let {
                    Image(
                        modifier = Modifier
                            .size(20.dp)
                            .clickable(hasRipple = false) {
                                onHintIconClick?.invoke()
                            },
                        painter = painterResource(id = it),
                        colorFilter = ColorFilter.tint(SDGColor.Neutral700),
                        contentDescription = null
                    )
                    Image(
                        modifier = Modifier
                            .size(20.dp)
                            .clickable(hasRipple = false) {
                                onHintIconClick?.invoke()
                            },
                        painter = painterResource(id = R.drawable.ic_common_dropdown),
                        colorFilter = ColorFilter.tint(SDGColor.Neutral700),
                        contentDescription = null
                    )
                } ?: run {
                    Image(
                        modifier = Modifier.size(14.dp),
                        painter = painterResource(id = R.drawable.ic_common_search),
                        colorFilter = ColorFilter.tint(SDGColor.Neutral300),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Box(Modifier.weight(1f)) {
                    if (input.isEmpty()) {
                        IOText(text = hint, textColor = SDGColor.Neutral300, fontSize = 14.sp)
                    }
                    innerTextField()
                }
                Spacer(modifier = Modifier.width(12.dp))
                if (input.isNotEmpty()) {
                    Image(
                        modifier = Modifier
                            .size(18.dp)
                            .clickable(false) { onDeleteClick() },
                        painter = painterResource(id = R.drawable.ic_input_delete),
                        contentDescription = null
                    )
                }
            }
        },
    )
}

@Composable
fun SDGBasicRoundSearch(
    isFillMaxWidth: Boolean = true,
    outlineType: OutlineType,
    input: TextFieldValue,
    hintIconResId: Int? = null,
    hint: String,
    enabled: Boolean,
    backgroundColor: Color = SDGColor.Neutral0,
    outlineColor: Color = SDGColor.Neutral200,
    marginValues: PaddingValues = PaddingValues(),
    onInputChange: (TextFieldValue) -> Unit,
    onDeleteClick: () -> Unit,
    onHintIconClick: (() -> Unit)? = null,
    useStartRequester: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()
    var skipFirstEvent by remember { mutableStateOf(useStartRequester) }

    LaunchedEffect(key1 = Unit) {
        if (useStartRequester) {
            focusRequester.requestFocus()
        }
    }

    LaunchedEffect(key1 = isKeyboardOpen) {
        if (isKeyboardOpen == Keyboard.Closed && !skipFirstEvent) {
            focusManager.clearFocus()
        }
        skipFirstEvent = false
    }

    BasicTextField(
        modifier = Modifier.focusRequester(focusRequester),
        value = input,
        onValueChange = onInputChange,
        enabled = enabled,
        textStyle = TextStyle(
            color = SDGColor.Neutral700,
            fontSize = 14.sp,
            fontFamily = FontFamily(typeface = TypefaceConfig.normal),
            letterSpacing = 0.sp,
        ),
        singleLine = true,
        maxLines = 1,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(SDGColor.Neutral700),
        decorationBox = { innerTextField ->
            Row(
                Modifier
                    .padding(marginValues)
                    .then(
                        if (isFillMaxWidth) {
                            Modifier.fillMaxWidth()
                        } else {
                            Modifier
                        }
                    )
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .border(
                        width = 1.dp,
                        brush = if (outlineType == OutlineType.OUTLINE) SolidColor(outlineColor)
                        else
                            SolidColor(Color.Transparent),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(vertical = 10.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                hintIconResId?.let {
                    Image(
                        modifier = Modifier
                            .size(20.dp)
                            .clickable(hasRipple = false) {
                                onHintIconClick?.invoke()
                            },
                        painter = painterResource(id = it),
                        colorFilter = ColorFilter.tint(SDGColor.Neutral700),
                        contentDescription = null
                    )
                    Image(
                        modifier = Modifier
                            .size(20.dp)
                            .clickable(hasRipple = false) {
                                onHintIconClick?.invoke()
                            },
                        painter = painterResource(id = R.drawable.ic_common_dropdown),
                        colorFilter = ColorFilter.tint(SDGColor.Neutral700),
                        contentDescription = null
                    )
                } ?: run {
                    Image(
                        modifier = Modifier.size(14.dp),
                        painter = painterResource(id = R.drawable.ic_common_search),
                        colorFilter = ColorFilter.tint(SDGColor.Neutral300),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Box(Modifier.weight(1f)) {
                    if (input.text.isEmpty()) {
                        IOText(text = hint, textColor = SDGColor.Neutral300, fontSize = 14.sp)
                    }
                    innerTextField()
                }
                Spacer(modifier = Modifier.width(12.dp))
                if (input.text.isNotEmpty()) {
                    Image(
                        modifier = Modifier
                            .size(14.dp)
                            .clickable(false) { onDeleteClick() },
                        painter = painterResource(id = R.drawable.ic_input_delete),
                        contentDescription = null
                    )
                }
            }
        },
    )
}

@Composable
fun RowScope.SDGBasicRoundSearch(
    weight: Float,
    isFillMaxWidth: Boolean = true,
    outlineType: OutlineType,
    input: String,
    hintIconResId: Int? = null,
    hint: String,
    enabled: Boolean,
    backgroundColor: Color = SDGColor.Neutral0,
    outlineColor: Color = SDGColor.Neutral200,
    marginValues: PaddingValues = PaddingValues(),
    onInputChange: (String) -> Unit,
    onDeleteClick: () -> Unit,
    onHintIconClick: (() -> Unit)? = null,
    useStartRequester: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Box(Modifier.weight(weight)) {
        SDGBasicRoundSearch(
            isFillMaxWidth = isFillMaxWidth,
            outlineType = outlineType,
            input = input,
            hintIconResId = hintIconResId,
            hint = hint,
            enabled = enabled,
            backgroundColor = backgroundColor,
            outlineColor = outlineColor,
            marginValues = marginValues,
            onInputChange = onInputChange,
            onDeleteClick = onDeleteClick,
            onHintIconClick = onHintIconClick,
            useStartRequester = useStartRequester,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        )
    }
}

@Composable
fun RowScope.SDGBasicRoundSearch(
    weight: Float,
    isFillMaxWidth: Boolean = true,
    outlineType: OutlineType,
    input: TextFieldValue,
    hintIconResId: Int? = null,
    hint: String,
    enabled: Boolean,
    backgroundColor: Color = SDGColor.Neutral0,
    outlineColor: Color = SDGColor.Neutral200,
    marginValues: PaddingValues = PaddingValues(),
    onInputChange: (TextFieldValue) -> Unit,
    onDeleteClick: () -> Unit,
    onHintIconClick: (() -> Unit)? = null,
    useStartRequester: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Box(Modifier.weight(weight)) {
        SDGBasicRoundSearch(
            isFillMaxWidth = isFillMaxWidth,
            outlineType = outlineType,
            input = input,
            hintIconResId = hintIconResId,
            hint = hint,
            enabled = enabled,
            backgroundColor = backgroundColor,
            outlineColor = outlineColor,
            marginValues = marginValues,
            onInputChange = onInputChange,
            onDeleteClick = onDeleteClick,
            onHintIconClick = onHintIconClick,
            useStartRequester = useStartRequester,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        )
    }
}


@Preview
@Composable
fun PrevSearch() {
    var text by remember { mutableStateOf("") }
    Surface {
        Column(
            Modifier
                .background(color = SDGColor.Neutral100)
                .padding(30.dp)
        ) {
            SDGBasicBoxSearch(
                outlineType = OutlineType.BASIC,
                input = text,
                hint = "hint",
                onInputChange = { text = it },
                onDeleteClick = { text = "" },
                enabled = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            SDGBasicBoxSearch(
                outlineType = OutlineType.OUTLINE,
                input = text,
                hint = "hint",
                onInputChange = { text = it },
                onDeleteClick = { text = "" },
                enabled = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            SDGBasicRoundSearch(
                outlineType = OutlineType.BASIC,
                input = text,
                hint = "hint",
                onInputChange = { text = it },
                onDeleteClick = { text = "" },
                enabled = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            SDGBasicRoundSearch(
                outlineType = OutlineType.OUTLINE,
                input = text,
                hint = "hint",
                onInputChange = { text = it },
                onDeleteClick = { text = "" },
                enabled = true
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}