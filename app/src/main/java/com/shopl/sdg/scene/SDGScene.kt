package com.shopl.sdg.scene

import kotlinx.collections.immutable.persistentListOf

/**
 * 세 가지 주요 카테고리로 구성
 * - Foundation: 디자인 시스템의 기본 요소 (Color, Typography 등)
 * - Component: UI 컴포넌트들 (Button, Avatar 등)
 * - Template: 컴포넌트들이 조합된 템플릿
 *
 * @param displayLabel Sample App에서 노출되는 이름
 * @param implemented Sample App에 구현 여부 - Sample App 페이지 랜딩 가능 여부를 판단
 */
sealed class SDGScene(
    open val displayLabel: String = "",
    open val implemented: Boolean = false,
) {
    
    /**
     * 개요 화면
     */
    data object Overview : SDGScene(implemented = true)
}

internal val foundationScenes = persistentListOf(
    FoundationScene.Color,
    FoundationScene.CornerRadius,
    FoundationScene.Iconography,
    FoundationScene.Spacing,
    FoundationScene.Typograph,
)

internal val componentScenes = persistentListOf(
    ComponentScene.Avatar,
    ComponentScene.AttachmentList,
    ComponentScene.Badge.CapsuleBadge,
    ComponentScene.Badge.BoxBadge,
    ComponentScene.Button.BottomButton,
    ComponentScene.Button.BoxButton,
    ComponentScene.Button.CapsuleButton,
    ComponentScene.Button.FloatingButton,
    ComponentScene.Button.GhostButton,
    ComponentScene.Button.IconButton,
    ComponentScene.Calendar,
    ComponentScene.Checkbox,
    ComponentScene.CheckOption,
    ComponentScene.Dropdown,
    ComponentScene.EmptyIcon.BasicEmptyIcon,
    ComponentScene.EmptyIcon.ContentsEmptyIcon,
    ComponentScene.Indicator.TextIndicator,
    ComponentScene.Indicator.NumberIndicator,
    ComponentScene.Indicator.DotIndicator,
    ComponentScene.IconLabel,
    ComponentScene.NumberPicker,
    ComponentScene.Progress.SystemProgress,
    ComponentScene.Progress.CircularProgress,
    ComponentScene.Progress.DotProgress,
    ComponentScene.Progress.LinearProgress,
    ComponentScene.Radio,
    ComponentScene.SearchBar.CapsuleSearch,
    ComponentScene.SearchBar.BoxSearch,
    ComponentScene.SearchBar.CategorySearch,
    ComponentScene.Segment,
    ComponentScene.SelectInput,
    ComponentScene.Tab.ScrollTab,
    ComponentScene.Tab.FixedTab,
    ComponentScene.Tab.BoxTab,
    ComponentScene.Tab.IconTab,
    ComponentScene.TextInput.SimpleTextInput,
    ComponentScene.TextInput.FixedTextInput,
    ComponentScene.TextInput.UnderlineInput,
    ComponentScene.TextInput.LoginInput,
    ComponentScene.Thumbnails,
    ComponentScene.TimePicker,
    ComponentScene.TimeSelectInput,
    ComponentScene.Toggle,
    ComponentScene.Tooltip,
)