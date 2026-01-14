package com.shopl.sdg.ui.screen.component.button

import androidx.compose.runtime.Composable
import com.shopl.sdg.R
import com.shopl.sdg.component.navigation.basic.SDGBasicNavi
import com.shopl.sdg.component.navigation.basic.SDGBasicNaviIconItem
import com.shopl.sdg.ui.SDGScaffold
import com.shopl.sdg_common.foundation.SDGColor

@Composable
internal fun BoxButtonScreen() {
    SDGScaffold(
        backgroundColor = SDGColor.Neutral900,
        topBar = {
            SDGBasicNavi(
                title = null,
                backgroundColor = SDGColor.Neutral900,
                leftIcon = SDGBasicNaviIconItem(
                    resId = R.drawable.ic_navi_drawer,
                    onClick = {}
                ),
                rightIcons = null
            )
        },
    ) {

    }
}