package com.shopl.sdg.ui.screen.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.time_select_input.SDGTimeSelectInput
import com.shopl.sdg.component.time_select_input.SDGTimeSelectInputField
import com.shopl.sdg.component.time_select_input.SDGTimeSelectInputState
import com.shopl.sdg.enums.SDGSampleStatus
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.ui.base.SDGSampleBaseComponentScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG Sample App - Component - Time Select Input
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=27321-6608&m=dev">Figma</a>
 */
@Composable
internal fun TimeSelectInputScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    val types = persistentListOf(
        SDGSampleBaseTabItem(
            title = SDGTimeSelectInputField.LIGHT_GRAY.typeName,
            item = SDGTimeSelectInputField.LIGHT_GRAY,
        ),
        SDGSampleBaseTabItem(
            title = SDGTimeSelectInputField.WHITE.typeName,
            item = SDGTimeSelectInputField.WHITE,
        ),
    )

    SDGSampleBaseComponentScaffold<SDGTimeSelectInputField, Unit>(
        componentName = ComponentScene.TimeSelectInput.displayLabel,
        componentDescription = "시작 시간과 종료 시간을 지정하여 시간 범위를 선택하는 컴포넌트",
        types = types,
        guideLineDescriptions = persistentListOf(
            "Input Field는 LightGray / White 2가지 유형으로 구성됩니다.",
            "Error 상태에서는 Input Field 유형과 관계없이 Error 배경을 사용합니다.",
            "Disabled 상태에서는 모든 터치 액션을 차단합니다.",
        ),
        componentContent = { currentType, _, currentStatus ->
            currentType?.let { inputField ->
                TimeSelectInputContent(
                    inputField = inputField,
                    status = currentStatus,
                )
            }
        },
        onClickBack = onClickBack,
        onClickMenu = onClickMenu,
    )
}

@Composable
private fun TimeSelectInputContent(
    inputField: SDGTimeSelectInputField,
    status: SDGSampleStatus,
) {
    SDGTimeSelectInput(
        startTime = "09:00",
        endTime = "18:00",
        state = when (status) {
            SDGSampleStatus.DEFAULT -> SDGTimeSelectInputState.Selected
            SDGSampleStatus.DISABLED -> SDGTimeSelectInputState.Disabled
        },
        inputField = inputField,
        marginValues = PaddingValues(
            horizontal = SDGSpacing.Spacing16,
            vertical = SDGSpacing.Spacing40,
        ),
    )
}

@Preview
@Composable
private fun PreviewTimeSelectInputScreen() {
    ShoplDesignGuideTheme {
        TimeSelectInputScreen(
            onClickBack = {},
            onClickMenu = {},
        )
    }
}
