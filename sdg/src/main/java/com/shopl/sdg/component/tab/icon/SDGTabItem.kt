package com.shopl.sdg.component.tab.icon

import androidx.compose.runtime.Immutable

/**
 * [SDGIconTabOption]에 전달하는 Icon Tab Item의 속성입니다.
 *
 * 선택 위치는 [SDGIconTab]의 selectedTab에서 제어하며, 아이템은 공통 정보만 담습니다.
 * Count는 가용 너비에 맞춰 최대 수치 표기(예: 999+)로 가공한 문자열을 전달합니다.
 *
 * @param label Label: 탭 라벨이자 비선택 아이콘의 접근성 설명
 * @param count Count: 선택 여부와 관계없이 유지하는 수치 문자열. 예: N, 0, 999+
 * @param showCount Show Count: 선택 여부와 관계없이 수치를 표시할지 여부
 * @param iconTabIc Icon Tab ic: 비선택 상태에서 표시할 아이콘
 */
@Immutable
data class SDGTabItem(
    val label: String,
    val count: String,
    val showCount: Boolean,
    val iconTabIc: SDGIconTabIcon,
)
