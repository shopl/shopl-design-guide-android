package com.shopl.sdg.template.navigation

import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shopl.sdg.component.avatar.IOAvatar
import com.shopl.sdg.component.avatar.SDGAvatarSize
import com.shopl.sdg.component.search_bar.SDGBasicRoundSearch
import com.shopl.sdg_common.enums.OutlineType
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.ext.dpToSp
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.IOText
import com.shopl.sdg_common.ui.components.IOTypeface
import com.shopl.sdg_resource.R

@Stable
sealed class SDGTopNaviMenu(
    @DrawableRes
    open val iconResId: Int?,
    open val onMenuClick: () -> Unit,
    open val iconTint: Color? = null,
) {
    data class CommonMenu(
        override val iconResId: Int,
        override val onMenuClick: () -> Unit,
        override val iconTint: Color? = null,
    ) : SDGTopNaviMenu(iconResId, onMenuClick, iconTint)

    data class BulletMenu(
        override val iconResId: Int,
        override val onMenuClick: () -> Unit,
        override val iconTint: Color? = null,
        val isActive: Boolean = false
    ) : SDGTopNaviMenu(iconResId, onMenuClick, iconTint)

    data class NumberMenu(
        override val iconResId: Int,
        override val onMenuClick: () -> Unit,
        override val iconTint: Color? = null,
        private val count: Int
    ) : SDGTopNaviMenu(iconResId, onMenuClick, iconTint) {
        val displayCount: String
            get() {
                return if (count < 100) {
                    "$count"
                } else {
                    "99+"
                }
            }
        val isActive: Boolean
            get() = count > 0
    }

    data class SearchMenu(
        override val iconTint: Color? = null,
        val hintIconResId: Int? = null,
        val hint: String,
        val onInputChange: (String) -> Unit,
        val onDeleteClick: () -> Unit,
        val onHintIconClick: (() -> Unit)? = null,
        val onSearchClick: (() -> Unit)? = null,
    ) : SDGTopNaviMenu(R.drawable.ic_navi_search, {}, iconTint)

    data class SearchMenuV2(
        override val iconTint: Color? = null,
        val hintIconResId: Int? = null,
        val hint: String,
        val onInputChange: (TextFieldValue) -> Unit,
        val onDeleteClick: () -> Unit,
        val onHintIconClick: (() -> Unit)? = null,
        val onSearchClick: (() -> Unit)? = null,
    ) : SDGTopNaviMenu(R.drawable.ic_navi_search, {}, iconTint)

    data class TextMenu(
        override val iconResId: Int? = null,
        override val iconTint: Color? = null,
        val iconSize: Dp? = null,
        val textMenuTitle: String = "",
        override val onMenuClick: () -> Unit,
    ) : SDGTopNaviMenu(iconResId, onMenuClick, iconTint)
}

@Composable
fun SDGTopNaviBasic(
    title: String = "",
    titleColor: Color = SDGColor.Neutral900,
    backgroundColor: Color = SDGColor.Neutral100,
    menus: List<SDGTopNaviMenu>? = null,
    backBtn: SDGTopNaviMenu.CommonMenu? = null,
    searchInput: String? = null,
    onSearchClick: (() -> Unit)? = null,
    onSearchClickColse: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    if (menus != null) {
        require(menus.size < 4) {
            "The maximum size of the menu list must be 3."
        }
    }

    var isSearchMode by remember { mutableStateOf(false) }
    val searchItem = menus?.find { it is SDGTopNaviMenu.SearchMenu } as? SDGTopNaviMenu.SearchMenu

    Crossfade(targetState = isSearchMode, label = "topNaviMode") { isSearch ->
        when (isSearch) {
            true -> SearchLayer(
                backgroundColor = backgroundColor,
                input = searchInput ?: "",
                hintIconResId = searchItem?.hintIconResId,
                hint = searchItem?.hint ?: "",
                onInputChange = searchItem?.onInputChange ?: {},
                onDeleteClick = searchItem?.onDeleteClick ?: {},
                onCloseClick = {
                    onSearchClickColse?.invoke()
                    isSearchMode = false
                },
                onHintIconClick = searchItem?.onHintIconClick,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
            )

            false -> BasicLayer(
                onSearchClick = {
                    onSearchClick?.invoke()
                    isSearchMode = true
                },
                title = title,
                titleColor = titleColor,
                backgroundColor = backgroundColor,
                menus = menus,
                backBtn = backBtn,
            )
        }
    }
}

@Composable
private fun BasicLayer(
    title: String = "",
    titleColor: Color,
    backgroundColor: Color = SDGColor.Neutral100,
    menus: List<SDGTopNaviMenu>? = null,
    backBtn: SDGTopNaviMenu.CommonMenu? = null,
    onSearchClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .background(backgroundColor)
            .height(48.dp)
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (backBtn != null) {
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .clickable(false, debounceTimeMills = 1000) { backBtn.onMenuClick.invoke() },
                painter = painterResource(id = backBtn.iconResId),
                colorFilter = if (backBtn.iconTint == null) null else ColorFilter.tint(color = backBtn.iconTint),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(if (backBtn != null) 2.dp else 6.dp))
        IOText(
            modifier = Modifier
                .weight(1f),
            text = title,
            textColor = titleColor,
            fontSize = dpToSp(dp = 19.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.width(if (backBtn != null) 10.dp else 6.dp))
        menus?.forEachIndexed { index, menu ->
            Box(
                Modifier
                    .size(40.dp)
                    .clickable(false) {
                        if (menu is SDGTopNaviMenu.SearchMenu) {
                            onSearchClick()
                        } else {
                            menu.onMenuClick.invoke()
                        }
                    }
            ) {
                if (menu.iconResId != null) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = menu.iconResId!!),
                        colorFilter = if (menu.iconTint == null) null else ColorFilter.tint(color = menu.iconTint!!),
                        contentDescription = null
                    )
                    when (menu) {
                        is SDGTopNaviMenu.NumberMenu -> {
                            if (menu.isActive) {
                                IOText(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .sizeIn(minHeight = 14.dp, minWidth = 14.dp)
                                        .background(
                                            color = SDGColor.Red350,
                                            shape = RoundedCornerShape(7.dp)
                                        )
                                        .padding(1.dp)
                                        .align(Alignment.TopEnd),
                                    text = menu.displayCount,
                                    textColor = SDGColor.Neutral0,
                                    fontSize = dpToSp(dp = 8.dp),
                                    textAlign = TextAlign.Center,
                                    typeface = IOTypeface.SEMI_BOLD,
                                    lineHeight = 12.sp
                                )
                            }
                        }

                        is SDGTopNaviMenu.BulletMenu -> {
                            if (menu.isActive) {
                                Canvas(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .size(8.dp)
                                        .align(Alignment.TopEnd)
                                ) {
                                    drawCircle(
                                        color = SDGColor.Red350
                                    )
                                }
                            }
                        }

                        is SDGTopNaviMenu.CommonMenu -> {
                            // do nothing
                        }

                        is SDGTopNaviMenu.SearchMenu,
                        is SDGTopNaviMenu.SearchMenuV2 -> {
                            // do nothing
                        }

                        is SDGTopNaviMenu.TextMenu -> {
                            // do nothing
                        }
                    }
                }
            }
            if (menus.lastIndex != index) {
                Spacer(modifier = Modifier.width(2.dp))
            }
        }
    }
}

@Composable
private fun SearchLayer(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    input: String,
    hintIconResId: Int? = null,
    hint: String,
    onInputChange: (String) -> Unit,
    onDeleteClick: () -> Unit,
    onCloseClick: () -> Unit,
    onHintIconClick: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Row(
        modifier = modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SDGBasicRoundSearch(
            weight = 1f,
            isFillMaxWidth = false,
            outlineType = OutlineType.BASIC,
            input = input,
            hintIconResId = hintIconResId,
            hint = hint,
            enabled = true,
            onInputChange = onInputChange,
            onDeleteClick = onDeleteClick,
            onHintIconClick = onHintIconClick,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        )
        Image(
            modifier = Modifier
                .clickable(false) { onCloseClick() },
            painter = painterResource(id = R.drawable.ic_navi_close),
            contentDescription = null,
            colorFilter = ColorFilter.tint(SDGColor.Neutral700)
        )
    }
}

@Composable
fun SDGTopNaviSearch(
    backgroundColor: Color = SDGColor.Neutral100,
    backBtn: SDGTopNaviMenu.CommonMenu,
    searchItem: SDGTopNaviMenu.SearchMenuV2,
    searchInput: TextFieldValue,
) {
    Row(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .size(40.dp)
                .clickable(false, debounceTimeMills = 1000) { backBtn.onMenuClick.invoke() },
            painter = painterResource(id = backBtn.iconResId),
            colorFilter = if (backBtn.iconTint == null) null else ColorFilter.tint(color = backBtn.iconTint),
            contentDescription = null
        )
        SDGBasicRoundSearch(
            weight = 1f,
            isFillMaxWidth = false,
            outlineType = OutlineType.BASIC,
            input = searchInput,
            hintIconResId = searchItem.hintIconResId,
            hint = searchItem.hint,
            enabled = true,
            onInputChange = searchItem.onInputChange,
            onDeleteClick = searchItem.onDeleteClick,
            onHintIconClick = searchItem.onHintIconClick,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { searchItem.onSearchClick?.invoke() }),
        )
    }
}

@Composable
fun SDGTopNaviProfile(
    userRegImg: String? = null,
    title: String = "",
    titleColor: Color = SDGColor.Neutral700,
    backgroundColor: Color = SDGColor.Neutral100,
    menus: List<SDGTopNaviMenu>? = null,
    backBtn: SDGTopNaviMenu.CommonMenu? = null,
    searchInput: String? = null,
    onAvatarClick: (() -> Unit)? = null,
    onSearchClick: (() -> Unit)? = null,
    onSearchClickColse: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    if (menus != null) {
        require(menus.size < 4) {
            "The maximum size of the menu list must be 3."
        }
    }

    var isSearchMode by remember { mutableStateOf(false) }
    val searchItem = menus?.find { it is SDGTopNaviMenu.SearchMenu } as? SDGTopNaviMenu.SearchMenu

    Crossfade(targetState = isSearchMode, label = "topNaviMode") { isSearch ->
        when (isSearch) {
            true -> SearchLayer(
                backgroundColor = backgroundColor,
                input = searchInput ?: "",
                hintIconResId = searchItem?.hintIconResId,
                hint = searchItem?.hint ?: "",
                onInputChange = searchItem?.onInputChange ?: {},
                onDeleteClick = searchItem?.onDeleteClick ?: {},
                onCloseClick = {
                    onSearchClickColse?.invoke()
                    isSearchMode = false
                },
                onHintIconClick = searchItem?.onHintIconClick,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
            )

            false -> ProfileLayer(
                userRegImg = userRegImg,
                title = title,
                titleColor = titleColor,
                onAvatarClick = onAvatarClick,
                onSearchClick = {
                    onSearchClick?.invoke()
                    isSearchMode = true
                },
                backgroundColor = backgroundColor,
                menus = menus,
                backBtn = backBtn,
            )
        }
    }
}

@Composable
private fun ProfileLayer(
    userRegImg: String? = null,
    title: String = "",
    titleColor: Color = SDGColor.Neutral700,
    backgroundColor: Color = SDGColor.Neutral100,
    menus: List<SDGTopNaviMenu>? = null,
    backBtn: SDGTopNaviMenu.CommonMenu? = null,
    onAvatarClick: (() -> Unit)? = null,
    onSearchClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (backBtn != null) {
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .clickable(false, debounceTimeMills = 1000) { backBtn.onMenuClick.invoke() },
                painter = painterResource(id = backBtn.iconResId),
                colorFilter = if (backBtn.iconTint == null) null else ColorFilter.tint(color = backBtn.iconTint),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(if (backBtn != null) 2.dp else 6.dp))
        userRegImg?.let {
            IOAvatar(
                avatarSize = SDGAvatarSize.XS,
                roleType = "0",
                userRegImg = userRegImg,
                onClickAvatar = onAvatarClick
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        IOText(
            modifier = Modifier
                .weight(1f),
            text = title,
            textColor = titleColor,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.width(12.dp))
        menus?.forEachIndexed { index, menu ->
            Box(
                Modifier
                    .size(40.dp)
                    .clickable(false) {
                        if (menu is SDGTopNaviMenu.SearchMenu) {
                            onSearchClick()
                        } else {
                            menu.onMenuClick.invoke()
                        }
                    }
            ) {
                if (menu.iconResId != null) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = menu.iconResId!!),
                        colorFilter = if (menu.iconTint == null) null else ColorFilter.tint(color = menu.iconTint!!),
                        contentDescription = null
                    )
                    when (menu) {
                        is SDGTopNaviMenu.NumberMenu -> {
                            if (menu.isActive) {
                                IOText(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .sizeIn(minHeight = 14.dp, minWidth = 14.dp)
                                        .background(
                                            color = SDGColor.Red350,
                                            shape = RoundedCornerShape(7.dp)
                                        )
                                        .padding(1.dp)
                                        .align(Alignment.TopEnd),
                                    text = menu.displayCount,
                                    textColor = SDGColor.Neutral0,
                                    fontSize = dpToSp(dp = 8.dp),
                                    textAlign = TextAlign.Center,
                                    typeface = IOTypeface.SEMI_BOLD,
                                    lineHeight = 12.sp
                                )
                            }
                        }

                        is SDGTopNaviMenu.BulletMenu -> {
                            if (menu.isActive) {
                                Canvas(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .size(8.dp)
                                        .align(Alignment.TopEnd)
                                ) {
                                    drawCircle(
                                        color = SDGColor.Red350
                                    )
                                }
                            }
                        }

                        is SDGTopNaviMenu.CommonMenu -> {
                            // do nothing
                        }

                        is SDGTopNaviMenu.SearchMenu,
                        is SDGTopNaviMenu.SearchMenuV2 -> {
                            // do nothing
                        }

                        is SDGTopNaviMenu.TextMenu -> {
                            // do nothing
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun PrevToolBar() {
    Surface(Modifier.fillMaxSize()) {
        val dummyBackBtn = SDGTopNaviMenu.CommonMenu(
            iconResId = R.drawable.ic_toolbar_back,
            onMenuClick = {},
        )
        val dummyMenus = MutableList<SDGTopNaviMenu>(1) { dummyBackBtn.copy() }.apply {
            add(
                SDGTopNaviMenu.BulletMenu(
                    iconResId = R.drawable.ic_navi_filter,
                    onMenuClick = {},
                    isActive = true
                )
            )
            add(
                SDGTopNaviMenu.NumberMenu(
                    iconResId = R.drawable.ic_navi_chat,
                    onMenuClick = {},
                    count = 100
                )
            )
        }
        Column {
            IOText(text = "Top Basic Navi", textColor = SDGColor.Neutral700, fontSize = 30.sp, typeface = IOTypeface.SEMI_BOLD)
            SDGTopNaviBasic(
                title = "가 나 다 라 마 바 사 아 자 차 카 타 파 하 가 나 다 라 " +
                        "마 바 사 아 자 차 카 타 파 하 가 나 다 라 마 바 사 아 자 " +
                        "차 카 타 파 하 가 나 다 라 마 바 사 아 자 차 카 타 파 하",
            )
            Spacer(modifier = Modifier.height(10.dp))

            SDGTopNaviBasic(
                title = "가 나 다 라 마 바 사 아 자 차 카 타 파 하 가 나 다 라 " +
                        "마 바 사 아 자 차 카 타 파 하 가 나 다 라 마 바 사 아 자 " +
                        "차 카 타 파 하 가 나 다 라 마 바 사 아 자 차 카 타 파 하",
                backBtn = dummyBackBtn
            )
            Spacer(modifier = Modifier.height(10.dp))

            SDGTopNaviBasic(
                title = "가 나 다 라 마 바 사 아 자 차 카 타 파 하 가 나 다 라 " +
                        "마 바 사 아 자 차 카 타 파 하 가 나 다 라 마 바 사 아 자 " +
                        "차 카 타 파 하 가 나 다 라 마 바 사 아 자 차 카 타 파 하",
                menus = dummyMenus
            )
            Spacer(modifier = Modifier.height(10.dp))

            SDGTopNaviBasic(
                title = "가 나 다 라 마 바 사 아 자 차 카 타 파 하 가 나 다 라 " +
                        "마 바 사 아 자 차 카 타 파 하 가 나 다 라 마 바 사 아 자 " +
                        "차 카 타 파 하 가 나 다 라 마 바 사 아 자 차 카 타 파 하",
                backBtn = dummyBackBtn,
                menus = dummyMenus
            )
            Spacer(modifier = Modifier.height(10.dp))

            SDGTopNaviBasic(
                title = "가 나 다 라 마 바 사 아 자 차 카 타 파 하 가 나 다 라 " +
                        "마 바 사 아 자 차 카 타 파 하 가 나 다 라 마 바 사 아 자 " +
                        "차 카 타 파 하 가 나 다 라 마 바 사 아 자 차 카 타 파 하",
                backBtn = dummyBackBtn,
                menus = dummyMenus.slice(0..1)
            )
            Spacer(modifier = Modifier.height(10.dp))

            SDGTopNaviBasic(
                title = "가 나 다 라 마 바 사 아 자 차 카 타 파 하 가 나 다 라 " +
                        "마 바 사 아 자 차 카 타 파 하 가 나 다 라 마 바 사 아 자 " +
                        "차 카 타 파 하 가 나 다 라 마 바 사 아 자 차 카 타 파 하",
                backBtn = dummyBackBtn,
                menus = dummyMenus.slice(0..0)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                SDGTopNaviProfile(
                    userRegImg = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg",
                    title = "CASTELAN GONZALEZ NANCY PAMELA",
                    titleColor = SDGColor.Neutral700,
                    backgroundColor = SDGColor.Neutral100,
                    menus = dummyMenus.slice(0..1)
                )
            }
        }
    }
}