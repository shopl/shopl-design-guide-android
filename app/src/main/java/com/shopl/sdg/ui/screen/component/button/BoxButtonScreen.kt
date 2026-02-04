package com.shopl.sdg.ui.screen.component.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.button.box.SDGBoxButton
import com.shopl.sdg.component.button.box.SDGBoxButtonSize
import com.shopl.sdg.component.button.box.SDGBoxButtonType
import com.shopl.sdg.enums.SDGSampleStatus
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.ui.base.SDGSampleBaseComponentScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG Sample App - Component - Button - BoxButton
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=22804-3346&m=dev">Figma</a>
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
            title = SDGBoxButtonType.Line(SDGColor.Neutral350).typeName,
            item = SDGBoxButtonType.Line(SDGColor.Neutral350)
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
            "SDG 컬러 시스템의 모든 컬러 적용 가능합니다."
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = SDGSpacing.Spacing40,
            ),
        contentAlignment = Alignment.Center
    ) {
        SDGBoxButton(
            size = size,
            type = type,
            label = "Label",
            labelColor = SDGColor.Neutral600,
            backgroundColor = if (type == SDGBoxButtonType.Solid) {
                SDGColor.Neutral200
            } else SDGColor.Transparent,
            onClick = {},
            enable = status != SDGSampleStatus.DISABLED,
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
