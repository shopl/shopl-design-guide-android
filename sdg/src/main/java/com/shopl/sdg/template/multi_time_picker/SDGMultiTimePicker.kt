package com.shopl.sdg.template.multi_time_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.number_picker.SDGNumberPicker
import com.shopl.sdg.component.number_picker.SDGNumberPickerOption
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing12
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing16
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing20
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing24
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing28
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R
import kotlinx.collections.immutable.toPersistentList

private val HourRange = (0..23).toPersistentList()
private val MinuteRange = (0..59).toPersistentList()
private val TimeFieldHeight = 40.dp

/**
 * SDG - Template - Multi Time Picker
 *
 * 시각 및 시간을 선택하는 바텀 팝업으로 노출되는 템플릿
 * [type]에 따라 단일 시각 또는 시작/종료 시각 선택으로 표시합니다.
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=8099-26608&m=dev">Figma</a>
 */
@Composable
fun SDGMultiTimePicker(
    type: SDGMultiTimePickerType,
    onClickCancel: () -> Unit,
    onClickConfirm: (SDGMultiTimePickerType) -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    cancelLabel: String = stringResource(id = R.string.dialog_common_btn_cancel),
    confirmLabel: String = stringResource(id = R.string.dialog_common_btn_ok),
) {
    var pendingType by remember(type) { mutableStateOf(type.normalizeToClockRange()) }
    var selectedTarget by remember(type) { mutableStateOf(TimeTarget.Start) }
    val selectedTime = pendingType.selectedTime(selectedTarget)

    FixedBottomPopup(
        singleButton = false,
        onClickConfirm = { onClickConfirm(pendingType) },
        modifier = modifier,
        cancelLabel = cancelLabel,
        confirmLabel = confirmLabel,
        onClickCancel = onClickCancel,
        contentPadding = PaddingValues(
            start = Spacing20,
            top = Spacing28,
            end = Spacing20,
            bottom = Spacing24,
        ),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Spacing24),
        ) {
            if (!title.isNullOrBlank() || pendingType is SDGMultiTimePickerType.Multi) {
                Column(verticalArrangement = Arrangement.spacedBy(Spacing16)) {
                    if (!title.isNullOrBlank()) {
                        SDGText(
                            text = title,
                            typography = SDGTypography.Title2SB,
                            textColor = SDGColor.Neutral700,
                        )
                    }

                    when (val currentType = pendingType) {
                        is SDGMultiTimePickerType.Multi -> TimeRangeFields(
                            startTime = currentType.startTime,
                            endTime = currentType.endTime,
                            selectedTarget = selectedTarget,
                            onClickTarget = { selectedTarget = it },
                        )

                        is SDGMultiTimePickerType.Single -> Unit
                    }
                }
            }

            key(selectedTarget) {
                SDGNumberPicker(
                    option = SDGNumberPickerOption.TwoOption(
                        left = SDGNumberPickerOption.TwoOption.OptionModel(
                            value = selectedTime.hour,
                            rangeList = HourRange,
                            onValueChange = { hour ->
                                pendingType = pendingType.updateSelectedTime(selectedTarget) {
                                    it.copy(hour = hour)
                                }
                            },
                            supportsInfiniteScroll = true,
                        ),
                        right = SDGNumberPickerOption.TwoOption.OptionModel(
                            value = selectedTime.min,
                            rangeList = MinuteRange,
                            onValueChange = { minute ->
                                pendingType = pendingType.updateSelectedTime(selectedTarget) {
                                    it.copy(min = minute)
                                }
                            },
                            supportsInfiniteScroll = true,
                        ),
                    )
                )
            }
        }
    }
}

@Composable
private fun TimeRangeFields(
    startTime: HourMin,
    endTime: HourMin,
    selectedTarget: TimeTarget,
    onClickTarget: (TimeTarget) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing4),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TimeField(
            modifier = Modifier.weight(1f),
            time = startTime,
            selected = selectedTarget == TimeTarget.Start,
            onClick = { onClickTarget(TimeTarget.Start) },
        )

        SDGText(
            text = "~",
            typography = SDGTypography.Body1R,
            textColor = SDGColor.Neutral700,
        )

        TimeField(
            modifier = Modifier.weight(1f),
            time = endTime,
            selected = selectedTarget == TimeTarget.End,
            onClick = { onClickTarget(TimeTarget.End) },
        )
    }
}

@Composable
private fun TimeField(
    time: HourMin,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(TimeFieldHeight)
            .clip(SDGCornerRadius.BoxRadius.Radius12)
            .background(SDGColor.Neutral50)
            .semantics { this.selected = selected }
            .clickable(
                role = Role.Button,
                onClick = onClick,
            )
            .padding(horizontal = Spacing12),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SDGText(
            text = time.toTimeText(),
            typography = SDGTypography.Body1R,
            textColor = if (selected) SDGColor.Primary300 else SDGColor.Neutral700,
        )
    }
}

@Preview(name = "Multi", widthDp = 374, heightDp = 790)
@Composable
private fun PreviewSDGMultiTimePicker() {
    SDGMultiTimePicker(
        title = "Title",
        type = SDGMultiTimePickerType.Multi(
            startTime = HourMin(hour = 9),
            endTime = HourMin(hour = 18),
        ),
        onClickCancel = {},
        onClickConfirm = {},
    )
}

@Preview(name = "Single", widthDp = 374, heightDp = 790)
@Composable
private fun PreviewSDGSingleTimePicker() {
    SDGMultiTimePicker(
        title = "Title",
        type = SDGMultiTimePickerType.Single(time = HourMin(hour = 9)),
        onClickCancel = {},
        onClickConfirm = {},
    )
}
