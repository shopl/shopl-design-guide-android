package com.shopl.sdg.template.popup.center

import androidx.compose.ui.graphics.Color

sealed interface SDGCenterPopupButtonOption {
    data class OneOption(
        val label: String,
        val onClick: () -> Unit,
        val labelColor: Color,
        val enabled: Boolean = true,
    ) : SDGCenterPopupButtonOption

    data class TwoOption(
        val cancelLabel: String,
        val confirmLabel: String,
        val onClickCancel: () -> Unit,
        val onClickConfirm: () -> Unit,
        val confirmEnabled: Boolean = true,
        val cancelLabelColor: Color,
        val confirmLabelColor: Color,
    ) : SDGCenterPopupButtonOption

    data class DeleteOption(
        val deleteLabel: String,
        val cancelLabel: String,
        val onClickCancel: () -> Unit,
        val onClickDelete: () -> Unit,
        val deleteEnabled: Boolean = true,
        val cancelLabelColor: Color,
        val deleteLabelColor: Color,
    ) : SDGCenterPopupButtonOption
}