package com.shopl.sdg.ui.screen.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
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
import com.shopl.sdg_resource.R
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
            title = SDGTimeSelectInputField.LightGray.fieldName,
            item = SDGTimeSelectInputField.LightGray,
        ),
        SDGSampleBaseTabItem(
            title = SDGTimeSelectInputField.White.fieldName,
            item = SDGTimeSelectInputField.White,
        ),
    )

    SDGSampleBaseComponentScaffold<SDGTimeSelectInputField, Unit>(
        componentName = ComponentScene.TimeSelectInput.displayLabel,
        componentDescription = "시작 시간과 종료 시간을 지정하여 특정 시간(기간)의 범위를 선택하는 컴포넌",
        types = types,
        guideLineDescriptions = persistentListOf("시간 선택 템플릿 사용할 땐 시작/끝 시간은 한 번에 입력합니다."),
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
        startTimePlaceholder = stringResource(id = R.string.dialog_date_picker_start),
        endTimePlaceholder = stringResource(id = R.string.dialog_date_picker_end),
        inputField = inputField,
        marginValues = PaddingValues(
            horizontal = SDGSpacing.Spacing16,
            vertical = SDGSpacing.Spacing40,
        ),
        onClick = {},
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
