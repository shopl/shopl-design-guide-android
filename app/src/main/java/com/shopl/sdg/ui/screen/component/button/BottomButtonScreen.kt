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
import com.shopl.sdg.component.button.bottom.SDGBottomButtonSpec
import com.shopl.sdg.component.button.bottom.SDGBottomButtonType
import com.shopl.sdg.component.button.bottom.SDGBottomButtonSize
import com.shopl.sdg.component.button.bottom.SDGBottomButtonStyle
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
import com.shopl.sdg.component.button.bottom.extension.SDGBottomButton as LegacyBottomButton

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
            title = SDGBottomButtonStyle.Primary.styleName,
            item = SDGBottomButtonStyle.Primary
        ),
        SDGSampleBaseTabItem(
            title = SDGBottomButtonStyle.Neutral.styleName,
            item = SDGBottomButtonStyle.Neutral
        ),
        SDGSampleBaseTabItem(
            title = SDGBottomButtonStyle.Point.styleName,
            item = SDGBottomButtonStyle.Point
        ),
        SDGSampleBaseTabItem(
            title = SDGBottomButtonStyle.Normal.styleName,
            item = SDGBottomButtonStyle.Normal
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
        guideLineDescriptions = persistentListOf(
            "Primary, Neutral, Point, Normal 유형별 기본·눌림·비활성 컬러를 사용합니다.",
            "높이는 50dp, 모서리 반경은 25dp이며, 내부 좌우 여백은 16dp입니다.",
            "버튼명은 한 줄로 표시하고 가용 너비를 넘으면 말줄임합니다.",
            "Full은 가용 너비를 채우고, Adaptive는 라벨 길이에 맞추되 최소 80dp를 유지합니다.",
            "좌우 12dp, 하단 10dp를 띄워 하단에 배치합니다.",
            "두 버튼은 서로 다른 유형을 사용하며, Full은 같은 너비로, Adaptive는 우측 정렬로 배치합니다.",
            "버튼을 눌러 눌림 색상과 클릭 횟수를 확인할 수 있습니다.",
            "기존 일반·BoxScope·RowScope API도 같은 구현에 연결되며 클릭 횟수로 동작을 확인합니다.",
        ),
        componentContent = { currentType, currentSpec, currentStatus ->
            if (currentType != null && currentSpec != null) {
                ComponentContent(
                    style = currentType,
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
    style: SDGBottomButtonStyle,
    size: SDGBottomButtonSize,
    status: SDGSampleStatus,
) {
    var clickCount by remember { mutableIntStateOf(0) }
    val enabled = status != SDGSampleStatus.DISABLED
    val secondaryStyle = if (style == SDGBottomButtonStyle.Normal) {
        SDGBottomButtonStyle.Neutral
    } else {
        SDGBottomButtonStyle.Normal
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
                style = style,
                enabled = enabled,
            )
        }
        Box(modifier = areaModifier, contentAlignment = Alignment.BottomEnd) {
            SDGBottomButton(
                label = "한 줄로 표시할 수 있는 너비를 넘은 긴 버튼명은 말줄임으로 표시합니다",
                onClick = { clickCount++ },
                size = size,
                style = style,
                enabled = enabled,
            )
        }
        Box(modifier = areaModifier, contentAlignment = Alignment.BottomEnd) {
            Row(horizontalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing10)) {
                Box(modifier = if (size == SDGBottomButtonSize.Full) Modifier.weight(1f) else Modifier) {
                    SDGBottomButton(
                        label = "이전",
                        onClick = { clickCount++ },
                        size = size,
                        style = secondaryStyle,
                        enabled = enabled,
                    )
                }

                Box(modifier = if (size == SDGBottomButtonSize.Full) Modifier.weight(1f) else Modifier) {
                    SDGBottomButton(
                        label = "다음",
                        onClick = { clickCount++ },
                        size = size,
                        style = style,
                        enabled = enabled,
                    )
                }
            }
        }
        LegacyBottomButtonSamples(
            enabled = enabled,
            onClick = { clickCount++ },
            areaModifier = areaModifier,
        )
    }
}

@Composable
@Suppress("DEPRECATION")
private fun LegacyBottomButtonSamples(
    enabled: Boolean,
    onClick: () -> Unit,
    areaModifier: Modifier,
) {
    SDGText(
        modifier = Modifier.padding(horizontal = SDGSpacing.Spacing12),
        text = "기존 API 호환",
        textColor = SDGColor.Neutral700,
        typography = SDGTypography.Body2R,
    )
    Box(modifier = areaModifier, contentAlignment = Alignment.BottomEnd) {
        SDGBottomButton(
            title = "기존 일반 API",
            enabled = enabled,
            onClick = onClick,
        )
    }
    Box(modifier = areaModifier) {
        LegacyBottomButton(
            align = Alignment.BottomEnd,
            title = "기존 BoxScope API",
            spec = SDGBottomButtonSpec.ADAPTIVE,
            type = SDGBottomButtonType.NEGATIVE,
            enabled = enabled,
            onClick = onClick,
        )
    }
    Box(modifier = areaModifier, contentAlignment = Alignment.BottomEnd) {
        Row(horizontalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing10)) {
            LegacyBottomButton(
                weight = 1f,
                title = "기존 Row 1",
                type = SDGBottomButtonType.NORMAL,
                enabled = enabled,
                onClick = onClick,
            )
            LegacyBottomButton(
                weight = 1f,
                title = "기존 Row 2",
                type = SDGBottomButtonType.NORMAL_DARK,
                enabled = enabled,
                onClick = onClick,
            )
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
