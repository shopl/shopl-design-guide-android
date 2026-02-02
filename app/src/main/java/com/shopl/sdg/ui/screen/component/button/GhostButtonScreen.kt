package com.shopl.sdg.ui.screen.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.R
import com.shopl.sdg.component.button.SDGButtonFontWeight
import com.shopl.sdg.component.button.ghost.SDGGhostButton
import com.shopl.sdg.component.button.ghost.SDGGhostButtonIconSize
import com.shopl.sdg.component.button.ghost.SDGGhostButtonSize
import com.shopl.sdg.enums.SDGSampleStatus
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.ui.base.SDGSampleBaseComponentScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG Sample App - Component - Button - GhostButton
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=22804-3410&m=dev">Figma</a>
 */
@Composable
internal fun GhostButtonScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {

    val specs = persistentListOf<SDGSampleBaseTabItem<SDGGhostButtonSize>>(
        SDGSampleBaseTabItem(
            title = SDGGhostButtonSize.Large.sizeName,
            item = SDGGhostButtonSize.Large
        ),
        SDGSampleBaseTabItem(
            title = SDGGhostButtonSize.Medium.sizeName,
            item = SDGGhostButtonSize.Medium
        ),
        SDGSampleBaseTabItem(
            title = SDGGhostButtonSize.Small.sizeName,
            item = SDGGhostButtonSize.Small
        ),
    )


    SDGSampleBaseComponentScaffold<Unit, SDGGhostButtonSize>(
        componentName = ComponentScene.Button.GhostButton.displayLabel,
        componentDescription = "화면의 내용 영역에 배치하여 사용하는 배경이 없는 형태의 버튼 컴포넌트",
        specs = specs,
        guideLineDescriptions = persistentListOf(
            "SDG 컬러 시스템의 모든 컬러 적용 가능합니다."
        ),
        componentContent = { _, currentSpec, currentStatus ->
            if (currentSpec != null) {
                ComponentContent(
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
    size: SDGGhostButtonSize,
    status: SDGSampleStatus,
) {
    val enabled = when (status) {
        SDGSampleStatus.DEFAULT -> true
        SDGSampleStatus.DISABLED -> false
    }
    val iconSize = when (size) {
        SDGGhostButtonSize.Large -> SDGGhostButtonIconSize.Icon16
        SDGGhostButtonSize.Medium -> SDGGhostButtonIconSize.Icon14
        SDGGhostButtonSize.Small -> SDGGhostButtonIconSize.Icon12
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = SDGSpacing.Spacing40,
                horizontal = SDGSpacing.Spacing16
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing40)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing40)
            ) {
                SDGGhostButton(
                    size = size,
                    label = "Label",
                    labelColor = SDGColor.Neutral700,
                    onClick = {},
                    iconSize = iconSize,
                    labelWeight = SDGButtonFontWeight.R,
                    enable = enabled,
                    leftIcon = R.drawable.icon_sample,
                    leftIconTint = SDGColor.Red300,
                )
                SDGGhostButton(
                    size = size,
                    label = "Label",
                    labelColor = SDGColor.Neutral700,
                    onClick = {},
                    iconSize = iconSize,
                    labelWeight = SDGButtonFontWeight.R,
                    enable = enabled,
                    rightIcon = R.drawable.icon_sample,
                    rightIconTint = SDGColor.Red300,
                )
                SDGGhostButton(
                    size = size,
                    label = "Label",
                    labelColor = SDGColor.Neutral700,
                    onClick = {},
                    iconSize = iconSize,
                    labelWeight = SDGButtonFontWeight.R,
                    enable = enabled,
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing40)
            ) {
                SDGGhostButton(
                    size = size,
                    label = "Label",
                    labelColor = SDGColor.Neutral700,
                    onClick = {},
                    iconSize = iconSize,
                    labelWeight = SDGButtonFontWeight.SB,
                    enable = enabled,
                    leftIcon = R.drawable.icon_sample,
                    leftIconTint = SDGColor.Red300,
                )
                SDGGhostButton(
                    size = size,
                    label = "Label",
                    labelColor = SDGColor.Neutral700,
                    onClick = {},
                    iconSize = iconSize,
                    labelWeight = SDGButtonFontWeight.SB,
                    enable = enabled,
                    rightIcon = R.drawable.icon_sample,
                    rightIconTint = SDGColor.Red300,
                )
                SDGGhostButton(
                    size = size,
                    label = "Label",
                    labelColor = SDGColor.Neutral700,
                    onClick = {},
                    iconSize = iconSize,
                    labelWeight = SDGButtonFontWeight.SB,
                    enable = enabled,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewGhostButtonScreen() {
    ShoplDesignGuideTheme {
        GhostButtonScreen(
            onClickBack = {},
            onClickMenu = {}
        )
    }
}
