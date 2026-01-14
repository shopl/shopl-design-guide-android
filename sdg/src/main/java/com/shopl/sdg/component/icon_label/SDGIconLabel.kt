package com.shopl.sdg.component.icon_label

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_common.util.textCenterAlignment

/**
 * SDG - Icon Label
 *
 * @param size [SDGIconLabelSize] 내부 텍스트 Size, LineHeight 조절
 * @param type [SDGIconLabelType] 내부 텍스트 Weight 조절
 * @param spacing [SDGIconLabelSpacing] 아이콘과 텍스트 사이 간격
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=10507-19136&m=dev">Figma</a>
 */
@Composable
fun SDGIconLabel(
    text: String,
    textColor: Color,
    size: SDGIconLabelSize,
    type: SDGIconLabelType,
    spacing: SDGIconLabelSpacing,
    modifier: Modifier = Modifier,
    @DrawableRes leftIconResId: Int? = null,
    leftIconTint: Color? = null,
    onClickLeftIcon: (() -> Unit)? = null,
    @DrawableRes rightIconResId: Int? = null,
    rightIconTint: Color? = null,
    onClickRightIcon: (() -> Unit)? = null,
    maxLines: Int = Int.MAX_VALUE,
    isFillMaxWidth: Boolean = true,
) {
    SDGIconLabelContent(
        text = AnnotatedString(text),
        textColor = textColor,
        size = size,
        type = type,
        spacing = spacing,
        modifier = modifier,
        leftIconResId = leftIconResId,
        leftIconTint = leftIconTint,
        onClickLeftIcon = onClickLeftIcon,
        rightIconResId = rightIconResId,
        rightIconTint = rightIconTint,
        onClickRightIcon = onClickRightIcon,
        maxLines = maxLines,
        isFillMaxWidth = isFillMaxWidth
    )
}

@Composable
fun SDGIconLabel(
    text: AnnotatedString,
    textColor: Color,
    size: SDGIconLabelSize,
    type: SDGIconLabelType,
    spacing: SDGIconLabelSpacing,
    modifier: Modifier = Modifier,
    @DrawableRes leftIconResId: Int? = null,
    leftIconTint: Color? = null,
    onClickLeftIcon: (() -> Unit)? = null,
    @DrawableRes rightIconResId: Int? = null,
    rightIconTint: Color? = null,
    onClickRightIcon: (() -> Unit)? = null,
    maxLines: Int = Int.MAX_VALUE,
    isFillMaxWidth: Boolean = true,
) {
    SDGIconLabelContent(
        text = text,
        textColor = textColor,
        size = size,
        type = type,
        spacing = spacing,
        modifier = modifier,
        leftIconResId = leftIconResId,
        leftIconTint = leftIconTint,
        onClickLeftIcon = onClickLeftIcon,
        rightIconResId = rightIconResId,
        rightIconTint = rightIconTint,
        onClickRightIcon = onClickRightIcon,
        maxLines = maxLines,
        isFillMaxWidth = isFillMaxWidth
    )
}

@Composable
private fun SDGIconLabelContent(
    text: AnnotatedString,
    textColor: Color,
    size: SDGIconLabelSize,
    type: SDGIconLabelType,
    spacing: SDGIconLabelSpacing,
    modifier: Modifier = Modifier,
    @DrawableRes leftIconResId: Int? = null,
    leftIconTint: Color? = null,
    onClickLeftIcon: (() -> Unit)? = null,
    @DrawableRes rightIconResId: Int? = null,
    rightIconTint: Color? = null,
    onClickRightIcon: (() -> Unit)? = null,
    maxLines: Int = Int.MAX_VALUE,
    isFillMaxWidth: Boolean = true,
) {
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    val alignment = remember(textLayoutResult) {
        textCenterAlignment(textLayoutResult)
    }

    val typography = getIconLabelTypography(type = type, size = size)

    Row(
        modifier = modifier.then(
            if (isFillMaxWidth) {
                Modifier.fillMaxWidth()
            } else {
                Modifier
            }
        ),
        horizontalArrangement = Arrangement.spacedBy(spacing.value)
    ) {
        if (leftIconResId != null) {
            SDGImage(
                modifier = Modifier
                    .align(alignment)
                    .size(14.dp)
                    .then(
                        if (onClickLeftIcon != null) {
                            Modifier.clickable(hasRipple = false) { onClickLeftIcon() }
                        } else {
                            Modifier
                        }
                    ),
                resId = leftIconResId,
                color = leftIconTint
            )
        }
        SDGText(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f, isFillMaxWidth),
            text = text,
            textColor = textColor,
            typography = typography,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResult = it }
        )
        if (rightIconResId != null) {
            SDGImage(
                modifier = Modifier
                    .align(alignment)
                    .size(14.dp)
                    .then(
                        if (onClickRightIcon != null) {
                            Modifier.clickable(hasRipple = false) { onClickRightIcon() }
                        } else {
                            Modifier
                        }
                    ),
                resId = rightIconResId,
                color = rightIconTint
            )
        }
    }
}

/**
 * [SDGIconLabelType]과 [SDGIconLabelSize]에 맞는 Typography 반환
 */
private fun getIconLabelTypography(
    type: SDGIconLabelType,
    size: SDGIconLabelSize
): SDGTypography {
    return when (type) {
        SDGIconLabelType.Basic -> when (size) {
            SDGIconLabelSize.Size12 -> SDGTypography.Body3R
            SDGIconLabelSize.Size14 -> SDGTypography.Body2R
            SDGIconLabelSize.Size16 -> SDGTypography.Body1R
        }

        SDGIconLabelType.Empha -> when (size) {
            SDGIconLabelSize.Size12 -> SDGTypography.Body3SB
            SDGIconLabelSize.Size14 -> SDGTypography.Body2SB
            SDGIconLabelSize.Size16 -> SDGTypography.Body1SB
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGIconLabel(
    @PreviewParameter(SDGIconLabelPreviewParameterProvider::class)
    param: SDGIconLabelPreviewParam
) {
    with(param) {
        SDGIconLabel(
            text = text,
            textColor = textColor,
            size = size,
            type = type,
            spacing = spacing,
            leftIconResId = leftIconResId,
            leftIconTint = leftIconTint,
            onClickLeftIcon = null,
            rightIconResId = rightIconResId,
            rightIconTint = rightIconTint,
            onClickRightIcon = null,
            maxLines = maxLines,
            isFillMaxWidth = isFillMaxWidth
        )
    }
}
