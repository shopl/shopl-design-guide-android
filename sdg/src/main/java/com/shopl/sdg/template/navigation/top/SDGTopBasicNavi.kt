package com.shopl.sdg.template.navigation.top

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.template.navigation.SDGTopNaviMenu
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList


/**
 * SDG - Top Basic Navi
 *
 * 타이틀과 아이콘으로 조합
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?m=auto&node-id=6900-15481&t=zv5tCXZY9fUsqi9n-1">Figma</a>
 */

enum class SDGTopBasicType { Full, Back, Close }

@Composable
fun SDGTopBasicNavi(
    type: SDGTopBasicType,
    title: String,
    startMenus: PersistentList<SDGTopNaviMenu.CommonMenu> = persistentListOf(),
    endMenus: PersistentList<SDGTopNaviMenu.CommonMenu> = persistentListOf(),
    containerColor: Color = SDGColor.Neutral100,
    onBack: (() -> Unit)? = null,
    onClose: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            .background(containerColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = SDGSpacing.Spacing10),
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (type) {
                SDGTopBasicType.Full -> {
                    startMenus.forEachIndexed { idx, menu ->
                        SDGImage(
                            modifier = Modifier
                                .size(SDGSpacing.Spacing40)
                                .clickable(false) { menu.onMenuClick() },
                            resId = menu.iconResId,
                            color = menu.iconTint
                        )
                        if (idx != startMenus.lastIndex) Spacer(Modifier.width(2.dp))
                    }
                    if (startMenus.isNotEmpty()) Spacer(Modifier.width(2.dp))
                }

                SDGTopBasicType.Back -> {
                    SDGImage(
                        modifier = Modifier
                            .size(SDGSpacing.Spacing40)
                            .clickable(false) { onBack?.invoke() },
                        resId = R.drawable.ic_navi_back_android,
                        color = null
                    )
                    Spacer(Modifier.width(2.dp))
                }

                SDGTopBasicType.Close -> {
                    Spacer(Modifier.width(6.dp))
                }
            }

            Row(
                modifier = Modifier
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SDGText(
                    modifier = Modifier.weight(1f, false),
                    text = title,
                    textColor = SDGColor.Neutral900,
                    typography = SDGTypography.NaviTitle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Spacer(Modifier.width(if (endMenus.isNotEmpty()) 10.dp else 0.dp))
            endMenus.forEachIndexed { idx, menu ->
                SDGImage(
                    modifier = Modifier
                        .size(SDGSpacing.Spacing40)
                        .clickable(false) { menu.onMenuClick() },
                    resId = menu.iconResId,
                    color = menu.iconTint
                )
                if (idx != endMenus.lastIndex) Spacer(Modifier.width(2.dp))
            }

            when (type) {
                SDGTopBasicType.Close -> {
                    Spacer(Modifier.width(if (endMenus.isNotEmpty()) 2.dp else 0.dp))
                    SDGImage(
                        modifier = Modifier
                            .size(40.dp)
                            .clickable(false) { onClose?.invoke() },
                        resId = R.drawable.ic_navi_close,
                        color = null
                    )
                }

                else -> {}
            }
        }
    }
}

@Composable
fun SDGTopBasicNaviFull(
    title: String,
    startMenus: PersistentList<SDGTopNaviMenu.CommonMenu>,
    endMenus: PersistentList<SDGTopNaviMenu.CommonMenu> = persistentListOf(),
) = SDGTopBasicNavi(
    type = SDGTopBasicType.Full,
    title = title,
    startMenus = startMenus,
    endMenus = endMenus
)

@Composable
fun SDGTopBasicNaviBack(
    title: String,
    onBack: () -> Unit,
    endMenus: PersistentList<SDGTopNaviMenu.CommonMenu> = persistentListOf()
) = SDGTopBasicNavi(
    type = SDGTopBasicType.Back,
    title = title,
    onBack = onBack,
    endMenus = endMenus
)

@Composable
fun SDGTopBasicNaviClose(
    title: String,
    onClose: () -> Unit,
    endMenus: PersistentList<SDGTopNaviMenu.CommonMenu> = persistentListOf()
) = SDGTopBasicNavi(
    type = SDGTopBasicType.Close,
    title = title,
    onClose = onClose,
    endMenus = endMenus
)

@Preview(showBackground = true)
@Composable
private fun PreviewTopBasicAll() {
    Column {
        SDGTopBasicNaviFull(
            title = "Full",
            startMenus = listOf(
                SDGTopNaviMenu.CommonMenu(R.drawable.ic_navi_chat, onMenuClick = {}),
            ).toPersistentList(),
            endMenus = listOf(
                SDGTopNaviMenu.CommonMenu(R.drawable.ic_navi_chat, onMenuClick = {}),
                SDGTopNaviMenu.CommonMenu(R.drawable.ic_navi_chat, onMenuClick = {}),
                SDGTopNaviMenu.CommonMenu(R.drawable.ic_navi_chat, onMenuClick = {})
            ).toPersistentList(),
        )
        Spacer(Modifier.height(12.dp))
        SDGTopBasicNaviBack(
            title = "Back",
            onBack = {},
            endMenus = listOf(
                SDGTopNaviMenu.CommonMenu(R.drawable.ic_navi_chat, onMenuClick = {}),
                SDGTopNaviMenu.CommonMenu(R.drawable.ic_navi_chat, onMenuClick = {}),
                SDGTopNaviMenu.CommonMenu(R.drawable.ic_navi_chat, onMenuClick = {}),
            ).toPersistentList()
        )
        Spacer(Modifier.height(12.dp))
        SDGTopBasicNaviClose(
            title = "Close",
            onClose = {},
            endMenus = listOf(
                SDGTopNaviMenu.CommonMenu(R.drawable.ic_navi_chat, onMenuClick = {}),
                SDGTopNaviMenu.CommonMenu(R.drawable.ic_navi_chat, onMenuClick = {}),
            ).toPersistentList()
        )
    }
}
