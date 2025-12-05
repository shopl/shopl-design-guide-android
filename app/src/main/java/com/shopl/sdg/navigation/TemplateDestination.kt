package com.shopl.sdg.navigation

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

/**
 * SDG - Template
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=16930-2151&m=dev">Figma</a>
 */
@Stable
sealed interface TemplateDestination : SDGDestination {
    @Serializable
    data object CalendarAndTime : TemplateDestination

    @Serializable
    data object CheckboxLabel : TemplateDestination

    @Serializable
    data object CheckOptionLabel : TemplateDestination

    @Serializable
    data object EmptyImg : TemplateDestination

    @Stable
    sealed interface FilterChip : TemplateDestination {
        @Serializable
        data object NaviFilterChip : FilterChip

        @Serializable
        data object BodyFilterChip : FilterChip
    }

    @Stable
    sealed interface Form : TemplateDestination {
        @Serializable
        data object DropdownForm : Form

        @Serializable
        data object SelectForm : Form

        @Serializable
        data object SimpleTextForm : Form

        @Serializable
        data object FixedTextForm : Form

        @Serializable
        data object TimeSelectForm : Form
    }

    @Stable
    sealed interface FoundationList : TemplateDestination {
        @Serializable
        data object UserList : FoundationList

        @Serializable
        data object WorkplaceList : FoundationList

        @Serializable
        data object GroupAndPositionList : FoundationList
    }

    @Serializable
    data object History : TemplateDestination

    @Serializable
    data object HistoryMini : TemplateDestination

    @Serializable
    data object ListHeader : TemplateDestination

    @Serializable
    data object MultiCalendar : TemplateDestination

    @Serializable
    data object MultiTimePicker : TemplateDestination

    @Stable
    sealed interface Navigation : TemplateDestination {
        @Serializable
        data object BasicNavi : Navigation

        @Serializable
        data object CategoryNavi : Navigation

        @Serializable
        data object TextNavi : Navigation

        @Serializable
        data object SearchNavi : Navigation
    }

    @Stable
    sealed interface Popup : TemplateDestination {
        @Serializable
        data object CenterPopup : Popup

        @Serializable
        data object BottomPopup : Popup

        @Serializable
        data object ListPopup : Popup

        @Serializable
        data object MiniListPopup : Popup

        @Serializable
        data object IconPopup : Popup

        @Serializable
        data object ToastPopup : Popup
    }

    @Stable
    sealed interface Profile : TemplateDestination {
        @Serializable
        data object BasicProfile : Profile

        @Serializable
        data object SecondProfile : Profile

        @Serializable
        data object SimpleProfile : Profile

        @Serializable
        data object MiniProfile : Profile
    }

    @Serializable
    data object RadioLabel : TemplateDestination
}
