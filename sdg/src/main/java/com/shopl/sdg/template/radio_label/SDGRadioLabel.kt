package com.shopl.sdg.template.radio_label

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.shopl.sdg.component.radio.SDGRadio
import com.shopl.sdg.component.radio.SDGRadioColor
import com.shopl.sdg.component.radio.SDGRadioSize
import com.shopl.sdg.template.radio_label.preview.SDGRadioLabelPreviewParameterProvider
import com.shopl.sdg.template.radio_label.preview.SDGRadioLabelPreviewParams
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Template - Radio Label
 *
 * 여러 상호 배타적인 옵션 중 단 한 가지 항목만 명확하게 선택할 수 있도록 라디오 버튼과 데이터 텍스트(Label)를 결합한 템플릿
 *
 * @version 2.3.41
 *
 * @param label 라디오 옆에 표시되는 텍스트 라벨
 * @param state 라디오 라벨 상태
 * @param selectType 선택된 라디오와 라벨의 색상 타입
 * @param marginValues 컴포넌트 외부 여백
 * @param onClick 라디오 라벨 클릭 시 호출되는 콜백
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=27325-39477&m=dev">Figma</a>
 */
@Composable
fun SDGRadioLabel(
    label: String,
    state: SDGRadioLabelState,
    selectType: SDGRadioLabelSelectType,
    onClick: () -> Unit,
    marginValues: PaddingValues = PaddingValues(),
) {
    val typography = SDGTypography.Body1R
    val density = LocalDensity.current
    val lineHeightDp = with(receiver = density) { typography.style.lineHeight.toDp() }

    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Spacing8),
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .padding(marginValues)
            .then(
                if (state.isEnabled) {
                    Modifier.clickable(
                        role = Role.RadioButton,
                        onClick = onClick,
                    )
                } else {
                    Modifier
                }
            ),
    ) {
        Box(
            modifier = Modifier.height(height = lineHeightDp),
            contentAlignment = Alignment.Center
        ) {
            SDGRadio(
                status = state.radioStatus,
                selectedColor = selectType.radioColor,
                size = SDGRadioSize.MEDIUM,
            )
        }

        SDGText(
            text = label,
            textColor = state.labelColor(selectType),
            typography = typography,
        )
    }
}

/**
 * 신규 Radio Label API와의 하위 호환성을 위한 레거시 API입니다.
 */
@Deprecated(
    message = "SDGRadioLabel의 state와 selectType 기반 API를 사용하세요.",
)
@Composable
@Suppress("DEPRECATION")
fun SDGRadioLabel(
    label: String,
    status: SDGRadioLabelStatus,
    selectedLabelColor: SDGRadioLabelColor = SDGRadioLabelColor.BASIC,
    radioColor: SDGRadioColor = SDGRadioColor.BASIC,
    radioSize: SDGRadioSize = SDGRadioSize.MEDIUM,
    onClick: (() -> Unit)? = null,
) {
    SDGRadioLabel(
        label = label,
        state = status.toState(),
        selectType = SDGRadioLabelSelectType.fromLegacy(
            selectedLabelColor = selectedLabelColor,
            radioColor = radioColor,
        ),
        onClick = { onClick?.invoke() },
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGRadioLabel(
    @PreviewParameter(SDGRadioLabelPreviewParameterProvider::class)
    params: SDGRadioLabelPreviewParams
) {
    SDGRadioLabel(
        label = params.label,
        state = params.state,
        selectType = params.selectType,
        onClick = {},
    )
}
