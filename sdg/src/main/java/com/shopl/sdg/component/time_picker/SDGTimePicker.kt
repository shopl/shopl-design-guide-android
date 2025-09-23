package com.shopl.sdg.component.time_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.number_picker.SDGNumberPicker
import com.shopl.sdg.component.time_picker.SDGTimePickerOption.OneOption
import com.shopl.sdg.component.time_picker.SDGTimePickerOption.TwoOption
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

private val DEFAULT_PICKER_ITEM_HEIGHT = 40.dp

/**
 * SDG - Time Picker
 *
 * 스피너 형식의 숫자 선택기를 활용해 시간 값을 선택하는 컴포넌트
 *
 * @param option 타임 피커 타입 [SDGTimePickerOption]
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=8610-20836&m=dev">Figma</a>
 */
@Composable
fun SDGTimePicker(option: SDGTimePickerOption) {
    when (option) {
        is OneOption -> SDGOneOptionTimePicker(option)
        is TwoOption -> SDGTwoOptionTimePicker(option)
    }
}

/**
 * 1개의 값만 선택
 */
@Composable
private fun SDGOneOptionTimePicker(option: OneOption) {
    SDGNumberPicker(
        value = option.value,
        range = option.range,
        onValueChange = option.onValueChange,
        width = option.width,
        isEditMode = true
    )
}

/**
 * 2개의 조합된 값 선택
 */
@Composable
private fun SDGTwoOptionTimePicker(option: TwoOption) {
    Box(contentAlignment = Alignment.Center) {

        HighlightingBox()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing4)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                SDGNumberPicker(
                    value = option.left.value,
                    range = option.left.range,
                    onValueChange = option.left.onValueChange,
                    isEditMode = true,
                )
            }

            SDGText(
                text = ":",
                typography = SDGTypography.Body1R,
                textColor = SDGColor.Neutral700,
            )

            Box(modifier = Modifier.weight(1f)) {
                SDGNumberPicker(
                    value = option.right.value,
                    range = option.right.range,
                    onValueChange = option.right.onValueChange,
                    isEditMode = true
                )
            }
        }
    }
}

@Composable
private fun HighlightingBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(DEFAULT_PICKER_ITEM_HEIGHT)
            .clip(RoundedCornerShape(SDGCornerRadius.Radius8))
            .background(SDGColor.Neutral150)
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGTimePickerOneOption() {
    var value by remember { mutableIntStateOf(10) }

    SDGTimePicker(
        option = OneOption(
            value = value,
            range = 0..23,
            onValueChange = { value = it },
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGTimePickerTwoOption() {
    var hour by remember { mutableIntStateOf(11) }
    var minute by remember { mutableIntStateOf(30) }

    SDGTimePicker(
        option = TwoOption(
            left = TwoOption.OptionModel(
                value = hour,
                range = 0..23,
                onValueChange = { hour = it },
            ),
            right = TwoOption.OptionModel(
                value = minute,
                range = 0..59,
                onValueChange = { minute = it },
            )
        )
    )
}
