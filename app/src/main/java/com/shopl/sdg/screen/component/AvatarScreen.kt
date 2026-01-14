package com.shopl.sdg.screen.component

import androidx.compose.runtime.Composable
import com.shopl.sdg.component.navigation.basic.SDGBasicNavi
import com.shopl.sdg.component.navigation.basic.SDGBasicNaviIconItem
import com.shopl.sdg.ui.SDGScaffold
import com.shopl.sdg_common.foundation.SDGColor

@Composable
internal fun AvatarScreen() {
    SDGScaffold(
        backgroundColor = SDGColor.Neutral900,
        topBar = {
            SDGBasicNavi(
                title = null,
                backgroundColor = SDGColor.Neutral900,
                leftIcon = SDGBasicNaviIconItem(
                    resId = com.shopl.sdg.R.drawable.ic_navi_drawer,
                    onClick = {}
                ),
                rightIcons = null
            )
        },
    ) {

    }
}