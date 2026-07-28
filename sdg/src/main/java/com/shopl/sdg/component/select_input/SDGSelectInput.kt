package com.shopl.sdg.component.select_input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.shopl.sdg.component.select_input.preview.SDGSelectInputPreviewType
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
 * @param overflow 단일 선택은 [TextOverflow.Ellipsis], 다중 선택은 [TextOverflow.MiddleEllipsis] 사용
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
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    val displayText = if (state == SDGSelectInputState.Default) {
        placeholder
    } else {
        type.text.ifEmpty { placeholder }
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
        modifier = Modifier
            .padding(marginValues)
            .clip(shape = SDGCornerRadius.BoxRadius.Radius12)
            .background(color = backgroundColor)
            .then(
                if (state != SDGSelectInputState.Disabled && onClick != null) {
                    Modifier.clickable(
                        hasRipple = true,
                        rippleColor = SDGColor.Neutral350,
                        onClick = onClick,
                    )
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
                overflow = overflow,
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
 * [SDGSelectInputType]에 텍스트를 전달하는 신규 API와의 하위 호환성을 위한 API입니다.
 */
@Deprecated(
    message = "text 대신 SDGSelectInputType의 text를 사용하세요.",
)
@Composable
fun SDGSelectInput(
    text: String?,
    placeholder: String,
    state: SDGSelectInputState,
    inputField: SDGSelectInputField,
    type: SDGSelectInputType,
    marginValues: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    SDGSelectInput(
        placeholder = placeholder,
        state = state,
        inputField = inputField,
        type = type.withText(text ?: placeholder),
        marginValues = marginValues,
        onClick = onClick,
        overflow = overflow,
    )
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
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    when (type) {
        is SDGSelectInputType.Text -> {
            SelectInputText(
                modifier = modifier,
                text = text,
                textColor = textColor,
                overflow = overflow,
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
                overflow = overflow,
            )
        }

        is SDGSelectInputType.OneImage -> {
            SelectedElementRow(
                modifier = modifier,
                text = text,
                textColor = textColor,
                image = type.image,
                imageSize = type.type,
                overflow = overflow,
            )
        }

        is SDGSelectInputType.TwoImage -> {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing10),
            ) {
                SelectedElementRow(
                    modifier = Modifier.fillMaxWidth(),
                    text = text,
                    textColor = textColor,
                    image = type.image,
                    imageSize = type.imageSize,
                    overflow = overflow,
                )
                SelectedElementRow(
                    modifier = Modifier.fillMaxWidth(),
                    text = type.secondText,
                    textColor = textColor,
                    image = type.secondImage,
                    imageSize = type.imageSize,
                    overflow = overflow,
                )
            }
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
        val selectedElementType = type.toSelectInputType(text = text.orEmpty())

        SDGSelectInput(
            placeholder = placeholder,
            state = state,
            inputField = inputField,
            type = selectedElementType,
            marginValues = PaddingValues(SDGSpacing.Spacing20),
            overflow = overflow,
        )
    }
}

private fun SDGSelectInputPreviewType.toSelectInputType(
    text: String,
): SDGSelectInputType {
    return when (this) {
        SDGSelectInputPreviewType.Text -> SDGSelectInputType.Text(text = text)
        SDGSelectInputPreviewType.Avatar -> SDGSelectInputType.Avatar(
            text = text,
            selectedElementImage = SDGSelectInputImage.Resource(
                resId = R.drawable.profile_small,
            ),
        )

        SDGSelectInputPreviewType.OneImageNormal1 -> SDGSelectInputType.OneImage(
            text = text,
            image = SDGSelectInputImage.Resource(
                resId = R.drawable.ic_common_photo,
            ),
            type = SDGSelectInputImageType.Normal1,
        )

        SDGSelectInputPreviewType.OneImageNormal2 -> SDGSelectInputType.OneImage(
            text = text,
            image = SDGSelectInputImage.Url(
                url = "https://example.com/image.png",
                failureImageResId = R.drawable.ic_common_photo,
            ),
            type = SDGSelectInputImageType.Normal2,
        )

        SDGSelectInputPreviewType.OneImageSpecial1 -> SDGSelectInputType.OneImage(
            text = text,
            image = SDGSelectInputImage.Resource(
                resId = R.drawable.ic_common_photo,
            ),
            type = SDGSelectInputImageType.Special1,
        )

        SDGSelectInputPreviewType.TwoImage -> SDGSelectInputType.TwoImage(
            text = text,
            image = SDGSelectInputImage.Resource(
                resId = R.drawable.ic_common_photo,
            ),
            secondText = "Second Selected Text",
            secondImage = SDGSelectInputImage.Resource(
                resId = R.drawable.ic_common_photo,
            ),
        )
    }
}

/** 레거시 API의 텍스트를 [SDGSelectInputType]에 반영합니다. */
private fun SDGSelectInputType.withText(
    text: String,
): SDGSelectInputType {
    return when (this) {
        is SDGSelectInputType.Text -> copy(text = text)
        is SDGSelectInputType.Avatar -> copy(text = text)
        is SDGSelectInputType.OneImage -> copy(text = text)
        is SDGSelectInputType.TwoImage -> copy(text = text)
    }
}
