package com.shopl.sdg.ui.screen.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.select_input.SDGSelectInput
import com.shopl.sdg.component.select_input.SDGSelectInputField
import com.shopl.sdg.component.select_input.SDGSelectInputImage
import com.shopl.sdg.component.select_input.SDGSelectInputState
import com.shopl.sdg.component.select_input.SDGSelectInputType
import com.shopl.sdg.enums.SDGSampleStatus
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.ui.base.SDGSampleBaseComponentScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_resource.R
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG Sample App - Component - Select Input
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=27047-2318&m=dev">Figma</a>
 */
@Composable
internal fun SelectInputScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    val textType = SDGSelectInputType.Text
    val avatarType = SDGSelectInputType.Avatar(
        selectedElementImage = SDGSelectInputImage.Resource(
            resId = R.drawable.profile_small,
        ),
    )
    val oneImageType = SDGSelectInputType.OneImage(
        selectedElementImage = SDGSelectInputImage.Resource(
            resId = R.drawable.ic_common_photo,
        ),
    )
    val twoImageType = SDGSelectInputType.TwoImage(
        selectedElementImage = SDGSelectInputImage.Resource(
            resId = R.drawable.ic_common_photo,
        ),
        secondSelectedElementText = "Second Selected Text",
        secondSelectedElementImage = SDGSelectInputImage.Resource(
            resId = R.drawable.ic_common_photo,
        ),
    )
    val types = persistentListOf<SDGSampleBaseTabItem<SDGSelectInputType>>(
        SDGSampleBaseTabItem(
            title = textType.typeName,
            item = textType,
        ),
        SDGSampleBaseTabItem(
            title = avatarType.typeName,
            item = avatarType,
        ),
        SDGSampleBaseTabItem(
            title = oneImageType.typeName,
            item = oneImageType,
        ),
        SDGSampleBaseTabItem(
            title = twoImageType.typeName,
            item = twoImageType,
        ),
    )
    val specs = persistentListOf<SDGSampleBaseTabItem<SDGSelectInputField>>(
        SDGSampleBaseTabItem(
            title = SDGSelectInputField.LightGray.fieldName,
            item = SDGSelectInputField.LightGray,
        ),
        SDGSampleBaseTabItem(
            title = SDGSelectInputField.White.fieldName,
            item = SDGSelectInputField.White,
        ),
    )

    SDGSampleBaseComponentScaffold(
        componentName = ComponentScene.SelectInput.displayLabel,
        componentDescription = "특정 타겟을 선택하는 인풋 컴포넌트",
        types = types,
        specs = specs,
        guideLineDescriptions = persistentListOf(
            "Selected Element는 Text / Avatar / 1 Image / 2 Image 4가지 유형으로 구성됩니다.",
            "1 Image / 2 Image의 이미지 영역은 Normal 1 / Normal 2 / Special 1 규격을 지원합니다.",
            "단일 선택은 끝 말줄임표, 다중 선택은 중간 말줄임표를 사용합니다.",
        ),
        componentContent = { currentType, currentSpec, currentStatus ->
            if (currentType != null && currentSpec != null) {
                SelectInputContent(
                    type = currentType,
                    inputField = currentSpec,
                    status = currentStatus,
                )
            }
        },
        onClickBack = onClickBack,
        onClickMenu = onClickMenu,
    )
}

@Composable
private fun SelectInputContent(
    type: SDGSelectInputType,
    inputField: SDGSelectInputField,
    status: SDGSampleStatus,
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
        SDGSelectInput(
            text = "Selected Text가 길어지면 마지막에 말줄임표가 표시됩니다.",
            state = if (status == SDGSampleStatus.DISABLED) {
                SDGSelectInputState.Disabled
            } else {
                SDGSelectInputState.Selected
            },
            inputField = inputField,
            type = type,
            onClick = {},
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
private fun PreviewSelectInputScreen() {
    ShoplDesignGuideTheme {
        SelectInputScreen(
            onClickBack = {},
            onClickMenu = {},
        )
    }
}
