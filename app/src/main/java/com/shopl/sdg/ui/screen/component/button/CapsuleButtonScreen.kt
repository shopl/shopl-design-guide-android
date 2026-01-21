package com.shopl.sdg.ui.screen.component.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.button.capsule.SDGCapsuleButton
import com.shopl.sdg.component.button.capsule.SDGCapsuleButtonSize
import com.shopl.sdg.component.button.capsule.SDGCapsuleButtonType
import com.shopl.sdg.enums.SDGSampleStatus
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.ui.base.SDGSampleBaseComponentScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG Sample App - Component - Button - CapsuleButton
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=22804-3378&m=dev">Figma</a>
 */
@Composable
internal fun CapsuleButtonScreen() {

    val types = persistentListOf<SDGSampleBaseTabItem<SDGCapsuleButtonType>>(
        SDGSampleBaseTabItem(
            title = SDGCapsuleButtonType.Solid.typeName,
            item = SDGCapsuleButtonType.Solid
        ),
        SDGSampleBaseTabItem(
            title = SDGCapsuleButtonType.Line(SDGColor.Neutral350).typeName,
            item = SDGCapsuleButtonType.Line(SDGColor.Neutral350)
        ),
    )

    val specs = persistentListOf<SDGSampleBaseTabItem<SDGCapsuleButtonSize>>(
        SDGSampleBaseTabItem(
            title = SDGCapsuleButtonSize.Large.sizeName,
            item = SDGCapsuleButtonSize.Large
        ),
        SDGSampleBaseTabItem(
            title = SDGCapsuleButtonSize.Medium.sizeName,
            item = SDGCapsuleButtonSize.Medium
        ),
        SDGSampleBaseTabItem(
            title = SDGCapsuleButtonSize.Small.sizeName,
            item = SDGCapsuleButtonSize.Small
        ),
        SDGSampleBaseTabItem(
            title = SDGCapsuleButtonSize.XSmall.sizeName,
            item = SDGCapsuleButtonSize.XSmall
        ),
    )

    SDGSampleBaseComponentScaffold(
        componentName = ComponentScene.Button.CapsuleButton.displayLabel,
        componentDescription = "화면 내에 배치하여 사용하는 좌우가 동그란 형태의 버튼 컴포넌트",
        types = types,
        specs = specs,
        guideLineDescriptions = persistentListOf(
            "SDG 컬러 시스템의 모든 컬러 적용 가능합니다."
        ),
        componentContent = { currentType, currentSpec, currentStatus ->
            ComponentContent(
                type = currentType,
                size = currentSpec,
                status = currentStatus
            )
        }
    )
}

@Composable
private fun ComponentContent(
    type: SDGCapsuleButtonType,
    size: SDGCapsuleButtonSize,
    status : SDGSampleStatus,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = SDGSpacing.Spacing40,
            ),
        contentAlignment = Alignment.Center
    ) {
        SDGCapsuleButton(
            size = size,
            type = type,
            label = "Label",
            labelColor = if(type == SDGCapsuleButtonType.Solid) {
                SDGColor.Neutral0
            } else SDGColor.Neutral600,
            backgroundColor = if(type == SDGCapsuleButtonType.Solid) {
                SDGColor.Neutral600
            } else SDGColor.Transparent,
            onClick = {},
            enable = status != SDGSampleStatus.DISABLED,
        )
    }
}

@Preview
@Composable
private fun PreviewCapsuleButtonScreen() {
    ShoplDesignGuideTheme {
        CapsuleButtonScreen()
    }
}