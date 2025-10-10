package com.shopl.sdg.component.time_select_input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

/**
 * SDG - Component - Time Select Input
 *
 * 시간을 입력하는 컴포넌트
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=18221-5871&m=dev">Figma</a>
 */
@Composable
fun SDGTimeSelectInput(
    startTime: String?,
    endTime: String?,
    startTimePlaceholder: String = stringResource(id = R.string.dialog_date_picker_start),
    endTimePlaceholder: String = stringResource(id = R.string.dialog_date_picker_end),
    state: SDGTimeSelectInputState = SDGTimeSelectInputState.Default,
    marginValues: PaddingValues = PaddingValues(),
    backgroundColor: Color = SDGColor.Neutral50,
    onClick: (isStart: Boolean) -> Unit = { },
) {
    Row(
        modifier = Modifier
            .padding(marginValues)
            .fillMaxWidth()
            .clip(shape = SDGCornerRadius.BoxRadius.Radius12)
            .then(
                when (state) {
                    SDGTimeSelectInputState.Error -> Modifier.background(
                        color = SDGColor.Red300_a10,
                        shape = SDGCornerRadius.BoxRadius.Radius12,
                    )
                    else -> Modifier.background(
                        color = backgroundColor,
                        shape = SDGCornerRadius.BoxRadius.Radius12,
                    )
                }
            )
            .padding(
                vertical = SDGSpacing.Spacing10,
                horizontal = SDGSpacing.Spacing12
            ),
        horizontalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing12)
    ) {
        SDGText(
            modifier = Modifier
                .weight(1f)
                .clickable(false) { onClick(true) },
            text = if(!startTime.isNullOrEmpty()) startTime else startTimePlaceholder,
            textColor = when(state) {
                SDGTimeSelectInputState.Disabled -> SDGColor.Neutral300
                else -> {
                    if(!startTime.isNullOrEmpty()) {
                        SDGColor.Neutral700
                    } else {
                        SDGColor.Neutral300
                    }
                }
            },
            typography = SDGTypography.Body1R,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        SDGText(
            text = "~",
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body1R,
        )

        SDGText(
            modifier = Modifier
                .weight(1f)
                .clickable(false) { onClick(false) },
            text = if(!endTime.isNullOrEmpty()) endTime else endTimePlaceholder,
            textColor = when(state) {
                SDGTimeSelectInputState.Disabled -> SDGColor.Neutral300
                else -> {
                    if(!endTime.isNullOrEmpty()) {
                        SDGColor.Neutral700
                    } else {
                        SDGColor.Neutral300
                    }
                }
            },
            typography = SDGTypography.Body1R,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
private fun PreviewSDGTimeSelectInput() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = SDGColor.Neutral0)
            .padding(SDGSpacing.Spacing20),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing20)
    ) {
        SDGTimeSelectInput(
            startTime = null,
            endTime = null,
            state = SDGTimeSelectInputState.Default,
        )
        SDGTimeSelectInput(
            startTime = "12:00",
            endTime = "13:00",
            state = SDGTimeSelectInputState.Default,
        )
        SDGTimeSelectInput(
            startTime = "12:00",
            endTime = "13:00",
            state = SDGTimeSelectInputState.Disabled,
        )
        SDGTimeSelectInput(
            startTime = "12:00",
            endTime = "13:00",
            state = SDGTimeSelectInputState.Error,
        )
    }
}