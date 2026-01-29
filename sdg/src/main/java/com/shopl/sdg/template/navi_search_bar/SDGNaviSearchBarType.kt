package com.shopl.sdg.template.navi_search_bar

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.shopl.sdg_resource.R

/**
 * 각 타입별로 앞/뒤 아이콘이 필요한지 여부와 클릭 액션을 담는다.
 */
@Stable
sealed interface SDGNaviSearchBarType {
    val leftIcon: SDGSearchNaviIcon?
    val rightIcon: SDGSearchNaviIcon?

    data class Full(
        private val onClickBack: () -> Unit,
        private val onClickClose: () -> Unit,
        private val leftIconColor: Color,
        private val rightIconColor: Color,
    ) : SDGNaviSearchBarType {
        override val leftIcon: SDGSearchNaviIcon = SDGSearchNaviIcon(
            iconResId = R.drawable.ic_navi_back_android,
            iconColor = leftIconColor,
            onClick = onClickBack
        )

        override val rightIcon: SDGSearchNaviIcon = SDGSearchNaviIcon(
            iconResId = R.drawable.ic_navi_close,
            iconColor = rightIconColor,
            onClick = onClickClose
        )
    }

    data class Back(
        private val onClickBack: () -> Unit,
        private val rightIconColor: Color,
    ) : SDGNaviSearchBarType {
        override val leftIcon: SDGSearchNaviIcon = SDGSearchNaviIcon(
            iconResId = R.drawable.ic_navi_back_android,
            iconColor = rightIconColor,
            onClick = onClickBack
        )

        override val rightIcon: SDGSearchNaviIcon? = null
    }

    data class Close(
        private val onClickClose: () -> Unit,
        private val iconColor: Color
    ) : SDGNaviSearchBarType {
        override val leftIcon: SDGSearchNaviIcon? = null

        override val rightIcon: SDGSearchNaviIcon = SDGSearchNaviIcon(
            iconResId = R.drawable.ic_navi_close,
            iconColor = iconColor,
            onClick = onClickClose
        )
    }
}

data class SDGSearchNaviIcon(
    val iconResId: Int,
    val iconColor: Color,
    val onClick: () -> Unit
)
