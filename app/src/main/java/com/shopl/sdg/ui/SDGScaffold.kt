package com.shopl.sdg.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

/**
 * Scaffold를 기본 배경으로 사용하는 공통 레이아웃 컴포넌트.
 *
 * SystemBar(StatusBar, NavigationBar)는 모두 투명으로 통일되어 edge-to-edge 기반으로 표시됨
 * TopBar 영역에는 statusBarPadding이 적용되어 statusBar와 겹치지 않음
 * WindowInsets.navigationBars 만큼 제외하여 네비게이션 바 영역과 컨텐츠 영역은 겹침
 * FloatingActionButton에는 navigationBarPadding이 적용되어 하단 시스템 영역과 겹치지 않음
 *
 * 개별 컴포넌트의 패딩은 statusBarPadding, navigationBarPadding, ImePadding 등을 통해 제어할 것.
 *
 */
@Composable
internal fun SDGScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackBarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    backgroundColor: Color = SDGColor.Neutral0,
    content: @Composable BoxScope.() -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Box(modifier = Modifier.statusBarsPadding()) {
                topBar()
            }
        },
        bottomBar = bottomBar,
        snackbarHost = snackBarHost,
        floatingActionButton = {
            Box(modifier = Modifier.navigationBarsPadding()) {
                floatingActionButton()
            }
        },
        floatingActionButtonPosition = floatingActionButtonPosition,
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(insets = WindowInsets.navigationBars),
        containerColor = backgroundColor,
        content = { innerPadding ->
            Box(
                content = content,
                modifier = Modifier.padding(paddingValues = innerPadding),
            )
        }
    )
}