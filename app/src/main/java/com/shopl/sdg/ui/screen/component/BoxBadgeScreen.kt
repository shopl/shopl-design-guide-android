package com.shopl.sdg.ui.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.badge.box.SDGBoxBadge
import com.shopl.sdg.component.badge.box.SDGBoxBadgeFontWeight
import com.shopl.sdg.component.badge.box.SDGBoxBadgeIcon
import com.shopl.sdg.component.badge.box.SDGBoxBadgeStyle
import com.shopl.sdg.enums.SDGSampleStatus
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.ui.base.SDGSampleBaseComponentScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_resource.R
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG Sample App - Component - Badge - Box Badge
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=18890-9265&m=dev">Figma</a>
 */
@Composable
internal fun BoxBadgeScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    val types = persistentListOf<SDGSampleBaseTabItem<SDGBoxBadgeStyle>>(
        SDGSampleBaseTabItem(
            title = SDGBoxBadgeStyle.Solid.styleName,
            item = SDGBoxBadgeStyle.Solid,
        ),
        SDGSampleBaseTabItem(
            title = SDGBoxBadgeStyle.Line(lineColor = SDGColor.Primary300).styleName,
            item = SDGBoxBadgeStyle.Line(lineColor = SDGColor.Primary300),
        ),
    )
    val specs = persistentListOf<SDGSampleBaseTabItem<SDGBoxBadgeFontWeight>>(
        SDGSampleBaseTabItem(
            title = SDGBoxBadgeFontWeight.Normal.name,
            item = SDGBoxBadgeFontWeight.Normal,
        ),
        SDGSampleBaseTabItem(
            title = SDGBoxBadgeFontWeight.Bold.name,
            item = SDGBoxBadgeFontWeight.Bold,
        ),
    )

    SDGSampleBaseComponentScaffold(
        componentName = ComponentScene.Badge.BoxBadge.displayLabel,
        componentDescription = "특정 상태나 데이터 구분 또는 전달을 위한 박스 형태의 컴포넌트",
        types = types,
        specs = specs,
        componentContent = { currentType, currentSpec, currentStatus ->
            if (currentType != null && currentSpec != null) {
                ComponentContent(
                    style = currentType,
                    fontWeight = currentSpec,
                    status = currentStatus,
                )
            }
        },
        onClickBack = onClickBack,
        onClickMenu = onClickMenu,
    )
}

@Composable
private fun ComponentContent(
    style: SDGBoxBadgeStyle,
    fontWeight: SDGBoxBadgeFontWeight,
    status: SDGSampleStatus,
) {
    val labelColor = when (style) {
        SDGBoxBadgeStyle.Solid -> SDGColor.Neutral0
        is SDGBoxBadgeStyle.Line -> style.lineColor
    }
    val backgroundColor = when (style) {
        SDGBoxBadgeStyle.Solid -> SDGColor.Primary300
        is SDGBoxBadgeStyle.Line -> SDGColor.Transparent
    }
    val onClick: (() -> Unit)? = if (status == SDGSampleStatus.DEFAULT) ({}) else null

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = SDGSpacing.Spacing16,
                vertical = SDGSpacing.Spacing40,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing16),
        ) {
            SDGBoxBadge(
                label = "Label",
                style = style,
                fontWeight = fontWeight,
                labelColor = labelColor,
                backgroundColor = backgroundColor,
                onClick = onClick,
            )
            SDGBoxBadge(
                label = "Left Icon",
                style = style,
                fontWeight = fontWeight,
                labelColor = labelColor,
                backgroundColor = backgroundColor,
                leftIc = SDGBoxBadgeIcon(
                    resId = R.drawable.ic_common_prev_s,
                    tint = labelColor,
                ),
                onClick = onClick,
            )
            SDGBoxBadge(
                label = "Right Icon",
                style = style,
                fontWeight = fontWeight,
                labelColor = labelColor,
                backgroundColor = backgroundColor,
                rightIc = SDGBoxBadgeIcon(
                    resId = R.drawable.ic_common_next_s,
                    tint = labelColor,
                ),
                onClick = onClick,
            )
            SDGBoxBadge(
                label = "Label이 길어지면 가용 범위 내에서 말줄임표로 표시되는 최대 너비 Box Badge입니다.",
                style = style,
                fontWeight = fontWeight,
                labelColor = labelColor,
                backgroundColor = backgroundColor,
                isFillMaxWidth = true,
                marginValues = PaddingValues(horizontal = SDGSpacing.Spacing8),
                leftIc = SDGBoxBadgeIcon(
                    resId = R.drawable.ic_common_prev_s,
                    tint = labelColor,
                ),
                rightIc = SDGBoxBadgeIcon(
                    resId = R.drawable.ic_common_next_s,
                    tint = labelColor,
                ),
                onClick = onClick,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewBoxBadgeScreen() {
    ShoplDesignGuideTheme {
        BoxBadgeScreen(
            onClickBack = {},
            onClickMenu = {},
        )
    }
}
