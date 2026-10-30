package com.shopl.sdg.ui.screen.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.button.box.SDGBoxButton
import com.shopl.sdg.component.button.box.SDGBoxButtonIconSize
import com.shopl.sdg.component.button.box.SDGBoxButtonShowIcon
import com.shopl.sdg.component.button.box.SDGBoxButtonSize
import com.shopl.sdg.component.button.box.SDGBoxButtonType
import com.shopl.sdg.enums.SDGSampleStatus
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.ui.base.SDGSampleBaseComponentScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R
import kotlinx.collections.immutable.persistentListOf

private val BoxButtonSampleMaxWidth = 120.dp

/**
 * SDG Sample App - Component - Button - BoxButton
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=7102-15096&m=dev">Figma</a>
 */
@Composable
internal fun BoxButtonScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {

    val types = persistentListOf<SDGSampleBaseTabItem<SDGBoxButtonType>>(
        SDGSampleBaseTabItem(
            title = SDGBoxButtonType.Solid.typeName,
            item = SDGBoxButtonType.Solid
        ),
        SDGSampleBaseTabItem(
            title = SDGBoxButtonType.Line(SDGColor.Neutral250).typeName,
            item = SDGBoxButtonType.Line(SDGColor.Neutral250)
        ),
    )

    val specs = persistentListOf<SDGSampleBaseTabItem<SDGBoxButtonSize>>(
        SDGSampleBaseTabItem(
            title = SDGBoxButtonSize.Medium.sizeName,
            item = SDGBoxButtonSize.Medium
        ),
        SDGSampleBaseTabItem(
            title = SDGBoxButtonSize.Small.sizeName,
            item = SDGBoxButtonSize.Small
        ),
        SDGSampleBaseTabItem(
            title = SDGBoxButtonSize.SSmall.sizeName,
            item = SDGBoxButtonSize.SSmall
        ),
        SDGSampleBaseTabItem(
            title = SDGBoxButtonSize.XSmall.sizeName,
            item = SDGBoxButtonSize.XSmall
        ),
    )

    SDGSampleBaseComponentScaffold(
        componentName = ComponentScene.Button.BoxButton.displayLabel,
        componentDescription = "화면 내에 배치하여 사용하는 일반적인 사각 형태의 버튼 컴포넌트",
        types = types,
        specs = specs,
        guideLineDescriptions = persistentListOf(
            "아이콘과 Label은 수평으로 중앙 정렬합니다.",
        ),
        componentContent = { currentType, currentSpec, currentStatus ->
            if (currentType != null && currentSpec != null) {
                ComponentContent(
                    type = currentType,
                    size = currentSpec,
                    status = currentStatus
                )
            }
        },
        onClickBack = onClickBack,
        onClickMenu = onClickMenu
    )
}

@Composable
private fun ComponentContent(
    type: SDGBoxButtonType,
    size: SDGBoxButtonSize,
    status: SDGSampleStatus,
) {
    var clickCount by remember { mutableIntStateOf(0) }
    var selected by remember { mutableStateOf(false) }
    val enabled = status != SDGSampleStatus.DISABLED
    val backgroundColor = if (type == SDGBoxButtonType.Solid) SDGColor.Neutral200 else SDGColor.Neutral0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = SDGSpacing.Spacing16,
                vertical = SDGSpacing.Spacing24,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing12),
    ) {
        SDGText(
            text = "버튼 클릭: $clickCount",
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body2R,
        )
        SDGBoxButtonIconSize.entries.forEach { iconSize ->
            SDGBoxButton(
                size = size,
                type = type,
                label = "Label (${iconSize.name})",
                labelColor = SDGColor.Neutral600,
                backgroundColor = backgroundColor,
                leftIc = SDGBoxButtonShowIcon.True(
                    iconResId = R.drawable.ic_common_prev_s,
                    iconColor = SDGColor.Neutral600,
                    iconSize = iconSize,
                ),
                rightIc = SDGBoxButtonShowIcon.True(
                    iconResId = R.drawable.ic_common_next_s,
                    iconColor = SDGColor.Neutral600,
                    iconSize = iconSize,
                ),
                onClick = { clickCount++ },
                enable = enabled,
            )
        }
        SDGBoxButton(
            size = size,
            type = type,
            label = "아이콘 없음",
            labelColor = SDGColor.Neutral600,
            backgroundColor = backgroundColor,
            leftIc = SDGBoxButtonShowIcon.False,
            rightIc = SDGBoxButtonShowIcon.False,
            onClick = { clickCount++ },
            enable = enabled,
        )
        SDGBoxButton(
            size = size,
            type = type,
            label = "긴 버튼명은 정해진 최대 너비에서 말줄임합니다",
            labelColor = SDGColor.Neutral600,
            backgroundColor = SDGColor.Transparent,
            leftIc = SDGBoxButtonShowIcon.False,
            rightIc = SDGBoxButtonShowIcon.True(
                iconResId = R.drawable.ic_common_next_s,
                iconColor = null,
                iconSize = SDGBoxButtonIconSize.S,
            ),
            onClick = { clickCount++ },
            enable = enabled,
            maxWidth = BoxButtonSampleMaxWidth,
        )
        SDGBoxButton(
            size = size,
            type = type,
            label = "흰색 라벨",
            labelColor = SDGColor.Neutral0,
            backgroundColor = SDGColor.Primary300,
            leftIc = SDGBoxButtonShowIcon.True(
                iconResId = R.drawable.ic_common_prev_s,
                iconColor = SDGColor.Neutral0,
                iconSize = SDGBoxButtonIconSize.XS,
            ),
            rightIc = SDGBoxButtonShowIcon.False,
            onClick = { clickCount++ },
            enable = enabled,
        )
        SDGBoxButton(
            size = size,
            type = type,
            label = if (selected) "선택됨" else "선택하기",
            labelColor = if (selected) SDGColor.Neutral900 else SDGColor.Neutral600,
            backgroundColor = if (selected) SDGColor.Neutral300 else backgroundColor,
            leftIc = SDGBoxButtonShowIcon.False,
            rightIc = SDGBoxButtonShowIcon.False,
            onClick = {
                selected = !selected
                clickCount++
            },
            enable = enabled,
        )
    }
}

@Preview
@Composable
private fun PreviewBoxButtonScreen() {
    ShoplDesignGuideTheme {
        BoxButtonScreen(
            onClickBack = {},
            onClickMenu = {}
        )
    }
}
