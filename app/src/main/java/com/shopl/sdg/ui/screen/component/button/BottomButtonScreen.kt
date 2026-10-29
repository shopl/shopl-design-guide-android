package com.shopl.sdg.ui.screen.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.button.bottom.SDGBottomButton
import com.shopl.sdg.component.button.bottom.SDGBottomButtonSize
import com.shopl.sdg.component.button.bottom.model.SDGBottomButtonType
import com.shopl.sdg.enums.SDGSampleStatus
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.ui.base.SDGSampleBaseComponentScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.persistentListOf

private val BottomButtonSampleAreaHeight = 100.dp

/**
 * SDG Sample App - Component - Button - BottomButton
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19481-1283&m=dev">Figma</a>
 */
@Composable
internal fun BottomButtonScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {

    val types = persistentListOf(
        SDGSampleBaseTabItem(
            title = SDGBottomButtonType.Primary.typeName,
            item = SDGBottomButtonType.Primary
        ),
        SDGSampleBaseTabItem(
            title = SDGBottomButtonType.Neutral.typeName,
            item = SDGBottomButtonType.Neutral
        ),
        SDGSampleBaseTabItem(
            title = SDGBottomButtonType.Point.typeName,
            item = SDGBottomButtonType.Point
        ),
        SDGSampleBaseTabItem(
            title = SDGBottomButtonType.Normal.typeName,
            item = SDGBottomButtonType.Normal
        ),
    )

    val specs = persistentListOf(
        SDGSampleBaseTabItem(
            title = SDGBottomButtonSize.Full.sizeName,
            item = SDGBottomButtonSize.Full
        ),
        SDGSampleBaseTabItem(
            title = SDGBottomButtonSize.Adaptive.sizeName,
            item = SDGBottomButtonSize.Adaptive
        ),
    )

    SDGSampleBaseComponentScaffold(
        componentName = ComponentScene.Button.BottomButton.displayLabel,
        componentDescription = "화면 하단에 고정으로 위치한 버튼 컴포넌트",
        types = types,
        specs = specs,
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
    type: SDGBottomButtonType,
    size: SDGBottomButtonSize,
    status: SDGSampleStatus,
) {
    var clickCount by remember { mutableIntStateOf(0) }
    val enabled = status != SDGSampleStatus.DISABLED
    val secondaryType = if (type == SDGBottomButtonType.Normal) {
        SDGBottomButtonType.Neutral
    } else {
        SDGBottomButtonType.Normal
    }
    val areaModifier = Modifier
        .fillMaxWidth()
        .height(BottomButtonSampleAreaHeight)
        .background(SDGColor.Neutral100)
        .padding(
            start = SDGSpacing.Spacing12,
            end = SDGSpacing.Spacing12,
            bottom = SDGSpacing.Spacing10,
        )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = SDGSpacing.Spacing16),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing12),
    ) {
        SDGText(
            modifier = Modifier.padding(horizontal = SDGSpacing.Spacing12),
            text = "버튼 클릭: $clickCount",
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body2R,
        )
        Box(modifier = areaModifier, contentAlignment = Alignment.BottomEnd) {
            SDGBottomButton(
                label = "Label",
                onClick = { clickCount++ },
                size = size,
                type = type,
                enabled = enabled,
            )
        }
        Box(modifier = areaModifier, contentAlignment = Alignment.BottomEnd) {
            SDGBottomButton(
                label = "한 줄로 표시할 수 있는 너비를 넘은 긴 버튼명은 말줄임으로 표시합니다",
                onClick = { clickCount++ },
                size = size,
                type = type,
                enabled = enabled,
            )
        }
        Box(modifier = areaModifier, contentAlignment = Alignment.BottomEnd) {
            Row(horizontalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing10)) {
                val buttonModifier = Modifier.then(
                    if (size == SDGBottomButtonSize.Full) Modifier.weight(1f, fill = true) else Modifier
                )

                Box(modifier = buttonModifier) {
                    SDGBottomButton(
                        label = "이전",
                        onClick = { clickCount++ },
                        size = size,
                        type = secondaryType,
                        enabled = enabled,
                    )
                }

                Box(modifier = buttonModifier) {
                    SDGBottomButton(
                        label = "다음",
                        onClick = { clickCount++ },
                        size = size,
                        type = type,
                        enabled = enabled,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewBottomButtonScreen() {
    ShoplDesignGuideTheme {
        BottomButtonScreen(
            onClickBack = {},
            onClickMenu = {}
        )
    }
}
