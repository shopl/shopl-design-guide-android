package com.shopl.sdg.template.popup.center

/**
 *
 */
sealed class SDGCenterPopupType {
    abstract fun onClickConfirm()
    abstract val confirmLabel: String
    abstract val confirmLabelColor: String
    abstract val confirmEnabled: Boolean

    data class Custom() : SDGCenterPopupType()
    data class Info() : SDGCenterPopupType()
    data class Confirm() : SDGCenterPopupType()
    data class Delete() : SDGCenterPopupType()
    data class Input() : SDGCenterPopupType()
}