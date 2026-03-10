package com.shopl.sdg.template.form.custom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.template.form.SDGFormType
import com.shopl.sdg.template.form.custom.preview.SDGCustomFormPreviewParameter
import com.shopl.sdg.template.form.custom.preview.SDGCustomFormPreviewParameterProvider
import com.shopl.sdg_common.ext.withColor
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

/**
 * SDG Template - Form - SDGCustomForm
 *
 * Label과 함께 바디 영역을 다양한 요소로 구성하여 입력하는 템플릿
 *
 * @param label [String] Form 라벨
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=22259-1320&m=dev">Figma</a>
 */

@Composable
fun SDGCustomForm(
    label: String,
    body: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    type: SDGFormType = SDGFormType.EMPHA,
    essential: Boolean = false,
    @DrawableRes iconResourceId: Int? = null,
    iconTint: Color? = SDGColor.Neutral700,
    onClickIcon: (() -> Unit)? = null,
    onClickRefresh: (() -> Unit)? = null,
) {
    val titleAnnotatedString = if (essential) {
        "$label*".withColor(SDGColor.Red300, "*")
    } else {
        AnnotatedString(label)
    }

    SDGCustomForm(
        label = titleAnnotatedString,
        body = body,
        modifier = modifier,
        type = type,
        iconResourceId = iconResourceId,
        iconTint = iconTint,
        onClickIcon = onClickIcon,
        onClickRefresh = onClickRefresh,
    )
}

@Composable
fun SDGCustomForm(
    label: AnnotatedString,
    body: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    type: SDGFormType = SDGFormType.EMPHA,
    @DrawableRes iconResourceId: Int? = null,
    iconTint: Color? = SDGColor.Neutral700,
    onClickIcon: (() -> Unit)? = null,
    onClickRefresh: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing8),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = SDGCustomFormDimens.HeaderMinHeight),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SDGText(
                    modifier = Modifier.weight(1f, fill = false),
                    text = label,
                    textColor = SDGColor.Neutral700,
                    typography = when (type) {
                        SDGFormType.EMPHA -> SDGTypography.Body1SB
                        SDGFormType.NORMAL -> SDGTypography.Body1R
                    },
                )

                iconResourceId?.let {
                    Box(
                        modifier = Modifier
                            .clickable {
                                onClickIcon?.invoke()
                            }
                            .width(SDGCustomFormDimens.IconTouchBoxWidth)
                            .height(SDGCustomFormDimens.IconTouchBoxHeight)
                            .padding(
                                start = SDGCustomFormDimens.IconTouchStartPadding,
                                top = SDGCustomFormDimens.IconTouchVerticalPadding,
                                end = SDGCustomFormDimens.IconTouchEndPadding,
                                bottom = SDGCustomFormDimens.IconTouchVerticalPadding,
                            ),
                    ) {
                        SDGImage(
                            resId = it,
                            color = iconTint,
                            modifier = Modifier.size(SDGCustomFormDimens.IconSize),
                        )
                    }
                }
            }

            onClickRefresh?.let { onRefreshClick ->
                SDGImage(
                    modifier = Modifier
                        .clickable { onRefreshClick() }
                        .background(
                            color = SDGColor.Neutral50,
                            shape = CircleShape,
                        )
                        .padding(SDGSpacing.Spacing2)
                        .size(SDGCustomFormDimens.RefreshButtonSize),
                    resId = R.drawable.ic_common_refresh,
                    color = SDGColor.Neutral400,
                )
            }
        }

        body()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGCustomForm(
    @PreviewParameter(SDGCustomFormPreviewParameterProvider::class)
    parameter: SDGCustomFormPreviewParameter,
) {
    with(parameter) {
        SDGCustomForm(
            label = label,
            type = type,
            essential = essential,
            iconResourceId = if (showIcon) R.drawable.ic_clip else null,
            onClickRefresh = if (showRefresh) ({}) else null,
            body = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SDGColor.PurpleP_a10)
                        .padding(vertical = SDGCustomFormDimens.PreviewBodyVerticalPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    SDGText(
                        text = "body area",
                        typography = SDGTypography.Body1R,
                        textColor = SDGColor.PurpleP,
                    )
                }
            },
        )
    }
}

private object SDGCustomFormDimens {
    val HeaderMinHeight = 28.dp

    val IconSize = 14.dp
    val IconTouchBoxWidth = 26.dp
    val IconTouchBoxHeight = 20.dp
    val IconTouchStartPadding = 4.dp
    val IconTouchVerticalPadding = 3.dp
    val IconTouchEndPadding = 8.dp

    val RefreshButtonSize = 24.dp

    val PreviewBodyVerticalPadding = 24.dp
}
