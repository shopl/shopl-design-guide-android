package com.shopl.sdg.component.navigation.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

private val NAVI_HEIGHT = 48.dp
private val BACK_ICON_SIZE = 40.dp
private val ACTION_ICON_SIZE = 20.dp

/**
 * SDG - Navigation - Text Navi
 *
 * 화면 상단에서 현재 위치를 나타내는 타이틀과 화면 이동, 우측 주요 실행 기능을 제공하는 내비게이션 컴포넌트
 *
 * @version 2.3.53
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=30016-9273&m=dev">Figma</a>
 */
@Composable
fun SDGTextNavi(
    label: SDGTextNaviLabel,
    backButton: SDGTextNaviBackButton,
    showActionIcon: ShowActionIcon,
    actionLabel: SDGTextNaviActionItem.Label,
    backgroundColor: Color,
    marginValues: PaddingValues = PaddingValues(),
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(marginValues)
            .height(NAVI_HEIGHT)
            .background(backgroundColor)
            .padding(horizontal = Spacing10, vertical = Spacing4),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (backButton is SDGTextNaviBackButton.True) {
            SDGImage(
                resId = R.drawable.ic_navi_back_android,
                color = backButton.iconColor,
                modifier = Modifier
                    .size(BACK_ICON_SIZE)
                    .clickable(onClick = backButton.onClick),
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

        Spacer(Modifier.width(Spacing8))

        SDGTextNaviAction(showActionIcon = showActionIcon, actionLabel = actionLabel)
    }
}

@Composable
private fun SDGTextNaviAction(
    showActionIcon: ShowActionIcon,
    actionLabel: SDGTextNaviActionItem.Label,
) {
    Row(
        modifier = Modifier
            .clickable(onClick = actionLabel.onClick)
            .padding(end = Spacing6),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (showActionIcon is ShowActionIcon.True) {
            SDGImage(
                resId = showActionIcon.iconResId,
                color = showActionIcon.iconColor,
                modifier = Modifier.size(ACTION_ICON_SIZE),
            )
        }

        SDGText(
            text = actionLabel.text,
            typography = SDGTypography.Body1R,
            textColor = actionLabel.textColor,
            maxLines = LABEL_MAX_LINES,
        )
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
            showActionIcon = showActionIcon,
            actionLabel = actionLabel,
            backgroundColor = backgroundColor,
        )
    }
}
