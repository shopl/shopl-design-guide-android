package com.shopl.sdg.ui.screen.component.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.button.bottom.SDGBottomButton
import com.shopl.sdg.component.button.bottom.SDGBottomButtonSpec
import com.shopl.sdg.component.button.bottom.SDGBottomButtonType
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

@Composable
internal fun BottomButtonScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {

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
            title = SDGBottomButtonSpec.FULL.specName,
            item = SDGBottomButtonSpec.FULL
        ),
        SDGSampleBaseTabItem(
            title = SDGBottomButtonSpec.ADAPTIVE.specName,
            item = SDGBottomButtonSpec.ADAPTIVE
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
    size: SDGBottomButtonSpec,
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
        when (size) {
            SDGBottomButtonSpec.FULL -> {
                SDGBottomButton(
                    title = "Label",
                    onClick = {},
                    spec = size,
                    type = type,
                    enabled = when (status) {
                        SDGSampleStatus.DEFAULT -> true
                        SDGSampleStatus.DISABLED -> false
                    },
                    marginValues = PaddingValues(horizontal = SDGSpacing.Spacing12)
                )
            }

            SDGBottomButtonSpec.ADAPTIVE -> {
                SDGBottomButton(
                    title = "Label",
                    onClick = {},
                    spec = size,
                    type = type,
                    enabled = when (status) {
                        SDGSampleStatus.DEFAULT -> true
                        SDGSampleStatus.DISABLED -> false
                    },
                )
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
