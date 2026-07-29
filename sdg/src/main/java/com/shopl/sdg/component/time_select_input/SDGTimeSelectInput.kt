package com.shopl.sdg.component.time_select_input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.time_select_input.preview.SDGTimeSelectInputPreviewParameterProvider
import com.shopl.sdg.component.time_select_input.preview.SDGTimeSelectInputPreviewParams
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

private const val SDGTimeSelectInputPeriodDivider = "~"
private const val SDGTimeSelectInputDisabledAlpha = 0.3f
private val SDGTimeSelectInputHeight = 40.dp
private val SDGTimeSelectInputDividerWidth = 12.dp

/**
 * SDG - Component - Time Select Input
 *
 * 시작 시간과 종료 시간을 지정하여 특정 시간(기간)의 범위를 선택하는 컴포넌트
 *
 * @version 2.3.40
 *
 * @param startTime 선택된 시작 시간
 * @param endTime 선택된 종료 시간
 * @param state 시간 선택 인풋 상태
 * @param placeholder 시작 및 종료 시간에 공통으로 표시할 안내 문구
 * @param inputField 인풋 필드 배경 타입
 * @param marginValues 컴포넌트 외부 여백
 * @param onClick 시작 또는 종료 시간 클릭 이벤트
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=27321-6608&m=dev">Figma</a>
 */
@Composable
fun SDGTimeSelectInput(
    startTime: String?,
    endTime: String?,
    state: SDGTimeSelectInputState,
    placeholder: String,
    inputField: SDGTimeSelectInputField,
    marginValues: PaddingValues,
    onClick: (isStart: Boolean) -> Unit,
) {
    val isEnabled = state != SDGTimeSelectInputState.Disabled
    val isStartPlaceholderVisible =
        state == SDGTimeSelectInputState.Default || startTime.isNullOrEmpty()
    val isEndPlaceholderVisible =
        state == SDGTimeSelectInputState.Default || endTime.isNullOrEmpty()
    val textColor = if (state == SDGTimeSelectInputState.Default) {
        SDGColor.Neutral350
    } else {
        SDGColor.Neutral700
    }
    val contentAlpha = if (state == SDGTimeSelectInputState.Disabled) {
        SDGTimeSelectInputDisabledAlpha
    } else {
        1f
    }
    val backgroundColor = if (state == SDGTimeSelectInputState.Error) {
        SDGColor.Red300_a10
    } else {
        inputField.backgroundColor
    }

    Row(
        modifier = Modifier
            .padding(marginValues)
            .fillMaxWidth()
            .height(SDGTimeSelectInputHeight)
            .clip(shape = SDGCornerRadius.BoxRadius.Radius12)
            .background(color = backgroundColor)
            .padding(horizontal = SDGSpacing.Spacing12),
        horizontalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing8),
    ) {
        SDGText(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .alpha(alpha = contentAlpha)
                .clickable(
                    hasRipple = false,
                    enabled = isEnabled,
                    onClick = { onClick(true) },
                )
                .wrapContentHeight(align = Alignment.CenterVertically),
            text = if (isStartPlaceholderVisible) placeholder else startTime.orEmpty(),
            textColor = textColor,
            typography = SDGTypography.Body1R,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        SDGText(
            modifier = Modifier
                .width(SDGTimeSelectInputDividerWidth)
                .fillMaxHeight()
                .alpha(alpha = contentAlpha)
                .wrapContentHeight(align = Alignment.CenterVertically),
            text = SDGTimeSelectInputPeriodDivider,
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body1R,
            textAlign = TextAlign.Center,
        )

        SDGText(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .alpha(alpha = contentAlpha)
                .clickable(
                    hasRipple = false,
                    enabled = isEnabled,
                    onClick = { onClick(false) },
                )
                .wrapContentHeight(align = Alignment.CenterVertically),
            text = if (isEndPlaceholderVisible) placeholder else endTime.orEmpty(),
            textColor = textColor,
            typography = SDGTypography.Body1R,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGTimeSelectInput(
    @PreviewParameter(SDGTimeSelectInputPreviewParameterProvider::class)
    params: SDGTimeSelectInputPreviewParams,
) {
    SDGTimeSelectInput(
        startTime = params.startTime,
        endTime = params.endTime,
        state = params.state,
        placeholder = params.placeholder,
        inputField = params.inputField,
        marginValues = PaddingValues(SDGSpacing.Spacing20),
        onClick = {},
    )
}
