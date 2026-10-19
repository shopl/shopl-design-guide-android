package com.shopl.sdg.component.navigation.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing10
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing2
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

internal val SDGTextNaviBackIconSize = 40.dp
internal val SDGTextNaviSubIconSize = 20.dp

private const val LABEL_MAX_LINES = 1
private val NAVI_MIN_HEIGHT = 48.dp

@Composable
internal fun SDGTextNaviLayout(
    label: SDGTextNaviLabel,
    backgroundColor: Color,
    marginValues: PaddingValues,
    backButton: (@Composable () -> Unit)?,
    rightItem: (@Composable () -> Unit)?,
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
        backButton?.invoke()

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

        if (rightItem != null) {
            Spacer(Modifier.width(Spacing8))

            rightItem()
        }
    }
}
