package com.shopl.sdg.ui.screen.template

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.button.box.SDGBoxButton
import com.shopl.sdg.component.button.box.SDGBoxButtonSize
import com.shopl.sdg.component.button.box.SDGBoxButtonType
import com.shopl.sdg.enums.SDGSampleStatus
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.scene.TemplateScene
import com.shopl.sdg.template.multi_time_picker.HourMin
import com.shopl.sdg.template.multi_time_picker.SDGMultiTimePicker
import com.shopl.sdg.ui.base.SDGSampleBaseComponentScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG Sample App - Template - Multi Time Picker
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=8099-26608&m=dev">Figma</a>
 */
@Composable
internal fun MultiTimePickerScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    val types = persistentListOf(
        SDGSampleBaseTabItem(title = "Single", item = false),
        SDGSampleBaseTabItem(title = "Multi", item = true),
    )

    SDGSampleBaseComponentScaffold<Boolean, Unit>(
        componentName = TemplateScene.MultiTimePicker.displayLabel,
        componentDescription = "시각 또는 시작/종료 시간을 선택하는 하단 고정형 타임 피커 템플릿",
        types = types,
        guideLineDescriptions = persistentListOf(
            "단일 시각 또는 시작/종료 시간을 선택할 때 사용합니다.",
        ),
        componentContent = { isMulti, _, status ->
            isMulti?.let {
                MultiTimePickerContent(
                    isMulti = it,
                    enabled = status == SDGSampleStatus.DEFAULT,
                )
            }
        },
        onClickBack = onClickBack,
        onClickMenu = onClickMenu,
    )
}

@Composable
private fun MultiTimePickerContent(
    isMulti: Boolean,
    enabled: Boolean,
) {
    var isVisible by remember { mutableStateOf(false) }
    var startTime by remember { mutableStateOf(HourMin(hour = 9)) }
    var endTime by remember { mutableStateOf(HourMin(hour = 18)) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = SDGSpacing.Spacing40),
        contentAlignment = Alignment.Center,
    ) {
        SDGBoxButton(
            size = SDGBoxButtonSize.Medium,
            type = SDGBoxButtonType.Solid,
            label = "Open",
            labelColor = SDGColor.Neutral0,
            backgroundColor = SDGColor.Primary300,
            onClick = { isVisible = true },
            enable = enabled,
        )
    }

    if (isVisible) {
        SDGMultiTimePicker(
            title = "시간 선택",
            startTime = startTime,
            endTime = endTime.takeIf { isMulti },
            onClickCancel = { isVisible = false },
            onClickConfirm = { selectedStartTime, selectedEndTime ->
                startTime = selectedStartTime
                selectedEndTime?.let { endTime = it }
                isVisible = false
            },
        )
    }
}

@Preview
@Composable
private fun PreviewMultiTimePickerScreen() {
    ShoplDesignGuideTheme {
        MultiTimePickerScreen(
            onClickBack = {},
            onClickMenu = {},
        )
    }
}
