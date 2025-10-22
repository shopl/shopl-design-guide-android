package com.shopl.sdg.template.navigation.search

import androidx.compose.ui.graphics.Color


sealed interface SDGSearchNaviType {
    data class Full(
        val onClickClose: () -> Unit,
        val iconColor: Color
    ) : SDGSearchNaviType

    data class Back(
        val onClickBack: () -> Unit,
        val iconColor: Color
    ) : SDGSearchNaviType
}