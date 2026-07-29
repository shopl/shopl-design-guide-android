package com.shopl.sdg.component.icon_label

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Icon Label
 *
 * 아이콘과 텍스트를 조합하여 보여주는 컴포넌트
 *
 * @version 2.1.29
 *
 * @param size [SDGIconLabelSize] 내부 텍스트 Size, LineHeight 조절
 * @param type [SDGIconLabelType] 내부 텍스트 Weight 조절
 * @param spacing [SDGIconLabelSpacing] 아이콘과 텍스트 사이 간격
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=10507-19135&m=dev">Figma</a>
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
        isFillMaxWidth = isFillMaxWidth,
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
    val typography = type.typography(size)

    Row(
        modifier = modifier.then(
            if (isFillMaxWidth) {
                Modifier.fillMaxWidth()
            } else {
                Modifier
            }
        ),
        horizontalArrangement = Arrangement.spacedBy(spacing.value),
    ) {
        if (leftIconResId != null) {
            SDGImage(
                modifier = Modifier
                    .padding(vertical = size.iconVerticalPadding)
                    .size(SDGIconLabelIconSize)
                    .then(
                        if (onClickLeftIcon != null) {
                            Modifier.clickable(hasRipple = false) { onClickLeftIcon() }
                        } else {
                            Modifier
                        }
                    ),
                resId = leftIconResId,
                color = leftIconTint,
            )
        }
        SDGText(
            modifier = if (isFillMaxWidth) {
                Modifier.weight(1f)
            } else {
                Modifier
            },
            text = text,
            textColor = textColor,
            typography = typography,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
        )
        if (rightIconResId != null) {
            SDGImage(
                modifier = Modifier
                    .padding(vertical = size.iconVerticalPadding)
                    .size(SDGIconLabelIconSize)
                    .then(
                        if (onClickRightIcon != null) {
                            Modifier.clickable(hasRipple = false) { onClickRightIcon() }
                        } else {
                            Modifier
                        }
                    ),
                resId = rightIconResId,
                color = rightIconTint,
            )
        }
    }
}

private val SDGIconLabelIconSize = 14.dp

@Preview(showBackground = true)
@Composable
private fun PreviewSDGIconLabel(
    @PreviewParameter(SDGIconLabelPreviewParameterProvider::class)
    param: SDGIconLabelPreviewParam,
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
            isFillMaxWidth = isFillMaxWidth,
        )
    }
}
