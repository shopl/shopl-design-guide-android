package com.shopl.sdg.component.navigation.basic

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing10
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing16
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing2
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.PersistentList

/**
 * SDG - Navigation - Basic Navi
 *
 * 화면의 상단에 위치하며, 타이틀과 아이콘으로 조합된 템플릿
 *
 * @param
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19779-9926&t=0IkMpxxx2xWJ4zUm-4">Figma</a>
 */
@Composable
fun SDGBasicNavi(
    leftIcon: SDGBasicNaviIconItem?,
    rightIcons: PersistentList<SDGBasicNaviIconItem>?,
    backgroundColor: Color,
    title: String = "",
    titleColor: Color = SDGColor.Neutral900
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = backgroundColor)
            .padding(vertical = Spacing4)
            .padding(start = if (leftIcon == null) Spacing16 else Spacing10)
            .padding(end = Spacing10),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leftIcon?.let {
            SDGBasicNaviIcon(icon = it)

            Spacer(Modifier.width(Spacing2))
        }

        SDGText(
            text = title,
            typography = SDGTypography.NaviTitle,
            textColor = titleColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        if (!rightIcons.isNullOrEmpty()) {
            Spacer(Modifier.width(Spacing10))

            SDGBasicNaviRightIconRow(icons = rightIcons)
        }
    }
}

@Composable
private fun SDGBasicNaviRightIconRow(
    icons: PersistentList<SDGBasicNaviIconItem>
) {
    Row(
        modifier = Modifier.width(124.dp),
        horizontalArrangement = Arrangement.spacedBy(Spacing2, Alignment.End),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icons.forEach { SDGBasicNaviIcon(icon = it) }
    }
}

@Composable
private fun SDGBasicNaviIcon(icon: SDGBasicNaviIconItem) {
    with(icon) {
        Box {
            SDGImage(
                resId = resId,
                color = color,
                modifier = Modifier
                    .size(40.dp)
                    .clickable(onClick = onClick)
            )

            if (showDot) {
                Canvas(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(8.dp)
                        .align(Alignment.TopEnd)
                ) {
                    drawCircle(
                        color = SDGColor.Red350
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGBasicNavi(
    @PreviewParameter(SDGBasicNaviPreviewParameterProvider::class)
    parameter: SDGBasicNaviPreviewParameter
) {
    with(parameter) {
        SDGBasicNavi(
            leftIcon = leftIcon,
            rightIcons = rightIcons,
            backgroundColor = backgroundColor,
            title = title,
            titleColor = titleColor
        )
    }
}