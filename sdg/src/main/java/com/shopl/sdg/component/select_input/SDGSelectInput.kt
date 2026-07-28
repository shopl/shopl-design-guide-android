package com.shopl.sdg.component.select_input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.select_input.preview.SDGSelectInputPreviewParameter
import com.shopl.sdg.component.select_input.preview.SDGSelectInputPreviewParameterProvider
import com.shopl.sdg.component.select_input.preview.toSelectInputType
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGAsyncImage
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

private val SDGSelectInputDefaultHeight = 40.dp
private val SDGSelectInputChevronSize = 24.dp
private const val SDGSelectInputDisabledAlpha = 0.3f

/**
 * SDG - Component - Select Input
 *
 * 특정 타겟을 선택하는 인풋 컴포넌트
 *
 * @version 2.3.39
 *
 * @param placeholder 선택값이 없을 때 표시할 안내 문구
 * @param state 인풋 상태
 * @param inputField 인풋 필드 배경 유형
 * @param type Selected Element 유형과 선택 텍스트
 * @param marginValues 컴포넌트 외부 여백
 * @param onClick Input Field 클릭 이벤트
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=27047-2318&m=dev">Figma</a>
 */
@Composable
fun SDGSelectInput(
    placeholder: String,
    state: SDGSelectInputState,
    inputField: SDGSelectInputField,
    type: SDGSelectInputType,
    marginValues: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
) {
    if (type is SDGSelectInputType.TwoImage) {
        Column(
            modifier = Modifier.padding(marginValues),
            verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing10),
        ) {
            SelectInputField(
                placeholder = placeholder,
                state = type.first.state ?: state,
                inputField = inputField,
                type = type.first.toOneImageType(),
                onClick = onClick,
            )
            SelectInputField(
                placeholder = placeholder,
                state = type.second.state ?: state,
                inputField = inputField,
                type = type.second.toOneImageType(),
                onClick = onClick,
            )
        }
    } else {
        SelectInputField(
            modifier = Modifier.padding(marginValues),
            placeholder = placeholder,
            state = state,
            inputField = inputField,
            type = type,
            onClick = onClick,
        )
    }
}

@Composable
private fun SelectInputField(
    placeholder: String,
    state: SDGSelectInputState,
    inputField: SDGSelectInputField,
    type: SDGSelectInputType,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    val displayText = if (state == SDGSelectInputState.Default) {
        placeholder
    } else {
        type.text.displayText.ifEmpty { placeholder }
    }
    val textColor = when (state) {
        SDGSelectInputState.Default -> SDGColor.Neutral350
        SDGSelectInputState.Selected,
        SDGSelectInputState.Disabled,
        SDGSelectInputState.Error,
            -> SDGColor.Neutral700
    }
    val backgroundColor = when (state) {
        SDGSelectInputState.Error -> SDGColor.Red300_a10
        SDGSelectInputState.Default,
        SDGSelectInputState.Selected,
        SDGSelectInputState.Disabled,
            -> inputField.color
    }
    val chevronColor = when (state) {
        SDGSelectInputState.Disabled -> SDGColor.Neutral300
        SDGSelectInputState.Default,
        SDGSelectInputState.Selected,
        SDGSelectInputState.Error,
            -> SDGColor.Neutral700
    }

    Row(
        modifier = modifier
            .clip(shape = SDGCornerRadius.BoxRadius.Radius12)
            .background(color = backgroundColor)
            .then(
                if (state != SDGSelectInputState.Disabled && onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                },
            )
            .padding(
                horizontal = SDGSpacing.Spacing12,
                vertical = SDGSpacing.Spacing5,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing10),
    ) {
        if (state == SDGSelectInputState.Default) {
            SelectInputText(
                modifier = Modifier.weight(1f),
                text = displayText,
                textColor = textColor,
                overflow = TextOverflow.Ellipsis,
            )
        } else {
            SelectedElement(
                modifier = Modifier
                    .weight(1f)
                    .then(
                        if (state == SDGSelectInputState.Disabled) {
                            Modifier.alpha(SDGSelectInputDisabledAlpha)
                        } else {
                            Modifier
                        },
                    ),
                text = displayText,
                textColor = textColor,
                type = type,
            )
        }

        SDGImage(
            modifier = Modifier.size(SDGSelectInputChevronSize),
            resId = R.drawable.ic_common_next,
            color = chevronColor,
        )
    }
}

/**
 * 신규 Select Input API와의 하위 호환성을 위한 레거시 API입니다.
 */
@Deprecated(
    message = "SDGSelectInput의 state, inputField, type 기반 API를 사용하세요.",
)
@Composable
fun SDGSelectInput(
    text: String? = null,
    placeholder: String = stringResource(id = R.string.select),
    state: SDGSelectInputState = SDGSelectInputState.Default,
    marginValues: PaddingValues = PaddingValues(0.dp),
    icon: @Composable (() -> Unit)? = null,
    backgroundColor: Color = SDGColor.Neutral0,
    onClick: (() -> Unit)? = null,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    val background = if (state == SDGSelectInputState.Error) {
        SDGColor.Red300_a10
    } else {
        backgroundColor
    }
    val textColor = when {
        state == SDGSelectInputState.Disabled -> SDGColor.Neutral300
        !text.isNullOrEmpty() -> SDGColor.Neutral700
        else -> SDGColor.Neutral300
    }
    val chevronColor = if (state == SDGSelectInputState.Disabled) {
        SDGColor.Neutral300
    } else {
        SDGColor.Neutral700
    }

    Row(
        modifier = Modifier
            .padding(marginValues)
            .height(SDGSelectInputDefaultHeight)
            .clip(shape = SDGCornerRadius.BoxRadius.Radius12)
            .background(color = background)
            .clickable(
                hasRipple = true,
                rippleColor = SDGColor.Neutral350,
                onClick = {
                    if (state != SDGSelectInputState.Disabled) {
                        onClick?.invoke()
                    }
                },
            )
            .padding(
                horizontal = SDGSpacing.Spacing12,
                vertical = SDGSpacing.Spacing4,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing10),
    ) {
        icon?.invoke()
        SDGText(
            modifier = Modifier.weight(1f),
            text = if (!text.isNullOrEmpty()) text else placeholder,
            textColor = textColor,
            typography = SDGTypography.Body1R,
            overflow = overflow,
            maxLines = 1,
        )
        SDGImage(
            resId = R.drawable.ic_common_next,
            color = chevronColor,
        )
    }
}

@Composable
private fun SelectedElement(
    text: String,
    textColor: Color,
    type: SDGSelectInputType,
    modifier: Modifier = Modifier,
) {
    when (type) {
        is SDGSelectInputType.Text -> {
            SelectInputText(
                modifier = modifier,
                text = text,
                textColor = textColor,
                overflow = type.text.overflow,
            )
        }

        is SDGSelectInputType.Avatar -> {
            SelectedElementRow(
                modifier = modifier,
                text = text,
                textColor = textColor,
                image = type.selectedElementImage,
                imageSize = SDGSelectInputImageType.Normal1,
                clipImageToCircle = true,
                overflow = type.text.overflow,
            )
        }

        is SDGSelectInputType.OneImage -> {
            SelectedElementRow(
                modifier = modifier,
                text = text,
                textColor = textColor,
                image = type.image,
                imageSize = type.type,
                overflow = type.text.overflow,
            )
        }

        is SDGSelectInputType.TwoImage -> {
            error("TwoImage는 독립적인 One Image Input으로 렌더링되어야 합니다.")
        }
    }
}

@Composable
private fun SelectedElementRow(
    text: String,
    textColor: Color,
    image: SDGSelectInputImage,
    imageSize: SDGSelectInputImageType,
    modifier: Modifier = Modifier,
    clipImageToCircle: Boolean = false,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing10),
    ) {
        SelectedElementImage(
            imageSize = imageSize,
            image = image,
            clipToCircle = clipImageToCircle,
        )
        SelectInputText(
            modifier = Modifier.weight(1f),
            text = text,
            textColor = textColor,
            overflow = overflow,
        )
    }
}

@Composable
private fun SelectedElementImage(
    imageSize: SDGSelectInputImageType,
    image: SDGSelectInputImage,
    clipToCircle: Boolean = false,
) {
    val imageModifier = Modifier
        .size(
            width = imageSize.width,
            height = imageSize.height,
        )
        .then(
            if (clipToCircle) {
                Modifier.clip(CircleShape)
            } else {
                Modifier
            },
        )
    val contentScale = if (clipToCircle) {
        ContentScale.Crop
    } else {
        ContentScale.Fit
    }

    when (image) {
        is SDGSelectInputImage.Resource -> {
            SDGImage(
                modifier = imageModifier,
                resId = image.resId,
                color = null,
                contentScale = contentScale,
            )
        }

        is SDGSelectInputImage.Url -> {
            SDGAsyncImage(
                modifier = imageModifier,
                imageModel = image.url,
                contentScale = contentScale,
                failureImage = {
                    image.failureImageResId?.let { resId ->
                        SDGImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .then(
                                    if (clipToCircle) {
                                        Modifier.clip(CircleShape)
                                    } else {
                                        Modifier
                                    },
                                ),
                            resId = resId,
                            color = null,
                            contentScale = contentScale,
                        )
                    }
                },
                previewContent = {
                    SDGImage(
                        modifier = imageModifier,
                        resId = image.failureImageResId ?: R.drawable.ic_common_photo,
                        color = null,
                        contentScale = contentScale,
                    )
                },
            )
        }
    }
}

@Composable
private fun SelectInputText(
    text: String,
    textColor: Color,
    modifier: Modifier = Modifier,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    SDGText(
        modifier = modifier,
        text = text,
        textColor = textColor,
        typography = SDGTypography.Body1R,
        overflow = overflow,
        maxLines = 1,
    )
}

@Preview(
    showBackground = true,
    widthDp = 375,
)
@Composable
private fun PreviewSDGSelectInput(
    @PreviewParameter(SDGSelectInputPreviewParameterProvider::class)
    parameter: SDGSelectInputPreviewParameter,
) {
    with(parameter) {
        val selectedElementType = parameter.toSelectInputType()

        SDGSelectInput(
            placeholder = placeholder,
            state = state,
            inputField = inputField,
            type = selectedElementType,
            marginValues = PaddingValues(SDGSpacing.Spacing20),
        )
    }
}

/** [SDGSelectInputImageElement]를 독립적인 One Image Input 유형으로 변환합니다. */
private fun SDGSelectInputImageElement.toOneImageType(): SDGSelectInputType.OneImage {
    return SDGSelectInputType.OneImage(
        text = text,
        image = image,
        type = type,
    )
}
