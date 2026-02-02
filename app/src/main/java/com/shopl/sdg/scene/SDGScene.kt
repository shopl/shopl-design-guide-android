package com.shopl.sdg.scene

import androidx.compose.runtime.Composable
import com.shopl.sdg.ui.screen.MenuScreen
import com.shopl.sdg.ui.screen.OverviewScreen

/**
 * 세 가지 주요 카테고리로 구성
 * - Foundation: 디자인 시스템의 기본 요소 (Color, Typography 등)
 * - Component: UI 컴포넌트들 (Button, Avatar 등)
 * - Template: 컴포넌트들이 조합된 템플릿
 *
 * @param displayLabel Sample App에서 노출되는 이름
 * @param implemented Sample App에 구현 여부 - Sample App 페이지 랜딩 가능 여부를 판단
 */
internal sealed class SDGScene(
    open val displayLabel: String = "",
    open val implemented: Boolean = false,
) {

    open val isDarkIcon: Boolean = true

    open val isPopup: Boolean = false

    @Composable
    abstract fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit)

    /**
     * 개요 화면
     */
    data object Overview : SDGScene(
        displayLabel = "Overview",
        implemented = true
    ) {

        override val isDarkIcon: Boolean = false

        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            OverviewScreen(moveToScene = moveToScene)
        }
    }

    /**
     * 메뉴 화면
     */
    data object Menu : SDGScene(
        displayLabel = "Menu",
        implemented = true
    ) {

        override val isPopup: Boolean = true

        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            MenuScreen(
                moveToScene = {
                    backToScene()
                    moveToScene(it)
                },
                moveToBack = backToScene
            )
        }
    }
}
