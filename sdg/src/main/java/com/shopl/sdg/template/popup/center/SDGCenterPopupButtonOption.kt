package com.shopl.sdg.template.popup.center

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

sealed interface SDGCenterPopupButtonOption {
    data class OneOption(
        val label: String,
        val onClick: () -> Unit,
        val labelColor: Color = SDGColor.Neutral700,
        val enabled: Boolean = true,
    ) : SDGCenterPopupButtonOption

    data class TwoOption(
        val cancelLabel: String,
        val confirmLabel: String,
        val onClickCancel: () -> Unit,
        val onClickConfirm: () -> Unit,
        val confirmEnabled: Boolean = true,
        val cancelLabelColor: Color = SDGColor.Neutral700,
        val confirmLabelColor: Color = SDGColor.Neutral700,
    ) : SDGCenterPopupButtonOption

    data class DeleteOption(
        val deleteLabel: String,
        val cancelLabel: String,
        val onClickCancel: () -> Unit,
        val onClickDelete: () -> Unit,
        val deleteEnabled: Boolean = true,
        val cancelLabelColor: Color = SDGColor.Neutral700,
        val deleteLabelColor: Color = SDGColor.Neutral700,
    ) : SDGCenterPopupButtonOption
}