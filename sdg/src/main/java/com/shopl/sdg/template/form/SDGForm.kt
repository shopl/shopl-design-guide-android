package com.shopl.sdg.template.form

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shopl.sdg.component.dropdown.SDGBasicDropdown
import com.shopl.sdg.component.dropdown.SDGBasicDropdownState
import com.shopl.sdg.component.text_input.InputState
import com.shopl.sdg.component.text_input.SDGFixedInput
import com.shopl.sdg.template.selected_input.SDGSelectedInputSingle
import com.shopl.sdg.template.selected_input.SDGSelectedInputState
import com.shopl.sdg.template.selected_input.SDGSelectedInputTime
import com.shopl.sdg_common.enums.OutlineType
import com.shopl.sdg_common.ext.bounceClickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.IOText
import com.shopl.sdg_common.ui.components.IOTypeface
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

@Stable
enum class SDGFormType {
    EMPHA,
    NORMAL
}

@Composable
fun SDGDropdownForm(
    type: SDGFormType,
    title: String,
    hint: String? = null,
    value: String?,
    dropdownState: SDGBasicDropdownState = SDGBasicDropdownState.Default,
    @DrawableRes iconResId: Int? = null,
    iconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
    onDropdownClick: () -> Unit,
    onResetClick: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(marginValues)
                .fillMaxWidth()
                .heightIn(min = 28.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IOText(
                    text = title,
                    textColor = SDGColor.Neutral700,
                    fontSize = 16.sp,
                    typeface = when (type) {
                        SDGFormType.EMPHA -> IOTypeface.SEMI_BOLD
                        SDGFormType.NORMAL -> IOTypeface.REGULAR
                    },
                )
                iconResId?.let {
                    SDGImage(
                        resId = it,
                        color = iconTint,
                    )
                }
            }
            if (value != null && onResetClick != null) {
                Image(
                    modifier = Modifier
                        .bounceClickable { onResetClick() }
                        .background(
                            color = SDGColor.Neutral50,
                            shape = CircleShape,
                        )
                        .padding(2.dp)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_common_refresh),
                    colorFilter = ColorFilter.tint(SDGColor.Neutral400),
                    contentDescription = null
                )
            }
        }
        SDGBasicDropdown(
            backgroundColor = SDGColor.Neutral50,
            state = dropdownState,
            text = value ?: hint ?: stringResource(id = R.string.select),
            enable = true,
            hasSelectedItem = value != null,
            onClick = onDropdownClick,
        )
    }
}

@Composable
fun SDGDropdownForm(
    type: SDGFormType,
    title: AnnotatedString,
    hint: String? = null,
    value: String?,
    dropdownState: SDGBasicDropdownState = SDGBasicDropdownState.Default,
    @DrawableRes iconResId: Int? = null,
    iconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
    onDropdownClick: () -> Unit,
    onResetClick: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(marginValues)
                .fillMaxWidth()
                .heightIn(min = 28.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SDGText(
                    text = title,
                    textColor = SDGColor.Neutral700,
                    typography = when (type) {
                        SDGFormType.EMPHA -> SDGTypography.Body1SB
                        SDGFormType.NORMAL -> SDGTypography.Body1R
                    },
                )
                iconResId?.let {
                    SDGImage(
                        resId = it,
                        color = iconTint,
                    )
                }
            }
            if (value != null && onResetClick != null) {
                Image(
                    modifier = Modifier
                        .bounceClickable { onResetClick() }
                        .background(
                            color = SDGColor.Neutral50,
                            shape = CircleShape,
                        )
                        .padding(2.dp)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_common_refresh),
                    colorFilter = ColorFilter.tint(SDGColor.Neutral400),
                    contentDescription = null
                )
            }
        }
        SDGBasicDropdown(
            backgroundColor = SDGColor.Neutral50,
            state = dropdownState,
            text = value ?: hint ?: stringResource(id = R.string.select),
            enable = true,
            hasSelectedItem = value != null,
            onClick = onDropdownClick,
        )
    }
}

@Composable
fun SDGSelectedInputForm(
    type: SDGFormType,
    title: String,
    hint: String? = null,
    value: String?,
    selectedInputState: SDGSelectedInputState = SDGSelectedInputState.Default,
    @DrawableRes iconResId: Int? = null,
    iconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
    onInputClick: () -> Unit,
    onResetClick: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(marginValues)
                .fillMaxWidth()
                .heightIn(min = 28.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IOText(
                    text = title,
                    textColor = SDGColor.Neutral700,
                    fontSize = 16.sp,
                    typeface = when (type) {
                        SDGFormType.EMPHA -> IOTypeface.SEMI_BOLD
                        SDGFormType.NORMAL -> IOTypeface.REGULAR
                    },
                )
                iconResId?.let {
                    SDGImage(
                        resId = it,
                        color = iconTint,
                    )
                }
            }

            if (value != null && onResetClick != null) {
                Image(
                    modifier = Modifier
                        .bounceClickable { onResetClick() }
                        .background(
                            color = SDGColor.Neutral50,
                            shape = CircleShape,
                        )
                        .padding(2.dp)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_common_refresh),
                    colorFilter = ColorFilter.tint(SDGColor.Neutral400),
                    contentDescription = null
                )
            }
        }
        SDGSelectedInputSingle(
            backgroundColor = SDGColor.Neutral50,
            state = selectedInputState,
            text = value ?: hint ?: stringResource(id = R.string.select),
            enable = true,
            hasSelectedItem = value != null,
            onClick = onInputClick,
        )
    }
}

@Composable
fun SDGSelectedInputForm(
    type: SDGFormType,
    title: AnnotatedString,
    hint: String? = null,
    value: String?,
    selectedInputState: SDGSelectedInputState = SDGSelectedInputState.Default,
    @DrawableRes iconResId: Int? = null,
    iconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
    onInputClick: () -> Unit,
    onResetClick: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(marginValues)
                .fillMaxWidth()
                .heightIn(min = 28.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IOText(
                    text = title,
                    textColor = SDGColor.Neutral700,
                    fontSize = 16.sp,
                    typeface = when (type) {
                        SDGFormType.EMPHA -> IOTypeface.SEMI_BOLD
                        SDGFormType.NORMAL -> IOTypeface.REGULAR
                    },
                )
                iconResId?.let {
                    SDGImage(
                        resId = it,
                        color = iconTint,
                    )
                }
            }

            if (value != null && onResetClick != null) {
                Image(
                    modifier = Modifier
                        .bounceClickable { onResetClick() }
                        .background(
                            color = SDGColor.Neutral50,
                            shape = CircleShape,
                        )
                        .padding(2.dp)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_common_refresh),
                    colorFilter = ColorFilter.tint(SDGColor.Neutral400),
                    contentDescription = null
                )
            }
        }
        SDGSelectedInputSingle(
            backgroundColor = SDGColor.Neutral50,
            state = selectedInputState,
            text = value ?: hint ?: stringResource(id = R.string.select),
            enable = true,
            hasSelectedItem = value != null,
            onClick = onInputClick,
        )
    }
}

@Composable
fun SDGFixedInputForm(
    type: SDGFormType,
    title: AnnotatedString,
    hint: String? = null,
    value: String?,
    @DrawableRes iconResId: Int? = null,
    iconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(marginValues)
                .fillMaxWidth()
                .heightIn(min = 28.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IOText(
                    text = title,
                    textColor = SDGColor.Neutral700,
                    fontSize = 16.sp,
                    typeface = when (type) {
                        SDGFormType.EMPHA -> IOTypeface.SEMI_BOLD
                        SDGFormType.NORMAL -> IOTypeface.REGULAR
                    },
                )
                iconResId?.let {
                    SDGImage(
                        resId = it,
                        color = iconTint,
                    )
                }
            }
        }
        SDGFixedInput(
            outlineType = OutlineType.BASIC,
            input = value,
            hint = hint ?: stringResource(id = R.string.text_hint_study_place),
            inputState = InputState.Enable,
            onInputChange = onValueChange,
            backgroundColor = SDGColor.Neutral50,
        )
    }
}

@Composable
fun SDGFixedInputForm(
    type: SDGFormType,
    title: String,
    hint: String? = null,
    value: String?,
    @DrawableRes iconResId: Int? = null,
    iconTint: Color? = null,
    inputBackgroundColor: Color = SDGColor.Neutral0,
    marginValues: PaddingValues = PaddingValues(),
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(marginValues)
                .fillMaxWidth()
                .heightIn(min = 28.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IOText(
                    text = title,
                    textColor = SDGColor.Neutral700,
                    fontSize = 16.sp,
                    typeface = when (type) {
                        SDGFormType.EMPHA -> IOTypeface.SEMI_BOLD
                        SDGFormType.NORMAL -> IOTypeface.REGULAR
                    },
                )
                iconResId?.let {
                    SDGImage(
                        resId = it,
                        color = iconTint,
                    )
                }
            }
        }
        SDGFixedInput(
            outlineType = OutlineType.BASIC,
            input = value,
            hint = hint ?: stringResource(id = R.string.text_hint_study_place),
            backgroundColor = inputBackgroundColor,
            inputState = InputState.Enable,
            onInputChange = onValueChange,
        )
    }
}

@Composable
fun SDGTimeSelectedForm(
    type: SDGFormType,
    title: String,
    startTime: String?,
    endTime: String?,
    @DrawableRes iconResId: Int? = null,
    iconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
    onTimeSelectClick: (isStart: Boolean) -> Unit,
    onResetClick: (() -> Unit)? = null,
) {
    SDGTimeSelectedForm(
        type = type,
        title = AnnotatedString(text = title),
        startTime = startTime,
        endTime = endTime,
        iconResId = iconResId,
        iconTint = iconTint,
        marginValues = marginValues,
        onTimeSelectClick = onTimeSelectClick,
        onResetClick = onResetClick,
    )
}

@Composable
fun SDGTimeSelectedForm(
    type: SDGFormType,
    title: AnnotatedString,
    startTime: String?,
    endTime: String?,
    @DrawableRes iconResId: Int? = null,
    iconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
    onTimeSelectClick: (isClickStart: Boolean) -> Unit,
    onResetClick: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(marginValues)
                .fillMaxWidth()
                .heightIn(min = 28.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IOText(
                    text = title,
                    textColor = SDGColor.Neutral700,
                    fontSize = 16.sp,
                    typeface = when (type) {
                        SDGFormType.EMPHA -> IOTypeface.SEMI_BOLD
                        SDGFormType.NORMAL -> IOTypeface.REGULAR
                    },
                )
                iconResId?.let {
                    SDGImage(
                        resId = it,
                        color = iconTint,
                    )
                }
            }
            if ((startTime != null || endTime != null) && onResetClick != null) {
                Image(
                    modifier = Modifier
                        .bounceClickable { onResetClick() }
                        .background(
                            color = SDGColor.Neutral50,
                            shape = CircleShape,
                        )
                        .padding(2.dp)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_common_refresh),
                    colorFilter = ColorFilter.tint(SDGColor.Neutral400),
                    contentDescription = null
                )
            }
        }
        SDGSelectedInputTime(
            startTime = startTime,
            endTime = endTime,
            backgroundColor = SDGColor.Neutral50,
            onClick = onTimeSelectClick
        )
    }
}

@Preview
@Composable
private fun PrevForm(
    modifier: Modifier = Modifier,
) {
    Surface(
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SDGDropdownForm(
                type = SDGFormType.NORMAL,
                title = "SDGDropdownForm",
                hint = null,
                value = null,
                iconResId = null,
                iconTint = null,
                onDropdownClick = {},
                onResetClick = {},
            )
            SDGSelectedInputForm(
                type = SDGFormType.NORMAL,
                title = "SDGSelectedInputForm",
                hint = null,
                value = null,
                iconResId = null,
                iconTint = null,
                onInputClick = {},
                onResetClick = {},
            )
            SDGFixedInputForm(
                type = SDGFormType.NORMAL,
                title = "SDGFixedInputForm",
                value = "",
                onValueChange = {}
            )
            SDGTimeSelectedForm(
                type = SDGFormType.NORMAL,
                title = AnnotatedString("SDGTimeSelectedForm"),
                startTime = null,
                endTime = null,
                onTimeSelectClick = {},
            )
        }
    }
}