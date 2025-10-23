package com.shopl.sdg.template.navigation.search

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_resource.R

/**
 * 각 타입별로 앞/뒤 아이콘이 필요한지 여부와 클릭 액션을 담는다.
 */
sealed interface SDGSearchNaviType {
    val icon: SDGSearchNaviIcon

    data class Full(
        private val onClickClose: () -> Unit,
        private val iconColor: Color
    ) : SDGSearchNaviType {
        override val icon: SDGSearchNaviIcon = SDGSearchNaviIcon(
            iconResId = R.drawable.ic_navi_close,
            iconColor = iconColor,
            onClick = onClickClose
        )
    }

    data class Back(
        private val onClickBack: () -> Unit,
        private val iconColor: Color
    ) : SDGSearchNaviType {
        override val icon: SDGSearchNaviIcon = SDGSearchNaviIcon(
            iconResId = R.drawable.ic_navi_back_android,
            iconColor = iconColor,
            onClick = onClickBack
        )
    }
}

data class SDGSearchNaviIcon(
    val iconResId: Int,
    val iconColor: Color,
    val onClick: () -> Unit
)
