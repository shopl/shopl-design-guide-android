package com.shopl.sdg.template.common_select_view

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.shopl.sdg_resource.R

sealed interface SDGCommonSelectViewState {

    data object Default : SDGCommonSelectViewState
    data class NoResult(
        @param:DrawableRes
        val iconResId: Int = R.drawable.ic_empty_data,
        val description: String = "",
        @param:StringRes
        val descriptionResId: Int? = null,
    ) : SDGCommonSelectViewState

}