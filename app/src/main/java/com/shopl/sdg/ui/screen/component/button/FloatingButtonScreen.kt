package com.shopl.sdg.ui.screen.component.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.R
import com.shopl.sdg.component.button.floating.SDGFloatingButton
import com.shopl.sdg.enums.SDGSampleStatus
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.ui.base.SDGSampleBaseComponentScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG Sample App - Component - Button - FloatingButton
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=22804-3444&m=dev">Figma</a>
 */
@Composable
internal fun FloatingButtonScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {

    val specs = persistentListOf(
        SDGSampleBaseTabItem(
            title = "One size",
            item = Unit
        ),
    )

    SDGSampleBaseComponentScaffold<Unit, Unit>(
        componentName = ComponentScene.Button.FloatingButton.displayLabel,
        componentDescription = "화면 우측 하단에 고정으로 위치하며, 생성&추가 등의 동작을 위한 버튼 컴포넌트",
        specs = specs,
        guideLineDescriptions = persistentListOf(
            "지정된 컬러를 사용합니다."
        ),
        componentContent = { _, _, currentStatus ->
            ComponentContent(
                status = currentStatus
            )
        },
        onClickBack = onClickBack,
        onClickMenu = onClickMenu
    )
}

@Composable
private fun ComponentContent(
    status: SDGSampleStatus,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = SDGSpacing.Spacing40,
                horizontal = SDGSpacing.Spacing16
            ),
        contentAlignment = Alignment.Center
    ) {
        SDGFloatingButton(
            icon = R.drawable.icon_sample,
            onClick = { },
            enabled = status == SDGSampleStatus.DEFAULT,
        )
    }
}

@Preview
@Composable
private fun PreviewFloatingButtonScreen() {
    ShoplDesignGuideTheme {
        FloatingButtonScreen(
            onClickBack = {},
            onClickMenu = {}
        )
    }
}
