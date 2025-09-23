package com.shopl.sdg.component.time_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

private val DEFAULT_TWO_OPTION_PICKER_WIDTH = 104.dp
private val DEFAULT_PICKER_ITEM_HEIGHT = 40.dp

/**
 * SDG - Time Picker
 *
 * 스피너 형식의 숫자 선택기를 활용해 시간 값을 선택하는 컴포넌트
 *
 * @param option 렌더링할 타임 피커 구성 값
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

@Composable
private fun SDGOneOptionTimePicker(option: OneOption) {
    SDGNumberPicker(
        value = option.value,
        range = option.range,
        onValueChange = option.onValueChange,
        width = option.width,
        isEditMode = option.isEditMode
    )
}

@Composable
private fun SDGTwoOptionTimePicker(option: TwoOption) {
    val firstWidth = if (option.first.width == 0.dp) DEFAULT_TWO_OPTION_PICKER_WIDTH else option.first.width
    val secondWidth = if (option.second.width == 0.dp) DEFAULT_TWO_OPTION_PICKER_WIDTH else option.second.width

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(option.spacing)
    ) {
        SDGNumberPicker(
            value = option.first.value,
            range = option.first.range,
            onValueChange = option.first.onValueChange,
            width = firstWidth,
            isEditMode = option.first.isEditMode
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(DEFAULT_PICKER_ITEM_HEIGHT)
                .clip(RoundedCornerShape(SDGCornerRadius.Radius8))
                .background(SDGColor.Neutral150)
        ) {
            SDGText(
                text = option.dividerText,
                typography = SDGTypography.Title2R,
                textColor = SDGColor.Neutral700,
                modifier = Modifier.padding(horizontal = option.dividerHorizontalPadding)
            )
        }

        SDGNumberPicker(
            value = option.second.value,
            range = option.second.range,
            onValueChange = option.second.onValueChange,
            width = secondWidth,
            isEditMode = option.second.isEditMode
        )
    }
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
            isEditMode = true
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
            first = SDGTimePickerColumnOption(
                value = hour,
                range = 0..23,
                onValueChange = { hour = it },
                isEditMode = true
            ),
            second = SDGTimePickerColumnOption(
                value = minute,
                range = 0..59,
                onValueChange = { minute = it },
                isEditMode = true
            )
        )
    )
}
