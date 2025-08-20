package com.shopl.sdg.template.popup.center

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.button.SDGButtonFontWeight
import com.shopl.sdg.component.button.ghost.SDGGhostButton
import com.shopl.sdg.component.button.ghost.SDGGhostButtonSize
import com.shopl.sdg.component.util.button.ghost.SDGGhostButton
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4

private const val SDGCenterPopupButtonHeight = 51

/**
 * SDG Popup에서 사용되는 Button
 */
@Composable
fun SDGCenterPopupButton(
    option: SDGCenterPopupButtonOption
) {
    when (option) {
        is SDGCenterPopupButtonOption.OneOption -> {
            SDGOneOptionCenterPopupButton(
                label = option.label,
                labelColor = option.labelColor,
                enabled = option.enabled,
                onClick = option.onClick
            )
        }

        is SDGCenterPopupButtonOption.TwoOption -> {
            SDGTwoOptionCenterPopupButton(
                cancelLabel = option.cancelLabel,
                confirmLabel = option.confirmLabel,
                isConfirmEnable = option.confirmEnabled,
                confirmLabelColor = option.confirmLabelColor,
                onClickCancel = option.onClickCancel,
                onClickConfirm = option.onClickConfirm,
            )
        }

        is SDGCenterPopupButtonOption.DeleteOption -> {
            SDGDeleteOptionCenterPopupButton(
                cancelLabel = option.cancelLabel,
                deleteLabel = option.deleteLabel,
                onClickCancel = option.onClickCancel,
                onClickDelete = option.onClickDelete,
                isDeleteEnable = option.deleteEnabled,
                deleteLabelColor = option.deleteLabelColor
            )
        }
    }
}

@Composable
private fun SDGOneOptionCenterPopupButton(
    label: String,
    onClick: () -> Unit,
    labelColor: Color = SDGColor.Neutral700,
    enabled: Boolean = true,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(SDGCenterPopupButtonHeight.dp)
            .clip(RoundedCornerShape(bottomStart = SDGCornerRadius.Radius20, bottomEnd = SDGCornerRadius.Radius20))
            .background(SDGColor.Neutral0)
    ) {
        HorizontalDivider(color = SDGColor.Neutral200)

        SDGGhostButton(
            size = SDGGhostButtonSize.Large,
            label = label,
            labelColor = labelColor,
            onClick = onClick,
            labelWeight = SDGButtonFontWeight.SB,
            enable = enabled,
            isFillMaxWidth = true,
            marginValues = PaddingValues(vertical = Spacing4)
        )
    }
}

@Composable
private fun SDGTwoOptionCenterPopupButton(
    cancelLabel: String,
    confirmLabel: String,
    onClickCancel: () -> Unit,
    onClickConfirm: () -> Unit,
    isConfirmEnable: Boolean,
    confirmLabelColor: Color = SDGColor.Neutral700,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(SDGCenterPopupButtonHeight.dp)
            .clip(RoundedCornerShape(bottomStart = SDGCornerRadius.Radius20, bottomEnd = SDGCornerRadius.Radius20))
            .background(SDGColor.Neutral0)
    ) {
        HorizontalDivider(color = SDGColor.Neutral200)

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            SDGGhostButton(
                weight = 1f,
                size = SDGGhostButtonSize.Large,
                label = cancelLabel,
                labelColor = SDGColor.Neutral700,
                onClick = onClickCancel,
                enable = true,
                marginValues = PaddingValues(vertical = Spacing4)
            )

            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                color = SDGColor.Neutral200
            )

            SDGGhostButton(
                weight = 1f,
                size = SDGGhostButtonSize.Large,
                label = confirmLabel,
                labelColor = confirmLabelColor,
                onClick = onClickConfirm,
                labelWeight = SDGButtonFontWeight.SB,
                enable = isConfirmEnable,
                marginValues = PaddingValues(vertical = Spacing4)
            )
        }
    }
}


@Composable
private fun SDGDeleteOptionCenterPopupButton(
    cancelLabel: String,
    deleteLabel: String,
    onClickCancel: () -> Unit,
    onClickDelete: () -> Unit,
    isDeleteEnable: Boolean,
    deleteLabelColor: Color = SDGColor.Red300,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(SDGCenterPopupButtonHeight.dp)
            .clip(RoundedCornerShape(bottomStart = SDGCornerRadius.Radius20, bottomEnd = SDGCornerRadius.Radius20))
            .background(SDGColor.Neutral0)
    ) {
        HorizontalDivider(color = SDGColor.Neutral200)
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            SDGGhostButton(
                weight = 1f,
                size = SDGGhostButtonSize.Large,
                label = cancelLabel,
                labelColor = SDGColor.Neutral700,
                onClick = onClickCancel,
                enable = true,
                marginValues = PaddingValues(vertical = Spacing4)
            )

            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                color = SDGColor.Neutral200
            )

            SDGGhostButton(
                weight = 1f,
                size = SDGGhostButtonSize.Large,
                label = deleteLabel,
                labelWeight = SDGButtonFontWeight.SB,
                labelColor = deleteLabelColor,
                onClick = onClickDelete,
                enable = isDeleteEnable,
                marginValues = PaddingValues(vertical = Spacing4)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSDGOneOptionCenterPopupButton() {
    SDGOneOptionCenterPopupButton(
        label = "확인",
        onClick = {},
        enabled = true
    )
}

@Preview
@Composable
private fun PreviewSDGTwoOptionCenterPopupButton() {
    SDGTwoOptionCenterPopupButton(
        cancelLabel = "취소",
        confirmLabel = "확인",
        onClickCancel = {},
        onClickConfirm = {},
        isConfirmEnable = true,
    )
}

@Preview
@Composable
private fun PreviewSDGDeleteOptionCenterPopupButton() {
    SDGDeleteOptionCenterPopupButton(
        cancelLabel = "취소",
        deleteLabel = "삭제",
        onClickCancel = {},
        onClickDelete = {},
        isDeleteEnable = true,
    )
}

