package com.shopl.sdg.template.navigation.top

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.navigation.text.SDGTextNaviBackIconSize
import com.shopl.sdg.component.navigation.text.SDGTextNaviLabel
import com.shopl.sdg.component.navigation.text.SDGTextNaviLayout
import com.shopl.sdg.component.navigation.text.SDGTextNaviSubIconSize
import com.shopl.sdg.template.navigation.SDGTopNaviMenu
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing6
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

private const val BACK_CLICK_DEBOUNCE_MILLIS = 1_000L
private const val MENU_MAX_LINES = 1

/**
 * SDG - Top Navigation - Top Text Navi
 *
 * 타이틀과 특정한 동작 또는 정보를 전달하는 텍스트 조합
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=6900-15491&t=dgvDUBUeWoMF9Oo1-4">Figma</a>
 * @see com.shopl.sdg.component.navigation.text.SDGTextNavi
 */
@Deprecated(message = "제거 예정. SDGTextNavi를 사용하세요.")
@Composable
fun SDGTopTextNavi(
    title: String = "",
    backgroundColor: Color = SDGColor.Neutral100,
    menu: SDGTopNaviMenu.TextMenu? = null,
    backBtn: SDGTopNaviMenu.CommonMenu? = null,
) {
    SDGTextNaviLayout(
        label = SDGTextNaviLabel.True(text = title),
        backgroundColor = backgroundColor,
        marginValues = PaddingValues(),
        backButton = if (backBtn != null) {
            {
                SDGImage(
                    resId = backBtn.iconResId,
                    color = backBtn.iconTint,
                    modifier = Modifier
                        .size(SDGTextNaviBackIconSize)
                        .clickable(
                            role = Role.Button,
                            debounceTimeMills = BACK_CLICK_DEBOUNCE_MILLIS,
                            onClick = backBtn.onMenuClick,
                        ),
                )
            }
        } else {
            null
        },
        rightItem = if (menu != null) {
            {
                Row(
                    modifier = Modifier
                        .clickable(role = Role.Button, onClick = menu.onMenuClick)
                        .padding(end = Spacing6),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (menu.iconResId != null) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(SDGTextNaviSubIconSize),
                        ) {
                            SDGImage(
                                resId = menu.iconResId,
                                color = menu.iconTint,
                                modifier = Modifier.size(menu.iconSize ?: SDGTextNaviSubIconSize),
                            )
                        }
                    }

                    SDGText(
                        text = menu.textMenuTitle,
                        textColor = SDGColor.Neutral700,
                        typography = SDGTypography.Body1R,
                        maxLines = MENU_MAX_LINES,
                        softWrap = false,
                    )
                }
            }
        } else {
            null
        },
    )
}

@Preview
@Composable
fun PreviewSDGTopTextNavi() {
    SDGTopTextNavi(
        title = "타이틀",
        menu = SDGTopNaviMenu.TextMenu(
            textMenuTitle = "메뉴",
            onMenuClick = {}
        ),
        backBtn = SDGTopNaviMenu.CommonMenu(
            iconResId = R.drawable.ic_navi_back_android,
            onMenuClick = {}
        )
    )
}

