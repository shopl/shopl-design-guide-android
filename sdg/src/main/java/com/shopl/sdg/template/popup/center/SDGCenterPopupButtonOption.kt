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
        val cancelLabelColor: Color,
        val confirmLabelColor: Color,
        val confirmEnabled: Boolean = true,
        val onDismiss: (() -> Unit)? = null
    ) : SDGCenterPopupButtonOption

    data class DeleteOption(
        val deleteLabel: String,
        val cancelLabel: String,
        val onClickCancel: () -> Unit,
        val onClickDelete: () -> Unit,
        val cancelLabelColor: Color,
        val deleteLabelColor: Color,
        val deleteEnabled: Boolean = true,
        val onDismiss: (() -> Unit)? = null
    ) : SDGCenterPopupButtonOption
}