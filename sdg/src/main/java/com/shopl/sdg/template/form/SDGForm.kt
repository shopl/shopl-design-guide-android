package com.shopl.sdg.template.form

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.shopl.sdg.component.dropdown.SDGDropdown
import com.shopl.sdg.component.dropdown.SDGDropdownState
import com.shopl.sdg.component.select_input.SDGSelectInput
import com.shopl.sdg.component.select_input.SDGSelectInputState
import com.shopl.sdg.component.text_input.InputState
import com.shopl.sdg.component.text_input.fixed.SDGFixedTextInput
import com.shopl.sdg.component.time_select_input.SDGTimeSelectInput
import com.shopl.sdg_common.enums.OutlineType
import com.shopl.sdg_common.ext.bounceClickable
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
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
    value: String?,
    onDropdownClick: () -> Unit,
    hint: String? = null,
    dropdownState: SDGDropdownState = SDGDropdownState.Default,
    @DrawableRes iconResId: Int? = null,
    iconTint: Color? = null,
    onClickIcon: (() -> Unit)? = null,
    marginValues: PaddingValues = PaddingValues(),
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
                    typography = when(type) {
                        SDGFormType.EMPHA -> SDGTypography.Body1SB
                        SDGFormType.NORMAL -> SDGTypography.Body1R
                    },
                )
                iconResId?.let {
                    Box(
                        modifier = Modifier
                            .clickable(hasRipple = false) {
                                onClickIcon?.invoke()
                            }
                            .width(26.dp)
                            .height(20.dp)
                            .padding(
                                start = 4.dp,
                                top = 3.dp,
                                end = 8.dp,
                                bottom = 3.dp
                            )
                    ) {
                        SDGImage(
                            resId = it,
                            color = iconTint,
                        )
                    }
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
        SDGDropdown(
            backgroundColor = SDGColor.Neutral50,
            state = dropdownState,
            text = value,
            placeholder = hint ?: stringResource(id = R.string.select),
            onClick = onDropdownClick,
        )
    }
}

@Composable
fun SDGDropdownForm(
    type: SDGFormType,
    title: AnnotatedString,
    value: String?,
    onDropdownClick: () -> Unit,
    hint: String? = null,
    dropdownState: SDGDropdownState = SDGDropdownState.Default,
    @DrawableRes iconResId: Int? = null,
    iconTint: Color? = null,
    onClickIcon: (() -> Unit)? = null,
    marginValues: PaddingValues = PaddingValues(),
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
                    Box(
                        modifier = Modifier
                            .clickable(hasRipple = false) {
                                onClickIcon?.invoke()
                            }
                            .width(26.dp)
                            .height(20.dp)
                            .padding(
                                start = 4.dp,
                                top = 3.dp,
                                end = 8.dp,
                                bottom = 3.dp
                            )
                    ) {
                        SDGImage(
                            resId = it,
                            color = iconTint,
                        )
                    }
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
        SDGDropdown(
            backgroundColor = SDGColor.Neutral50,
            state = dropdownState,
            text = value,
            placeholder = hint ?: stringResource(id = R.string.select),
            onClick = onDropdownClick,
        )
    }
}

@Composable
fun SDGSelectedInputForm(
    type: SDGFormType,
    title: String,
    value: String?,
    onInputClick: () -> Unit,
    hint: String? = null,
    selectedInputState: SDGSelectInputState = SDGSelectInputState.Default,
    @DrawableRes iconResId: Int? = null,
    iconTint: Color? = null,
    onClickIcon: (() -> Unit)? = null,
    inputStartIcon: @Composable (() -> Unit)? = null,
    marginValues: PaddingValues = PaddingValues(),
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
                    typography = when(type) {
                        SDGFormType.EMPHA -> SDGTypography.Body1SB
                        SDGFormType.NORMAL -> SDGTypography.Body1R
                    },
                )
                iconResId?.let {
                    Box(
                        modifier = Modifier
                            .clickable(hasRipple = false) {
                                onClickIcon?.invoke()
                            }
                            .width(26.dp)
                            .height(20.dp)
                            .padding(
                                start = 4.dp,
                                top = 3.dp,
                                end = 8.dp,
                                bottom = 3.dp
                            )
                    ) {
                        SDGImage(
                            resId = it,
                            color = iconTint,
                        )
                    }
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
        SDGSelectInput(
            backgroundColor = SDGColor.Neutral50,
            state = selectedInputState,
            text = value,
            placeholder = hint ?: stringResource(id = R.string.select),
            onClick = onInputClick,
            icon = inputStartIcon
        )
    }
}

@Composable
fun SDGSelectedInputForm(
    type: SDGFormType,
    title: AnnotatedString,
    value: String?,
    onInputClick: () -> Unit,
    hint: String? = null,
    selectedInputState: SDGSelectInputState = SDGSelectInputState.Default,
    @DrawableRes iconResId: Int? = null,
    iconTint: Color? = null,
    onClickIcon: (() -> Unit)? = null,
    inputStartIcon: @Composable (() -> Unit)? = null,
    marginValues: PaddingValues = PaddingValues(),
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
                    typography = when(type) {
                        SDGFormType.EMPHA -> SDGTypography.Body1SB
                        SDGFormType.NORMAL -> SDGTypography.Body1R
                    },
                )
                iconResId?.let {
                    Box(
                        modifier = Modifier
                            .clickable(hasRipple = false) {
                                onClickIcon?.invoke()
                            }
                            .width(26.dp)
                            .height(20.dp)
                            .padding(
                                start = 4.dp,
                                top = 3.dp,
                                end = 8.dp,
                                bottom = 3.dp
                            )
                    ) {
                        SDGImage(
                            resId = it,
                            color = iconTint,
                        )
                    }
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
        SDGSelectInput(
            backgroundColor = SDGColor.Neutral50,
            state = selectedInputState,
            text = value,
            placeholder = hint ?: stringResource(id = R.string.select),
            onClick = onInputClick,
            icon = inputStartIcon
        )
    }
}

@Composable
fun SDGFixedInputForm(
    type: SDGFormType,
    title: AnnotatedString,
    value: String?,
    onValueChange: (String) -> Unit,
    hint: String? = null,
    @DrawableRes iconResId: Int? = null,
    iconTint: Color? = null,
    onClickIcon: (() -> Unit)? = null,
    marginValues: PaddingValues = PaddingValues(),
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
                    typography = when(type) {
                        SDGFormType.EMPHA -> SDGTypography.Body1SB
                        SDGFormType.NORMAL -> SDGTypography.Body1R
                    },
                )
                iconResId?.let {
                    Box(
                        modifier = Modifier
                            .clickable(hasRipple = false) {
                                onClickIcon?.invoke()
                            }
                            .width(26.dp)
                            .height(20.dp)
                            .padding(
                                start = 4.dp,
                                top = 3.dp,
                                end = 8.dp,
                                bottom = 3.dp
                            )
                    ) {
                        SDGImage(
                            resId = it,
                            color = iconTint,
                        )
                    }
                }
            }
        }
        SDGFixedTextInput(
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
    value: String?,
    onValueChange: (String) -> Unit,
    hint: String? = null,
    @DrawableRes iconResId: Int? = null,
    iconTint: Color? = null,
    onClickIcon: (() -> Unit)? = null,
    inputBackgroundColor: Color = SDGColor.Neutral0,
    marginValues: PaddingValues = PaddingValues(),
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
                    typography = when(type) {
                        SDGFormType.EMPHA -> SDGTypography.Body1SB
                        SDGFormType.NORMAL -> SDGTypography.Body1R
                    },
                )
                iconResId?.let {
                    Box(
                        modifier = Modifier
                            .clickable(hasRipple = false) {
                                onClickIcon?.invoke()
                            }
                            .width(26.dp)
                            .height(20.dp)
                            .padding(
                                start = 4.dp,
                                top = 3.dp,
                                end = 8.dp,
                                bottom = 3.dp
                            )
                    ) {
                        SDGImage(
                            resId = it,
                            color = iconTint,
                        )
                    }
                }
            }
        }
        SDGFixedTextInput(
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
    onResetClick: (() -> Unit)? = null,
    @DrawableRes iconResId: Int? = null,
    iconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
    onTimeSelectClick: (isStart: Boolean) -> Unit,
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
    onTimeSelectClick: (isClickStart: Boolean) -> Unit,
    @DrawableRes iconResId: Int? = null,
    iconTint: Color? = null,
    onClickIcon: (() -> Unit)? = null,
    marginValues: PaddingValues = PaddingValues(),
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
                    typography = when(type) {
                        SDGFormType.EMPHA -> SDGTypography.Body1SB
                        SDGFormType.NORMAL -> SDGTypography.Body1R
                    },
                )
                iconResId?.let {
                    Box(
                        modifier = Modifier
                            .clickable(hasRipple = false) {
                                onClickIcon?.invoke()
                            }
                            .width(26.dp)
                            .height(20.dp)
                            .padding(
                                start = 4.dp,
                                top = 3.dp,
                                end = 8.dp,
                                bottom = 3.dp
                            )
                    ) {
                        SDGImage(
                            resId = it,
                            color = iconTint,
                        )
                    }
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
        SDGTimeSelectInput(
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
                iconResId = R.drawable.ic_clip,
                iconTint = null,
                onDropdownClick = {},
                onResetClick = {},
            )
            SDGSelectedInputForm(
                type = SDGFormType.NORMAL,
                title = "SDGSelectedInputForm 1",
                hint = null,
                value = null,
                iconResId = null,
                iconTint = null,
                onInputClick = {},
                onResetClick = {},
            )
            SDGSelectedInputForm(
                type = SDGFormType.NORMAL,
                title = "SDGSelectedInputForm 2",
                hint = null,
                value = null,
                iconResId = R.drawable.ic_clip,
                iconTint = null,
                onInputClick = {},
                onResetClick = {},
                inputStartIcon = {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(SDGColor.Neutral400)
                    )
                }
            )
            SDGFixedInputForm(
                type = SDGFormType.NORMAL,
                title = "SDGFixedInputForm",
                value = "",
                iconResId = R.drawable.ic_clip,
                onValueChange = {}
            )
            SDGTimeSelectedForm(
                type = SDGFormType.NORMAL,
                title = AnnotatedString("SDGTimeSelectedForm"),
                startTime = null,
                endTime = null,
                iconResId = R.drawable.ic_clip,
                onTimeSelectClick = {},
            )
        }
    }
}