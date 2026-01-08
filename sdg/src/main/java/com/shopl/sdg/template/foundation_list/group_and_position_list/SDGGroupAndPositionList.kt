package com.shopl.sdg.template.foundation_list.group_and_position_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.shopl.sdg.template.foundation_list.group_and_position_list.preview.SDGGroupAndPositionPreviewParameterProvider
import com.shopl.sdg.template.foundation_list.group_and_position_list.preview.SDGGroupAndPositionPreviewParams
import com.shopl.sdg.template.popup.center.SDGCenterPopupButtonOption
import com.shopl.sdg.template.util.popup.SDGCenterPopup
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.PersistentList

/**
 * SDG - Template - Group & Position List
 *
 * 그룹 및 직무/직급 전체 목록을 확인하는 템플릿
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19897-36301&m=dev">Figma</a>
 */
@Composable
fun SDGGroupAndPositionList(
    popupTitle: String,
    buttonLabel: String,
    groups: PersistentList<SDGGroupUiModel>,
    positions: PersistentList<SDGPositionUiModel>,
    onClickButton: () -> Unit,
    onClickGroup: ((groupId: String) -> Unit)? = null,
    onClickPosition: ((positionId: String) -> Unit)? = null,
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
        if (groups.isNotEmpty()) {
            item {
                // TODO: stringResource
                SDGText(
                    text = "그룹",
                    typography = SDGTypography.Body2R,
                    textColor = SDGColor.Neutral400
                )
            }

            items(items = groups) { group ->
                SDGText(
                    text = group.groupName,
                    typography = SDGTypography.Body1R,
                    textColor = SDGColor.Neutral700,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClickGroup?.invoke(group.groupId) }
                )

                Spacer(modifier = Modifier.height(height = SDGSpacing.Spacing4))
            }
        }

        if (groups.isNotEmpty() && positions.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(height = SDGSpacing.Spacing4))
                HorizontalDivider(color = SDGColor.Neutral150)
                Spacer(modifier = Modifier.height(height = SDGSpacing.Spacing4))
            }
        }

        if (positions.isNotEmpty()) {
            item {
                SDGText(
                    text = "직무/직급",
                    typography = SDGTypography.Body2R,
                    textColor = SDGColor.Neutral400
                )
            }

            items(items = positions) { position ->
                SDGText(
                    text = position.positionName,
                    typography = SDGTypography.Body1R,
                    textColor = SDGColor.Neutral700,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClickPosition?.invoke(position.positionId) }
                )

                Spacer(modifier = Modifier.height(height = SDGSpacing.Spacing4))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGGroupAndPositionList(
    @PreviewParameter(SDGGroupAndPositionPreviewParameterProvider::class)
    params: SDGGroupAndPositionPreviewParams
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SDGGroupAndPositionList(
            popupTitle = "Group and Position",
            buttonLabel = "Label",
            groups = params.groups,
            positions = params.positions,
            onClickButton = {},
        )
    }
}
