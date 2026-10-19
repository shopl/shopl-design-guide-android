package com.shopl.sdg.component.navigation.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.navigation.text.preview.SDGTextNaviPreviewParameter
import com.shopl.sdg.component.navigation.text.preview.SDGTextNaviPreviewParameterProvider
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing10
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing2
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing6
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

private const val LABEL_MAX_LINES = 1
private const val PREVIEW_WIDTH_DP = 375

private val NAVI_MIN_HEIGHT = 48.dp
private val BACK_ICON_SIZE = 40.dp
private val SUB_ICON_SIZE = 20.dp

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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(marginValues)
            .heightIn(min = NAVI_MIN_HEIGHT)
            .background(backgroundColor)
            .padding(horizontal = Spacing10, vertical = Spacing4),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (backButton is SDGTextNaviBackButton.True) {
            SDGImage(
                resId = R.drawable.ic_navi_back_android,
                color = backButton.iconColor,
                contentDescription = backButton.contentDescription,
                modifier = Modifier
                    .size(BACK_ICON_SIZE)
                    .clickable(role = Role.Button, onClick = backButton.onClick),
            )
        }

        Box(
            modifier = Modifier
                .weight(1f, true)
                .padding(start = Spacing2),
        ) {
            if (label is SDGTextNaviLabel.True) {
                SDGText(
                    text = label.text,
                    typography = SDGTypography.NaviTitle,
                    textColor = label.textColor,
                    maxLines = LABEL_MAX_LINES,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        if (rightItem != null &&
            (rightItem.subIcon is SDGTextNaviSubIcon.True || rightItem.subLabel is SDGTextNaviSubLabel.True)
        ) {
            Spacer(Modifier.width(Spacing8))

            SDGTextNaviRightItemContent(rightItem)
        }
    }
}

@Composable
private fun SDGTextNaviRightItemContent(item: SDGTextNaviRightItem) {
    val subIcon = item.subIcon
    val subLabel = item.subLabel

    Row(
        modifier = Modifier
            .clickable(role = Role.Button, onClick = item.onClick)
            .padding(end = Spacing6),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (subIcon is SDGTextNaviSubIcon.True) {
            SDGImage(
                resId = subIcon.iconResId,
                color = subIcon.iconColor,
                contentDescription = subIcon.contentDescription,
                modifier = Modifier.size(SUB_ICON_SIZE),
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
