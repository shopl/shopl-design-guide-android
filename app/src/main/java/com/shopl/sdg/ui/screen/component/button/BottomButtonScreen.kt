package com.shopl.sdg.ui.screen.component.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.button.bottom.SDGBottomButton
import com.shopl.sdg.component.button.bottom.SDGBottomButtonType
import com.shopl.sdg.component.util.SDGBottomButton
import com.shopl.sdg.enums.SDGSampleStatus
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.ui.base.SDGSampleBaseComponentScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG Sample App - Component - Button - BottomButton
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=22804-3311&m=dev">Figma</a>
 */

private const val SPEC_FULL = 0
private const val SPEC_ADAPTIVE = 1

@Composable
internal fun BottomButtonScreen() {

    val types = persistentListOf(
        SDGSampleBaseTabItem(
            title = SDGBottomButtonType.POSITIVE.typeName,
            item = SDGBottomButtonType.POSITIVE
        ),
        SDGSampleBaseTabItem(
            title = SDGBottomButtonType.NORMAL.typeName,
            item = SDGBottomButtonType.NORMAL
        ),
        SDGSampleBaseTabItem(
            title = SDGBottomButtonType.NEGATIVE.typeName,
            item = SDGBottomButtonType.NEGATIVE
        ),
        SDGSampleBaseTabItem(
            title = SDGBottomButtonType.NORMAL_DARK.typeName,
            item = SDGBottomButtonType.NORMAL_DARK
        ),
    )

    val specs = persistentListOf(
        SDGSampleBaseTabItem(
            title = "Full",
            item = SPEC_FULL
        ),
        SDGSampleBaseTabItem(
            title = "Adaptive",
            item = SPEC_ADAPTIVE
        ),
    )

    SDGSampleBaseComponentScaffold(
        componentName = ComponentScene.Button.BottomButton.displayLabel,
        componentDescription = "화면 하단에 고정으로 위치한 버튼 컴포넌트",
        types = types,
        specs = specs,
        guideLineDescriptions = persistentListOf(
            "지정된 컬러를 사용합니다."
        ),
        componentContent = { currentType, currentSpec, currentStatus ->
            if (currentType != null && currentSpec != null) {
                ComponentContent(
                    type = currentType,
                    spec = currentSpec,
                    status = currentStatus
                )
            }
        }
    )
}

@Composable
private fun ComponentContent(
    type: SDGBottomButtonType,
    spec: Int,
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
        when (spec) {
            SPEC_FULL -> {
                SDGBottomButton(
                    title = "Label",
                    onClick = {},
                    type = type,
                    enabled = when (status) {
                        SDGSampleStatus.DEFAULT -> true
                        SDGSampleStatus.DISABLED -> false
                    },
                    marginValues = PaddingValues(horizontal = SDGSpacing.Spacing16)
                )
            }

            SPEC_ADAPTIVE -> {
                Box(
                    modifier = Modifier.width(80.dp)
                ) {
                    SDGBottomButton(
                        align = Alignment.Center,
                        title = "Label",
                        type = type,
                        enabled = when (status) {
                            SDGSampleStatus.DEFAULT -> true
                            SDGSampleStatus.DISABLED -> false
                        },
                        onClick = {}
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
        BottomButtonScreen()
    }
}
