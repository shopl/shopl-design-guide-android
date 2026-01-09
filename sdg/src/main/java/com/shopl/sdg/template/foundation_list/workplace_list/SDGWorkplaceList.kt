package com.shopl.sdg.template.foundation_list.workplace_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.template.popup.center.SDGCenterPopupButtonOption
import com.shopl.sdg.template.util.popup.SDGCenterPopup
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

/**
 * SDG - Template - Workplace List
 *
 * 근무지 전체 목록을 확인하는 템플릿
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19897-35940&t=I4V4gQVx0QoV39aO-4">Figma</a>
 */
@Composable
fun SDGWorkplaceList(
    popupTitle: String,
    buttonLabel: String,
    workplaces: PersistentList<SDGWorkplaceUiModel>,
    onClickButton: () -> Unit,
    onClickWorkplace: ((workplaceId: String) -> Unit)? = null,
) {
    SDGCenterPopup(
        buttonOption = SDGCenterPopupButtonOption.OneOption(
            label = buttonLabel,
            labelColor = SDGColor.Neutral700,
            onClick = onClickButton,
        ),
        title = popupTitle,
        itemSpacing = SDGSpacing.Spacing12
    ) {
        items(items = workplaces) { model ->
            SDGText(
                text = "${model.workplaceName}${
                    model.workplaceCode.takeIf { it.isNotEmpty() }?.let { "($it)" } ?: ""
                }",
                typography = SDGTypography.Body1R,
                textColor = SDGColor.Neutral700,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClickWorkplace?.invoke(model.workplaceId) }
            )

            Spacer(modifier = Modifier.height(height = SDGSpacing.Spacing4))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGWorkplaceList() {
    Box(modifier = Modifier.fillMaxSize()) {
        SDGWorkplaceList(
            popupTitle = "Workplace",
            buttonLabel = "Label",
            workplaces = List(size = 10) {
                SDGWorkplaceUiModel(
                    workplaceId = "$it",
                    workplaceName = "Workplace",
                    workplaceCode = "$it"
                )
            }.toPersistentList(),
            onClickButton = {},
        )
    }
}
