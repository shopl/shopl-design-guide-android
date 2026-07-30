package com.shopl.sdg.ui.screen.template

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.enums.SDGSampleStatus
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.scene.TemplateScene
import com.shopl.sdg.template.checkbox_label.SDGCheckboxLabel
import com.shopl.sdg.template.checkbox_label.SDGCheckboxLabelSelectType
import com.shopl.sdg.template.checkbox_label.SDGCheckboxLabelState
import com.shopl.sdg.ui.base.SDGSampleBaseComponentScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG Sample App - Template - Checkbox Label
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=27349-43543&m=dev">Figma</a>
 */
@Composable
internal fun CheckboxLabelScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    val states = persistentListOf(
        SDGSampleBaseTabItem(
            title = SDGCheckboxLabelState.Default.toString(),
            item = SDGCheckboxLabelState.Default,
        ),
        SDGSampleBaseTabItem(
            title = SDGCheckboxLabelState.Selected.toString(),
            item = SDGCheckboxLabelState.Selected,
        ),
    )
    val selectTypes = persistentListOf(
        SDGSampleBaseTabItem(
            title = SDGCheckboxLabelSelectType.Normal.toString(),
            item = SDGCheckboxLabelSelectType.Normal,
        ),
        SDGSampleBaseTabItem(
            title = SDGCheckboxLabelSelectType.Color.toString(),
            item = SDGCheckboxLabelSelectType.Color,
        ),
        SDGSampleBaseTabItem(
            title = SDGCheckboxLabelSelectType.Neutral.toString(),
            item = SDGCheckboxLabelSelectType.Neutral,
        ),
    )

    SDGSampleBaseComponentScaffold(
        componentName = TemplateScene.CheckboxLabel.displayLabel,
        componentDescription = "여러 옵션 중 하나 이상을 자유롭게 복수 선택할 수 있도록 체크박스 버튼과 데이터 텍스트(Label)를 결합한 템플릿",
        types = states,
        specs = selectTypes,
        guideLineDescriptions = persistentListOf("아이콘과 Label은 수평으로 상단 정렬합니다."),
        componentContent = { state, selectType, status ->
            if (state != null && selectType != null) {
                CheckboxLabelContent(
                    state = if (status == SDGSampleStatus.DISABLED) {
                        SDGCheckboxLabelState.Disabled
                    } else {
                        state
                    },
                    selectType = selectType,
                )
            }
        },
        onClickBack = onClickBack,
        onClickMenu = onClickMenu,
    )
}

@Composable
private fun CheckboxLabelContent(
    state: SDGCheckboxLabelState,
    selectType: SDGCheckboxLabelSelectType,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = SDGSpacing.Spacing16,
                vertical = SDGSpacing.Spacing40,
            ),
        contentAlignment = Alignment.Center,
    ) {
        SDGCheckboxLabel(
            label = "옵션명이 길어지면 아이콘과 상단 정렬되고 사용 가능한 영역에서 줄바꿈으로 전체 내용을 노출합니다.",
            state = state,
            selectType = selectType,
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun PreviewCheckboxLabelScreen() {
    ShoplDesignGuideTheme {
        CheckboxLabelScreen(
            onClickBack = {},
            onClickMenu = {},
        )
    }
}
