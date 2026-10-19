package com.shopl.sdg.component.navigation.text

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.shopl.sdg.component.navigation.text.preview.SDGTextNaviPreviewParameter
import com.shopl.sdg.component.navigation.text.preview.SDGTextNaviPreviewParameterProvider
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing6
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

private const val LABEL_MAX_LINES = 1
private const val PREVIEW_WIDTH_DP = 375

/**
 * SDG - Navigation - Text Navi
 *
 * 화면의 상단에 위치하여 현재 위치를 알려주는 타이틀과 우측에 1개 기능을 텍스트로 조합한 컴포넌트
 *
 * @version 2.1.21
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19821-30436&m=dev">Figma</a>
 */
@Composable
fun SDGTextNavi(
    label: SDGTextNaviLabel,
    backButton: SDGTextNaviBackButton,
    rightItem: SDGTextNaviRightItem?,
    backgroundColor: Color,
    marginValues: PaddingValues = PaddingValues(),
) {
    SDGTextNaviLayout(
        label = label,
        backgroundColor = backgroundColor,
        marginValues = marginValues,
        backButton = if (backButton is SDGTextNaviBackButton.True) {
            {
                SDGImage(
                    resId = R.drawable.ic_navi_back_android,
                    color = SDGColor.Neutral700,
                    modifier = Modifier
                        .size(SDGTextNaviBackIconSize)
                        .clickable(onClick = backButton.onClick),
                )
            }
        } else {
            null
        },
        rightItem = if (rightItem != null &&
            (rightItem.subIcon is SDGTextNaviSubIcon.True || rightItem.subLabel is SDGTextNaviSubLabel.True)
        ) {
            { SDGTextNaviRightItemContent(rightItem) }
        } else {
            null
        },
    )
}

@Composable
private fun SDGTextNaviRightItemContent(item: SDGTextNaviRightItem) {
    val subIcon = item.subIcon
    val subLabel = item.subLabel

    Row(
        modifier = Modifier
            .clickable(onClick = item.onClick)
            .padding(end = Spacing6),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (subIcon is SDGTextNaviSubIcon.True) {
            SDGImage(
                resId = subIcon.iconResId,
                color = subIcon.iconColor,
                modifier = Modifier.size(SDGTextNaviSubIconSize),
            )
        }

        if (subLabel is SDGTextNaviSubLabel.True) {
            SDGText(
                text = subLabel.text,
                typography = SDGTypography.Body1R,
                textColor = subLabel.textColor,
                maxLines = LABEL_MAX_LINES,
                softWrap = false,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = PREVIEW_WIDTH_DP)
@Composable
private fun PreviewSDGTextNavi(
    @PreviewParameter(SDGTextNaviPreviewParameterProvider::class)
    parameter: SDGTextNaviPreviewParameter,
) {
    with(parameter) {
        SDGTextNavi(
            label = label,
            backButton = backButton,
            rightItem = rightItem,
            backgroundColor = backgroundColor,
        )
    }
}
