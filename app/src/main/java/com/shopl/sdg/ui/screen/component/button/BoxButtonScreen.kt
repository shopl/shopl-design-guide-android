package com.shopl.sdg.ui.screen.component.button

import androidx.compose.runtime.Composable
import com.shopl.sdg.R
import com.shopl.sdg.component.navigation.basic.SDGBasicNavi
import com.shopl.sdg.component.navigation.basic.SDGBasicNaviIconItem
import com.shopl.sdg.ui.SDGScaffold
import com.shopl.sdg_common.foundation.SDGColor

/**
 * SDG Sample App - Component - Button - BoxButton
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=22804-3346&m=dev">Figma</a>
 */
@Composable
internal fun BoxButtonScreen() {
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