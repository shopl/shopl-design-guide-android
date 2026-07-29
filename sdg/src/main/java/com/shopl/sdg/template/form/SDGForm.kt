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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.dropdown.SDGDropdown
import com.shopl.sdg.component.dropdown.SDGDropdownInputField
import com.shopl.sdg.component.dropdown.SDGDropdownState
import com.shopl.sdg.component.select_input.SDGSelectInput
import com.shopl.sdg.component.select_input.SDGSelectInputField
import com.shopl.sdg.component.select_input.SDGSelectInputImage
import com.shopl.sdg.component.select_input.SDGSelectInputImageType
import com.shopl.sdg.component.select_input.SDGSelectInputState
import com.shopl.sdg.component.select_input.SDGSelectInputText
import com.shopl.sdg.component.select_input.SDGSelectInputType
import com.shopl.sdg.component.text_input.fixed.SDGFixedTextInput
import com.shopl.sdg.component.text_input.fixed.SDGFixedTextInputField
import com.shopl.sdg.component.text_input.fixed.SDGFixedTextInputState
import com.shopl.sdg.component.text_input.fixed.SDGFixedTextInputStyle
import com.shopl.sdg.component.time_select_input.SDGTimeSelectInput
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
            state = dropdownState.takeUnless {
                it == SDGDropdownState.Default && !value.isNullOrEmpty()
            } ?: SDGDropdownState.Selected,
            text = value.orEmpty(),
            placeholder = hint ?: stringResource(id = R.string.select),
            inputField = SDGDropdownInputField.LightGray,
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
            state = dropdownState.takeUnless {
                it == SDGDropdownState.Default && !value.isNullOrEmpty()
            } ?: SDGDropdownState.Selected,
            text = value.orEmpty(),
            placeholder = hint ?: stringResource(id = R.string.select),
            inputField = SDGDropdownInputField.LightGray,
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
    inputStartImage: SDGSelectInputImage?,
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
        SDGSelectInput(
            inputField = SDGSelectInputField.LightGray,
            state = selectedInputState.toSelectInputState(value),
            placeholder = hint ?: stringResource(id = R.string.select),
            type = selectInputType(
                text = value.orEmpty(),
                selectedElementImage = inputStartImage,
            ),
            onClick = onInputClick,
        )
    }
}

/**
 * 선택 텍스트 정책을 [SDGSelectInputType]에서 관리하는 API와의 하위 호환성을 위한 API입니다.
 */
@Deprecated(
    message = "inputTextOverflow 대신 SDGSelectInputType의 text를 사용하세요.",
)
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
    inputStartImage: SDGSelectInputImage?,
    inputTextOverflow: TextOverflow,
    marginValues: PaddingValues = PaddingValues(),
    onResetClick: (() -> Unit)? = null,
) {
    SDGSelectedInputForm(
        type = type,
        title = title,
        value = value,
        onInputClick = onInputClick,
        hint = hint,
        selectedInputState = selectedInputState,
        iconResId = iconResId,
        iconTint = iconTint,
        onClickIcon = onClickIcon,
        inputStartImage = inputStartImage,
        marginValues = marginValues,
        onResetClick = onResetClick,
    )
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
    inputStartImage: SDGSelectInputImage?,
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
        SDGSelectInput(
            inputField = SDGSelectInputField.LightGray,
            state = selectedInputState.toSelectInputState(value),
            placeholder = hint ?: stringResource(id = R.string.select),
            type = selectInputType(
                text = value.orEmpty(),
                selectedElementImage = inputStartImage,
            ),
            onClick = onInputClick,
        )
    }
}

/**
 * 선택 텍스트 정책을 [SDGSelectInputType]에서 관리하는 API와의 하위 호환성을 위한 API입니다.
 */
@Deprecated(
    message = "inputTextOverflow 대신 SDGSelectInputType의 text를 사용하세요.",
)
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
    inputStartImage: SDGSelectInputImage?,
    inputTextOverflow: TextOverflow,
    marginValues: PaddingValues = PaddingValues(),
    onResetClick: (() -> Unit)? = null,
) {
    SDGSelectedInputForm(
        type = type,
        title = title,
        value = value,
        onInputClick = onInputClick,
        hint = hint,
        selectedInputState = selectedInputState,
        iconResId = iconResId,
        iconTint = iconTint,
        onClickIcon = onClickIcon,
        inputStartImage = inputStartImage,
        marginValues = marginValues,
        onResetClick = onResetClick,
    )
}

/**
 * 신규 Selected Input Form API와의 하위 호환성을 위한 레거시 API입니다.
 */
@Deprecated(
    message = "inputStartIcon 대신 inputStartImage를 사용하는 SDGSelectedInputForm을 사용하세요.",
)
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
    inputTextOverflow: TextOverflow = TextOverflow.Ellipsis,
    marginValues: PaddingValues = PaddingValues(),
    onResetClick: (() -> Unit)? = null,
) {
    LegacySDGSelectedInputForm(
        type = type,
        title = AnnotatedString(text = title),
        value = value,
        onInputClick = onInputClick,
        hint = hint,
        selectedInputState = selectedInputState,
        iconResId = iconResId,
        iconTint = iconTint,
        onClickIcon = onClickIcon,
        inputStartIcon = inputStartIcon,
        inputTextOverflow = inputTextOverflow,
        marginValues = marginValues,
        onResetClick = onResetClick,
    )
}

/**
 * 신규 Selected Input Form API와의 하위 호환성을 위한 레거시 API입니다.
 */
@Deprecated(
    message = "inputStartIcon 대신 inputStartImage를 사용하는 SDGSelectedInputForm을 사용하세요.",
)
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
    inputTextOverflow: TextOverflow = TextOverflow.Ellipsis,
    marginValues: PaddingValues = PaddingValues(),
    onResetClick: (() -> Unit)? = null,
) {
    LegacySDGSelectedInputForm(
        type = type,
        title = title,
        value = value,
        onInputClick = onInputClick,
        hint = hint,
        selectedInputState = selectedInputState,
        iconResId = iconResId,
        iconTint = iconTint,
        onClickIcon = onClickIcon,
        inputStartIcon = inputStartIcon,
        inputTextOverflow = inputTextOverflow,
        marginValues = marginValues,
        onResetClick = onResetClick,
    )
}

@Suppress("DEPRECATION")
@Composable
private fun LegacySDGSelectedInputForm(
    type: SDGFormType,
    title: AnnotatedString,
    value: String?,
    onInputClick: () -> Unit,
    hint: String?,
    selectedInputState: SDGSelectInputState,
    @DrawableRes iconResId: Int?,
    iconTint: Color?,
    onClickIcon: (() -> Unit)?,
    inputStartIcon: @Composable (() -> Unit)?,
    inputTextOverflow: TextOverflow,
    marginValues: PaddingValues,
    onResetClick: (() -> Unit)?,
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
            verticalAlignment = Alignment.CenterVertically,
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
                                bottom = 3.dp,
                            ),
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
                    contentDescription = null,
                )
            }
        }
        SDGSelectInput(
            backgroundColor = SDGColor.Neutral50,
            state = selectedInputState,
            text = value,
            placeholder = hint ?: stringResource(id = R.string.select),
            onClick = onInputClick,
            icon = inputStartIcon,
            overflow = inputTextOverflow,
        )
    }
}

private fun SDGSelectInputState.toSelectInputState(
    value: String?,
): SDGSelectInputState {
    return when {
        this != SDGSelectInputState.Default -> this
        value.isNullOrEmpty() -> SDGSelectInputState.Default
        else -> SDGSelectInputState.Selected
    }
}

private fun selectInputType(
    text: String,
    selectedElementImage: SDGSelectInputImage?,
): SDGSelectInputType {
    return if (selectedElementImage == null) {
        SDGSelectInputType.Text(
            text = SDGSelectInputText.Single(value = text),
        )
    } else {
        SDGSelectInputType.OneImage(
            text = SDGSelectInputText.Single(value = text),
            image = selectedElementImage,
            type = SDGSelectInputImageType.Normal2,
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
        }
        SDGFixedTextInput(
            text = value.orEmpty(),
            placeholder = hint ?: stringResource(id = R.string.text_hint_study_place),
            state = if (value.isNullOrEmpty()) {
                SDGFixedTextInputState.Default
            } else {
                SDGFixedTextInputState.Completed
            },
            inputField = SDGFixedTextInputField.LightGray,
            style = SDGFixedTextInputStyle.Solid,
            onTextChange = onValueChange,
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
    inputField: SDGFixedTextInputField = SDGFixedTextInputField.White,
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
        }
        SDGFixedTextInput(
            text = value.orEmpty(),
            placeholder = hint ?: stringResource(id = R.string.text_hint_study_place),
            state = if (value.isNullOrEmpty()) {
                SDGFixedTextInputState.Default
            } else {
                SDGFixedTextInputState.Completed
            },
            inputField = inputField,
            style = SDGFixedTextInputStyle.Solid,
            onTextChange = onValueChange,
        )
    }
}

/**
 * 신규 Fixed Input Form API와의 하위 호환성을 위한 레거시 API입니다.
 */
@Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")
@kotlin.internal.LowPriorityInOverloadResolution
@Deprecated(
    message = "inputBackgroundColor 대신 inputField를 사용하는 SDGFixedInputForm을 사용하세요.",
)
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
    SDGFixedInputForm(
        type = type,
        title = title,
        value = value,
        onValueChange = onValueChange,
        hint = hint,
        iconResId = iconResId,
        iconTint = iconTint,
        onClickIcon = onClickIcon,
        inputField = if (inputBackgroundColor == SDGColor.Neutral50) {
            SDGFixedTextInputField.LightGray
        } else {
            SDGFixedTextInputField.White
        },
        marginValues = marginValues,
    )
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
                inputStartImage = null,
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
                inputStartImage = SDGSelectInputImage.Resource(
                    resId = R.drawable.ic_common_photo,
                ),
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
