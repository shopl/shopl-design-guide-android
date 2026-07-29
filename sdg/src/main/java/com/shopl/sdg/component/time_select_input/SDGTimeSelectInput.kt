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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
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
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing10
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing12
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing20
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

private const val SDGTimeSelectInputPeriodDivider = "~"
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
    marginValues: PaddingValues = PaddingValues(),
    onClick: (target: SDGTimeSelectInputTarget) -> Unit,
) {
    val backgroundColor = state.backgroundColor(inputField)

    Row(
        modifier = Modifier
            .padding(marginValues)
            .fillMaxWidth()
            .height(SDGTimeSelectInputHeight)
            .clip(shape = SDGCornerRadius.BoxRadius.Radius12)
            .background(color = backgroundColor)
            .padding(vertical = Spacing10, horizontal = Spacing12),
        horizontalArrangement = Arrangement.spacedBy(Spacing8),
    ) {
        SDGText(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .alpha(alpha = state.contentAlpha)
                .clickable(
                    enabled = state.isEnabled,
                    onClick = { onClick(SDGTimeSelectInputTarget.StartTime) },
                )
                .wrapContentHeight(align = Alignment.CenterVertically),
            text = if (state.isPlaceholderVisible(startTime)) placeholder else startTime.orEmpty(),
            textColor = state.textColor,
            typography = SDGTypography.Body1R,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        SDGText(
            modifier = Modifier
                .width(SDGTimeSelectInputDividerWidth)
                .fillMaxHeight()
                .alpha(alpha = state.contentAlpha)
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
                .alpha(alpha = state.contentAlpha)
                .clickable(
                    hasRipple = false,
                    enabled = state.isEnabled,
                    onClick = { onClick(SDGTimeSelectInputTarget.EndTime) },
                )
                .wrapContentHeight(align = Alignment.CenterVertically),
            text = if (state.isPlaceholderVisible(endTime)) placeholder else endTime.orEmpty(),
            textColor = state.textColor,
            typography = SDGTypography.Body1R,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

/**
 * 신규 Time Select Input API와의 하위 호환성을 위한 레거시 API입니다.
 */
@Deprecated(
    message = "SDGTimeSelectInput의 placeholder와 inputField 기반 API를 사용하세요.",
)
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
    val timeSelectInputState = state.resolveForTime(
        startTime = startTime,
        endTime = endTime,
    )
    val placeholder = if (!startTime.isNullOrEmpty() && endTime.isNullOrEmpty()) {
        endTimePlaceholder
    } else {
        startTimePlaceholder
    }

    SDGTimeSelectInput(
        startTime = startTime,
        endTime = endTime,
        state = timeSelectInputState,
        placeholder = placeholder,
        inputField = SDGTimeSelectInputField.fromLegacyBackgroundColor(
            backgroundColor = backgroundColor,
        ),
        marginValues = marginValues,
        onClick = { target ->
            onClick(target == SDGTimeSelectInputTarget.StartTime)
        },
    )
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
        marginValues = PaddingValues(Spacing20),
        onClick = {},
    )
}
