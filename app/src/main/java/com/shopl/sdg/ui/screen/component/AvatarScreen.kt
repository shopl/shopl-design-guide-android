package com.shopl.sdg.ui.screen.component

import androidx.compose.runtime.Composable
import com.shopl.sdg.R
import com.shopl.sdg.component.navigation.basic.SDGBasicNavi
import com.shopl.sdg.component.navigation.basic.SDGBasicNaviIconItem
import com.shopl.sdg.ui.SDGScaffold
import com.shopl.sdg_common.foundation.SDGColor

@Composable
internal fun AvatarScreen() {
    SDGScaffold(
        backgroundColor = SDGColor.Neutral0,
        topBar = {
            SDGBasicNavi(
                title = null,
                backgroundColor = SDGColor.Neutral0,
                leftIcon = SDGBasicNaviIconItem(
                    resId = R.drawable.ic_navi_drawer,
                    color = SDGColor.Neutral700,
                    onClick = {}
                ),
                rightIcons = null
            )
        },
    ) {

    }
}