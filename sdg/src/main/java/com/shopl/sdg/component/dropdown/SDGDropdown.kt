package com.shopl.sdg.component.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.dropdown.preview.SDGDropdownPreviewParameterProvider
import com.shopl.sdg.component.dropdown.preview.SDGDropdownPreviewParams
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

private val SDGDropdownHeight = 40.dp
private val SDGDropdownIconSize = 20.dp

/**
 * SDG - Component - Dropdown
 *
 * 여러 개의 리스트 옵션 중 하나의 옵션을 선택하기 위한 컴포넌트
 *
 * @version 2.3.40
 *
 * @param text 선택된 옵션 텍스트
 * @param placeholder [SDGDropdownState.Default] 상태에서 표시할 안내 문구
 * @param state 드롭다운 상태
 * @param inputField 인풋 필드 배경 유형
 * @param onClick 드롭다운 클릭 콜백
 * @param marginValues 컴포넌트 외부 여백
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=27309-517&m=dev">Figma</a>
 */
@Composable
fun SDGDropdown(
    text: String,
    placeholder: String,
    state: SDGDropdownState,
    inputField: SDGDropdownInputField,
    onClick: () -> Unit,
    marginValues: PaddingValues = PaddingValues(),
) {
    val backgroundColor = state.backgroundColor(inputField)
    val showPlaceholder = state.isPlaceholderVisible(text)

    Row(
        modifier = Modifier
            .padding(marginValues)
            .fillMaxWidth()
            .height(SDGDropdownHeight)
            .clip(shape = SDGCornerRadius.BoxRadius.Radius12)
            .background(color = backgroundColor)
            .then(
                if (state.isEnabled) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                }
            )
            .padding(
                horizontal = SDGSpacing.Spacing12,
                vertical = SDGSpacing.Spacing10,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing10),
    ) {
        SDGText(
            modifier = Modifier.weight(1f),
            text = if (showPlaceholder) placeholder else text,
            textColor = state.textColor,
            typography = SDGTypography.Body1R,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
        SDGImage(
            modifier = Modifier.size(SDGDropdownIconSize),
            resId = R.drawable.ic_common_dropdown,
            color = state.iconColor,
        )
    }
}

/**
 * 신규 Dropdown API와의 하위 호환성을 위한 레거시 API입니다.
 */
@Deprecated(
    message = "SDGDropdown의 state와 inputField 기반 API를 사용하세요.",
)
@Composable
fun SDGDropdown(
    text: String? = null,
    placeholder: String = stringResource(id = R.string.select),
    state: SDGDropdownState = SDGDropdownState.Default,
    backgroundColor: Color = SDGColor.Neutral0,
    width: Dp? = null,
    marginValues: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
) {
    val dropdownText = text.orEmpty()
    val dropdownState = state.resolveForText(text = dropdownText)

    SDGDropdown(
        text = dropdownText,
        state = dropdownState,
        placeholder = placeholder,
        inputField = SDGDropdownInputField.fromLegacyBackgroundColor(backgroundColor),
        marginValues = marginValues,
        onClick = { onClick?.invoke() },
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGDropdown(
    @PreviewParameter(SDGDropdownPreviewParameterProvider::class)
    params: SDGDropdownPreviewParams,
) {
    SDGDropdown(
        text = params.text,
        state = params.state,
        placeholder = params.placeholder,
        inputField = params.inputField,
        marginValues = PaddingValues(SDGSpacing.Spacing20),
        onClick = {},
    )
}
