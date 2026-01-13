package com.shopl.sdg.navigation

import kotlinx.serialization.Serializable

/**
 * 세 가지 주요 카테고리로 구성
 * - Foundation: 디자인 시스템의 기본 요소 (Color, Typography 등)
 * - Component: UI 컴포넌트들 (Button, Avatar 등)
 * - Template: 컴포넌트들이 조합된 템플릿
 */
sealed interface SDGDestination {
    
    /**
     * 개요 화면
     */
    @Serializable
    data object Overview : SDGDestination
}
